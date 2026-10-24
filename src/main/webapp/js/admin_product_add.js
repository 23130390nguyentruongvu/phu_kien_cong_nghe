import {hideLoading, showLoading} from "./overlay_processing.js";

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
        alertDiv.textContent = '✓ ' + message;
    } else if (type === 'error') {
        alertDiv.style.backgroundColor = '#f44336';
        alertDiv.textContent = '✕ ' + message;
    } else if (type === 'warning') {
        alertDiv.style.backgroundColor = '#ff9800';
        alertDiv.textContent = '⚠ ' + message;
    }
    alertDiv.style.whiteSpace = 'pre-line';

    document.body.appendChild(alertDiv);

    // Tự động xóa sau 5 giây
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

let didAttemptSubmit = false;

function setInlineError(input, message, errorKey = input.name) {
    const scope = input.closest('.add-prod-variant-item') || document;
    const error = scope.querySelector(`[data-error-for="${errorKey}"]`);
    input.setAttribute('aria-invalid', message ? 'true' : 'false');
    input.classList.toggle('input-invalid', Boolean(message));
    if (error) error.textContent = message;
}

function setStandaloneError(key, message) {
    const error = document.querySelector(`[data-error-for="${key}"]`);
    if (error) error.textContent = message;
}

function validateFormData() {
    const errors = [];
    const skuInputs = [];
    const validate = (input, message, errorKey) => {
        setInlineError(input, message, errorKey || input.name);
        if (message) errors.push(message);
    };
    const name = document.getElementById('add-prod-name');
    const warranty = document.getElementById('add-prod-warranty');
    const category = document.getElementById('add-prod-categoryId');
    const subtitle = document.getElementById('add-prod-subtitle');
    const description = document.getElementById('add-prod-description');
    const productImages = document.getElementById('add-prod-productImages');

    validate(name, !name.value.trim() ? 'Tên sản phẩm không được để trống'
        : name.value.trim().length > 191 ? 'Tên sản phẩm tối đa 191 ký tự' : '', 'add-prod-name');
    const warrantyValue = Number(warranty.value);
    validate(warranty, !Number.isInteger(warrantyValue) || warrantyValue < 1 || warrantyValue > 127
        ? 'Thời gian bảo hành phải là số nguyên từ 1 đến 127 tháng' : '', 'add-prod-warranty');
    validate(category, !category.value ? 'Vui lòng chọn một thể loại con' : '', 'add-prod-categoryId');
    validate(subtitle, !subtitle.value.trim() ? 'Mô tả ngắn không được để trống'
        : subtitle.value.trim().length > 191 ? 'Mô tả ngắn tối đa 191 ký tự' : '', 'add-prod-subtitle');
    validate(description, !description.value.trim() ? 'Mô tả chi tiết không được để trống' : '',
        'add-prod-description');

    const images = Array.from(productImages.files || []);
    const invalidProductImage = images.some(file => !file.type.startsWith('image/'));
    validate(productImages, images.length === 0 ? 'Vui lòng chọn ít nhất 1 ảnh sản phẩm'
        : invalidProductImage ? 'Tệp tải lên phải là ảnh' : '', 'add-prod-productImages');
    const mainImage = document.querySelector('input[name="add-prod-mainImage"]:checked');
    const mainImageError = images.length && !mainImage ? 'Vui lòng chọn một ảnh chính' : '';
    setStandaloneError('add-prod-mainImage', mainImageError);
    if (mainImageError) {
        errors.push(mainImageError);
    }

    const variantItems = Array.from(document.querySelectorAll('.add-prod-variant-item'));
    setStandaloneError('variantList', variantItems.length ? '' : 'Sản phẩm phải có ít nhất 1 biến thể');
    if (!variantItems.length) errors.push('Sản phẩm phải có ít nhất 1 biến thể');

    variantItems.forEach((item, index) => {
        const field = name => item.querySelector(`input[name="${name}"]`);
        const variantName = field('variantNames');
        const sku = field('sku');
        const price = field('variantPrices');
        const stock = field('variantStocks');
        const gram = field('gram');
        const color = field('color');
        const size = field('size');
        const variantImage = field('add-prod-variantImages');
        const label = `Biến thể ${index + 1}: `;
        const trimmedName = variantName.value.trim();
        const trimmedSku = sku.value.trim();
        const trimmedColor = color.value.trim();
        const priceText = price.value.trim();
        const priceValue = Number(priceText);
        const stockValue = Number(stock.value);
        const gramValue = Number(gram.value);

        validate(variantName, trimmedName.length < 3 ? label + 'Tên phải có ít nhất 3 ký tự'
            : trimmedName.length > 191 ? label + 'Tên tối đa 191 ký tự' : '');
        validate(sku, trimmedSku.length < 2 ? label + 'SKU phải có ít nhất 2 ký tự'
            : trimmedSku.length > 50 ? label + 'SKU tối đa 50 ký tự' : '');
        if (trimmedSku.length >= 2 && trimmedSku.length <= 50) skuInputs.push(sku);
        validate(price, !/^\d+(?:\.\d{1,2})?$/.test(priceText) || !Number.isFinite(priceValue)
            || priceValue <= 0 || priceValue > 9999999999999.99
            ? label + 'Giá phải lớn hơn 0 và có tối đa 2 chữ số thập phân' : '');
        validate(stock, !Number.isInteger(stockValue) || stockValue < 0 || stockValue > 2147483647
            ? label + 'Số lượng phải là số nguyên từ 0 trở lên' : '');
        validate(gram, !Number.isInteger(gramValue) || gramValue <= 0 || gramValue > 2147483647
            ? label + 'Trọng lượng phải là số nguyên lớn hơn 0 gram' : '');
        validate(color, trimmedColor.length < 2 ? label + 'Màu sắc phải có ít nhất 2 ký tự'
            : trimmedColor.length > 50 ? label + 'Màu sắc tối đa 50 ký tự' : '');
        validate(size, !size.value.trim() ? label + 'Kích thước không được để trống'
            : size.value.trim().length > 50 ? label + 'Kích thước tối đa 50 ký tự' : '');
        validate(variantImage, !variantImage.files.length ? label + 'Vui lòng chọn ảnh biến thể'
            : !variantImage.files[0].type.startsWith('image/') ? label + 'Tệp biến thể phải là ảnh' : '');
    });

    const skuGroups = new Map();
    skuInputs.forEach(input => {
        const key = input.value.trim().toLocaleLowerCase();
        if (!skuGroups.has(key)) skuGroups.set(key, []);
        skuGroups.get(key).push(input);
    });
    let duplicateSkuFound = false;
    skuGroups.forEach(inputs => {
        const isDuplicate = inputs.length > 1;
        inputs.forEach(input => {
            if (isDuplicate) {
                setInlineError(input, 'SKU bị trùng trong các biến thể của form');
                duplicateSkuFound = true;
            }
        });
    });
    if (duplicateSkuFound) errors.push('SKU không được trùng giữa các biến thể');

    const totalStock = variantItems.reduce((total, item) => {
        const value = Number(item.querySelector('input[name="variantStocks"]').value);
        return Number.isInteger(value) && value >= 0 ? total + value : total;
    }, 0);
    if (totalStock > 2147483647) {
        setStandaloneError('variantList', 'Tổng tồn kho vượt quá giới hạn số nguyên của sản phẩm');
        errors.push('Tổng tồn kho của sản phẩm không được vượt quá 2147483647');
    } else if (variantItems.length) {
        setStandaloneError('variantList', '');
    }

    updateDerivedProductValues();
    return {
        isValid: errors.length === 0,
        errors,
        skuList: skuInputs.map(input => input.value.trim())
    };
}

