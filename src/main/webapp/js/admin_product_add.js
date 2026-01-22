import { storage, ref, uploadBytesResumable, getDownloadURL } from "./firebase.js";

const contextPath = window.contextPath

//Show thông báo
function showAlert(type, message) {
    // type: 'success', 'error', 'warning'
    const alertDiv = document.createElement('div');
    alertDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 5px;
        color: white;
        font-weight: bold;
        z-index: 10000;
        animation: slideIn 0.3s ease-in-out;
        max-width: 400px;
    `;

    if (type === 'success') {
        alertDiv.style.backgroundColor = '#4CAF50';
        alertDiv.innerHTML = '✓ ' + message;
    } else if (type === 'error') {
        alertDiv.style.backgroundColor = '#f44336';
        alertDiv.innerHTML = '✕ ' + message;
    } else if (type === 'warning') {
        alertDiv.style.backgroundColor = '#ff9800';
        alertDiv.innerHTML = '⚠ ' + message;
    }

    document.body.appendChild(alertDiv);

    // Tự động xóa sau 5 giây
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

//Thằng này sẽ check tính hợp lệ của form popup add product
function validateFormData() {
    const errors = [];

    // Lấy dữ liệu sản phẩm
    const name = document.getElementById('add-prod-name').value.trim();
    const warrantyPeriod = document.getElementById('add-prod-warranty').value.trim();
    const categoryId = document.getElementById('add-prod-categoryId').value;
    const subtitle = document.getElementById('add-prod-subtitle').value.trim();
    const description = document.getElementById('add-prod-description').value.trim();
    const productImages = document.getElementById('add-prod-productImages').files;

    // Validate thông tin sản phẩm
    if (!name) errors.push('Tên sản phẩm không được để trống');
    if (!warrantyPeriod || warrantyPeriod <= 0) errors.push('Thời gian bảo hành phải lớn hơn 0');
    if (!categoryId) errors.push('Vui lòng chọn thể loại sản phẩm');
    if (!subtitle) errors.push('Mô tả ngắn không được để trống');
    if (!description) errors.push('Mô tả dài không được để trống');
    if (!productImages || productImages.length === 0) {
        errors.push('Vui lòng chọn ít nhất 1 ảnh sản phẩm');
    }

    // Lấy tất cả biến thể
    const variantItems = document.querySelectorAll('.add-prod-variant-item');
    if (variantItems.length === 0) {
        errors.push('Vui lòng thêm ít nhất 1 biến thể');
    }

    // Validate từng biến thể
    const skuList = [];
    variantItems.forEach((item, index) => {
        const variantName = item.querySelector('input[name="variantNames"]').value.trim();
        const sku = item.querySelector('input[name="sku"]').value.trim();
        const price = item.querySelector('input[name="variantPrices"]').value.trim();
        const stock = item.querySelector('input[name="variantStocks"]').value.trim();
        const gram = item.querySelector('input[name="gram"]').value.trim();
        const color = item.querySelector('input[name="color"]').value.trim();
        const size = item.querySelector('input[name="size"]').value.trim();
        const variantImage = item.querySelector('input[name="add-prod-variantImages"]').files;

        if (!variantName) errors.push(`Biến thể ${index + 1}: Tên biến thể không được để trống`);
        if (!sku) errors.push(`Biến thể ${index + 1}: Mã SKU không được để trống`);
        else skuList.push(sku);
        if (!price || price <= 0) errors.push(`Biến thể ${index + 1}: Giá phải lớn hơn 0`);
        if (!stock || stock < 0) errors.push(`Biến thể ${index + 1}: Số lượng không hợp lệ`);
        if (!gram || gram <= 0) errors.push(`Biến thể ${index + 1}: Trọng lượng phải lớn hơn 0`);
        if (!color) errors.push(`Biến thể ${index + 1}: Màu sắc không được để trống`);
        if (!size) errors.push(`Biến thể ${index + 1}: Kích thước không được để trống`);
        if (!variantImage || variantImage.length === 0) {
            errors.push(`Biến thể ${index + 1}: Vui lòng chọn ảnh biến thể`);
        }
    });

    // Kiểm tra SKU trùng trong form
    const uniqueSKUs = new Set(skuList);
    if (uniqueSKUs.size !== skuList.length) {
        errors.push('Có mã SKU bị trùng lặp trong danh sách biến thể');
    }

    return {
        isValid: errors.length === 0,
        errors: errors,
        skuList: skuList
    };
}

//Xuống servlet kiểm tra các sku biến thể đã tồn tại chưa, nếu có thì không cho phép thêm sp
async function checkSKUExists(skuList) {
    try {
        const response = await fetch('check-sku', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({skus: skuList})
        });

        if (!response.ok) {
            throw new Error('Lỗi kết nối server');
        }

        const result = await response.json();
        // Expecting: { exists: true/false, duplicateSKUs: [] }
        return result;

    } catch (error) {
        console.error('Lỗi khi kiểm tra SKU:', error);
        throw error;
    }
}

//up ảnh lên firestorage
export async function uploadImageToFirebase(file, folderPath, fileName) {
    return new Promise((resolve, reject) => {
        // 1. Tạo reference
        const storageRef = ref(storage, `${folderPath}/${fileName}`);

        const metadata = {
            contentType: file.type
        };

        // 2. Bắt đầu upload
        const uploadTask = uploadBytesResumable(storageRef, file, metadata);

        uploadTask.on('state_changed',
            (snapshot) => {
                const progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                console.log(`Đang upload ${fileName}: ${progress.toFixed(2)}%`);
            },
            (error) => {
                console.error('Lỗi upload Firebase:', error);
                reject(error);
            },
            async () => {
                // 3. Lấy URL (Cách mới: getDownloadURL(task.snapshot.ref))
                try {
                    const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
                    resolve(downloadURL);
                } catch (err) {
                    reject(err);
                }
            }
        );
    });
}

//Gửi data xuống servlet khi đã check và upload hình ảnh lên firestorage thành công
async function submitProductToServlet(productData) {
    try {
        const response = await fetch(contextPath + '/add-product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });

        if (!response.ok) {
            throw new Error('Lỗi kết nối server');
        }

        const result = await response.json();
        // Expecting: { success: true/false, message: "..." }
        return result;

    } catch (error) {
        console.error('Lỗi khi gửi dữ liệu:', error);
        throw error;
    }
}

//bắt sự kiện submit của form add product
document.getElementById('form-add-product').addEventListener('submit', async (ev) => {
    ev.preventDefault();

    //Lấy là uuid để lưu vào phân cấp thư mục của storage products/uuid/main + variants (SKU)
    const folderId = self.crypto.randomUUID();

    const submitBtn = document.getElementById('submitAddProd');
    const originalText = submitBtn.textContent;
    submitBtn.disabled = true;
    submitBtn.textContent = 'Đang xử lý...';

    try {
        //TODO: Validate form
        const validation = validateFormData();
        if (!validation.isValid) {
            showAlert('error', validation.errors.join('<br>'));
            return;
        }

        //TODO: Kiểm tra SKU tồn tại trên server
        const skuCheck = await checkSKUExists(validation.skuList);
        if (skuCheck.exists) {
            showAlert('error', `Mã SKU đã tồn tại: ${skuCheck.duplicateSKUs.join(', ')}`);
            return;
        }

        //TODO: Upload ảnh sản phẩm lên Firebase Storage
        //Thêm ảnh của sản phẩm chung
        const productImages = document.getElementById('add-prod-productImages').files;
        const productImageURLs = [];

        for (let i = 0; i < productImages.length; i++) {
            // Đặt tên theo kiểu: prod_timestamp_index
            const customFileName = `prod_${Date.now()}_${i}`;
            const url = await uploadImageToFirebase(
                productImages[i],
                `products/${folderId}/main`,
                customFileName
            );
            productImageURLs.push(url);
        }

        //TODO: Xác định ảnh chính
        const mainImageIndex = document.querySelector('input[name="add-prod-mainImage"]:checked')?.value || 0;

        //TODO: Upload ảnh biến thể và thu thập dữ liệu
        const variantItems = document.querySelectorAll('.add-prod-variant-item');
        const variantsData = [];

        for (let i = 0; i < variantItems.length; i++) {
            const item = variantItems[i];

            // 1. Lấy mã SKU trước để làm tên file ảnh
            const sku = item.querySelector('input[name="sku"]').value.trim();
            const variantImageFile = item.querySelector('input[name="add-prod-variantImages"]').files[0];

            let variantImageURL = "";

            // 2. Chỉ upload nếu có chọn file ảnh
            if (variantImageFile) {
                // Đường dẫn: products/{folderId}/variants/{sku}
                // Không có extension phía sau tên file sku
                variantImageURL = await uploadImageToFirebase(
                    variantImageFile,
                    `products/${folderId}/variants`,
                    sku
                );
            }

            // 3. Thu thập dữ liệu biến thể
            variantsData.push({
                name: item.querySelector('input[name="variantNames"]').value.trim(),
                sku: sku,
                price: parseFloat(item.querySelector('input[name="variantPrices"]').value) || 0,
                stock: parseInt(item.querySelector('input[name="variantStocks"]').value) || 0,
                gram: parseFloat(item.querySelector('input[name="gram"]').value) || 0,
                color: item.querySelector('input[name="color"]').value.trim(),
                size: item.querySelector('input[name="size"]').value.trim(),
                imageUrl: variantImageURL // Đây là link URL từ Firebase
            });
        }
        // Tạo object dữ liệu hoàn chỉnh
        const productData = {
            folderId: folderId,
            name: document.querySelector('input[name="name"]').value.trim(),
            warrantyPeriod: parseInt(document.querySelector('input[name="warranty-period"]').value),
            categoryId: document.querySelector('select[name="categoryId"]').value,
            subtitle: document.querySelector('textarea[name="subtitle"]').value.trim(),
            description: document.querySelector('textarea[name="description"]').value.trim(),
            status: document.querySelector('input[name="status"]').checked,
            imageUrls: productImageURLs,
            mainImageIndex: parseInt(mainImageIndex),
            variants: variantsData
        };

        //TODO: Gửi dữ liệu xuống servlet
        showAlert('warning', 'Đang lưu sản phẩm...');
        const result = await submitProductToServlet(productData);

        if (result.success) {
            showAlert('success', 'Thêm sản phẩm thành công!');
            // Reset form hoặc đóng popup
            setTimeout(() => {
                document.getElementById('popup-add-product').style.display = 'none';
                location.reload(); // hoặc redirect
            }, 2000);
        } else {
            showAlert('error', result.message || 'Có lỗi xảy ra khi lưu sản phẩm!');
        }

    } catch (error) {
        console.error('Error:', error);
        showAlert('error', 'Có lỗi xảy ra: ' + error.message);
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = originalText;
    }
})

/*
Cấu trúc json request xuống servlet
{
  "folderId": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Tên sản phẩm chính",
  "warrantyPeriod": 12,
  "categoryId": "CAT001",
  "subtitle": "Mô tả ngắn gọn cho sản phẩm",
  "description": "Nội dung mô tả chi tiết sản phẩm bằng HTML hoặc text",
  "status": true,
  "imageUrls": [
    "https://firebasestorage.googleapis.com/.../main/prod_17152345_0?alt=media",
    "https://firebasestorage.googleapis.com/.../main/prod_17152345_1?alt=media"
  ],
  "mainImageIndex": 0,
  "variants": [
    {
      "name": "Biến thể màu Đỏ - Size L",
      "sku": "SKU-IP15-RED-L",
      "price": 25000000.0,
      "stock": 50,
      "gram": 200.5,
      "color": "Red",
      "size": "L",
      "imageUrl": "https://firebasestorage.googleapis.com/.../variants/SKU-IP15-RED-L?alt=media"
    },
    {
      "name": "Biến thể màu Xanh - Size XL",
      "sku": "SKU-IP15-BLUE-XL",
      "price": 26000000.0,
      "stock": 30,
      "gram": 210.0,
      "color": "Blue",
      "size": "XL",
      "imageUrl": "https://firebasestorage.googleapis.com/.../variants/SKU-IP15-BLUE-XL?alt=media"
    }
  ]
}
 */