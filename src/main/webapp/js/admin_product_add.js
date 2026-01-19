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
        const variantImage = item.querySelector('input[name="variantImage"]').files;

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
            body: JSON.stringify({ skus: skuList })
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
async function uploadImageToFirebase(file, folder) {
    return new Promise((resolve, reject) => {
        // Tạo tên file unique
        const timestamp = Date.now();
        const fileName = `${timestamp}_${file.name}`;
        const storageRef = storage.ref(`${folder}/${fileName}`);

        // Upload file
        const uploadTask = storageRef.put(file);

        uploadTask.on('state_changed',
            (snapshot) => {
                // Progress
                const progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                console.log('Upload is ' + progress + '% done');
            },
            (error) => {
                // Error
                console.error('Upload error:', error);
                reject(error);
            },
            () => {
                // Success - lấy URL
                uploadTask.snapshot.ref.getDownloadURL().then((downloadURL) => {
                    resolve(downloadURL);
                });
            }
        );
    });
}

// Upload nhiều ảnh
async function uploadMultipleImages(files, folder) {
    const uploadPromises = Array.from(files).map(file =>
        uploadImageToFirebase(file, folder)
    );
    return Promise.all(uploadPromises);
}

//Gửi data xuống servlet khi đã check và upload hình ảnh lên firestorage thành công
async function submitProductToServlet(productData) {
    try {
        const response = await fetch('AddProductServlet', {
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

//bắt sự kiện submit của form add product
document.getElementById('form-add-product').addEventListener('submit', async (ev) => {
    ev.preventDefault();

    const submitBtn = document.getElementById('submitAddProd');
    const originalText = submitBtn.textContent;
    submitBtn.disabled = true;
    submitBtn.textContent = 'Đang xử lý...';

    try {
        //Validate form
        const validation = validateFormData();
        if (!validation.isValid) {
            showAlert('error', validation.errors.join('<br>'));
            return;
        }

        //Kiểm tra SKU tồn tại trên server
        const skuCheck = await checkSKUExists(validation.skuList);
        if (skuCheck.exists) {
            showAlert('error', `Mã SKU đã tồn tại: ${skuCheck.duplicateSKUs.join(', ')}`);
            return;
        }

        // BƯỚC 3: Upload ảnh sản phẩm lên Firebase Storage
        showAlert('warning', 'Đang upload ảnh sản phẩm...');
        const productImages = document.getElementById('productImages').files;
        const productImageURLs = await uploadMultipleImages(productImages, 'products');

        // Xác định ảnh chính
        const mainImageIndex = document.querySelector('input[name="mainImage"]:checked')?.value || 0;

        // BƯỚC 4: Upload ảnh biến thể và thu thập dữ liệu
        showAlert('warning', 'Đang upload ảnh biến thể...');
        const variantItems = document.querySelectorAll('.variant-item');
        const variantsData = [];

        for (let i = 0; i < variantItems.length; i++) {
            const item = variantItems[i];

            // Upload ảnh biến thể
            const variantImageFile = item.querySelector('input[name="variantImage"]').files[0];
            const variantImageURL = await uploadImageToFirebase(variantImageFile, 'variants');

            // Thu thập dữ liệu biến thể
            variantsData.push({
                name: item.querySelector('input[name="variantNames"]').value.trim(),
                sku: item.querySelector('input[name="sku"]').value.trim(),
                price: parseFloat(item.querySelector('input[name="variantPrices"]').value),
                stock: parseInt(item.querySelector('input[name="variantStocks"]').value),
                gram: parseFloat(item.querySelector('input[name="gram"]').value),
                color: item.querySelector('input[name="color"]').value.trim(),
                size: item.querySelector('input[name="size"]').value.trim(),
                imageUrl: variantImageURL
            });
        }

        // BƯỚC 5: Tạo object dữ liệu hoàn chỉnh
        const productData = {
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

        // BƯỚC 6: Gửi dữ liệu xuống servlet
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