function updateDerivedProductValues() {
    const prices = Array.from(document.querySelectorAll('input[name="variantPrices"]'))
        .map(input => Number(input.value))
        .filter(price => Number.isFinite(price) && price > 0);
    const stock = Array.from(document.querySelectorAll('input[name="variantStocks"]'))
        .map(input => Number(input.value))
        .filter(value => Number.isInteger(value) && value >= 0)
        .reduce((total, value) => total + value, 0);
    document.getElementById('derived-min-price').textContent =
        prices.length ? Math.min(...prices).toLocaleString('vi-VN') : '—';
    document.getElementById('derived-max-price').textContent =
        prices.length ? Math.max(...prices).toLocaleString('vi-VN') : '—';
    document.getElementById('derived-total-stock').textContent = stock.toLocaleString('vi-VN');
}

//Xuống servlet kiểm tra các sku biến thể đã tồn tại chưa, nếu có thì không cho phép thêm sp
async function checkSKUExists(skuList) {
    const response = await fetch(contextPath + '/check-sku', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({skus: skuList})
    });
    if (!response.ok) {
        throw new Error('Không thể kiểm tra SKU trên máy chủ. Vui lòng thử lại.');
    }
    return await response.json();
}

//up ảnh lên firestorage
export async function uploadImageToFirebase(file, folderPath, fileName) {
    const {storage, ref, uploadBytesResumable, getDownloadURL} = await import("./firebase.js");
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
    didAttemptSubmit = true;

    const submitBtn = document.getElementById('submitAddProd');
    const originalText = submitBtn.textContent;

    try {
        const validation = validateFormData();
        if (!validation.isValid) {
            document.querySelector('.input-invalid')?.focus();
            return;
        }

        submitBtn.disabled = true;
        submitBtn.textContent = 'Đang kiểm tra...';
        const skuCheck = await checkSKUExists(validation.skuList);
        if (skuCheck.exists) {
            const duplicates = new Set(skuCheck.duplicateSKUs.map(sku => sku.toLocaleLowerCase()));
            document.querySelectorAll('.add-prod-variant-item input[name="sku"]').forEach(input => {
                if (duplicates.has(input.value.trim().toLocaleLowerCase())) {
                    setInlineError(input, 'SKU này đã tồn tại trong kho dữ liệu');
                }
            });
            document.querySelector('.input-invalid')?.focus();
            return;
        }

        const folderId = self.crypto.randomUUID();
        submitBtn.textContent = 'Đang xử lý...';
        showLoading();
        await new Promise(res => requestAnimationFrame(res));

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
        const mainImageIndex = Number(document.querySelector('input[name="add-prod-mainImage"]:checked').value);

        //TODO: Upload ảnh biến thể và thu thập dữ liệu
        const variantItems = document.querySelectorAll('.add-prod-variant-item');
        const variantsData = [];

        for (let i = 0; i < variantItems.length; i++) {
            const item = variantItems[i];

            // 1. Lấy mã SKU trước để làm tên file ảnh
            const sku = item.querySelector('input[name="sku"]').value.trim();
            const variantImageFile = item.querySelector('input[name="add-prod-variantImages"]').files[0];

            const variantImageURL = await uploadImageToFirebase(
                variantImageFile,
                `products/${folderId}/variants`,
                sku
            );

            // 3. Thu thập dữ liệu biến thể
            variantsData.push({
                name: item.querySelector('input[name="variantNames"]').value.trim(),
                sku: sku,
                price: parseFloat(item.querySelector('input[name="variantPrices"]').value) || 0,
                stock: parseInt(item.querySelector('input[name="variantStocks"]').value) || 0,
                gram: parseInt(item.querySelector('input[name="gram"]').value, 10),
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
            setInlineError(document.getElementById('add-prod-name'),
                result.message || 'Không thể lưu thông tin sản phẩm cha.', 'add-prod-name');
            showAlert('error', result.message || 'Có lỗi xảy ra khi lưu sản phẩm!');
        }

    } catch (error) {
        console.error('Error:', error);
        showAlert('error', 'Có lỗi xảy ra: ' + error.message);
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = originalText;
        hideLoading();
    }
});

document.getElementById('form-add-product').addEventListener('input', event => {
    if (event.target.matches('input[name="variantPrices"], input[name="variantStocks"]')) {
        updateDerivedProductValues();
    }
    if (didAttemptSubmit) validateFormData();
});
document.getElementById('form-add-product').addEventListener('change', () => {
    if (didAttemptSubmit) validateFormData();
});

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