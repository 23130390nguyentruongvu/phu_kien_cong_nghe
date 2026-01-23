import {uploadImageToFirebase} from "./admin_product_add.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

const validateVariantForm = (form) => {
    const name = form.variantNames.value.trim();
    const sku = form.sku.value.trim();
    const price = form.variantPrices.value.trim();
    const stock = form.variantStocks.value.trim();
    const gram = form.gram.value.trim();
    const color = form.color.value.trim();
    const size = form.size.value.trim();
    const fileInput = form.variantImage; // input file

    let errors = [];

    // Kiểm tra tên biến thể
    if (name.length < 3) {
        errors.push("Tên biến thể phải có ít nhất 3 ký tự.");
    }

    // Kiểm tra SKU
    if (sku.length < 2) {
        errors.push("Mã SKU phải có ít nhất 2 ký tự.");
    }

    // Kiểm tra giá
    if (!price || isNaN(price) || Number(price) <= 0) {
        errors.push("Giá phải là số dương.");
    }

    // Kiểm tra số lượng
    if (!stock || isNaN(stock) || Number(stock) < 0) {
        errors.push("Số lượng phải là số không âm.");
    }

    // Kiểm tra trọng lượng
    if (!gram || isNaN(gram) || Number(gram) <= 0) {
        errors.push("Trọng lượng phải là số dương.");
    }

    // Kiểm tra màu sắc
    if (!color || color.length < 2) {
        errors.push("Màu sắc phải có ít nhất 2 ký tự.");
    }

    // Kiểm tra kích thước
    if (!size || size.length < 1) {
        errors.push("Kích thước không được để trống.");
    }

    // Kiểm tra input file
    if (!fileInput.files || fileInput.files.length === 0) {
        errors.push("Vui lòng chọn ảnh cho biến thể.");
    }

    if (errors.length > 0) {
        alert("Có lỗi:\n- " + errors.join("\n- "));
        return false;
    }
    return true;
};

const processBtnSubmit = (prodId) => {
    const form = document.getElementById('variantForm');
    const submit = document.getElementById('submitAddProdVar')
    const closePopup = document.getElementById('closeAddProdVar')

    const buttonSubmit = document.getElementById('submitAddProdVar')
    buttonSubmit.addEventListener('click', async (ev) => {
        ev.preventDefault();
        try {
            if (!validateVariantForm(form)) return;
            //Check sự tồn tại của sku biến thể
            const response = await fetch(contextPath + '/check-sku', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({skus: [form.sku.value.trim()]})
            });
            const result = await response.json()
            if (result.exists) {
                alert(`Mã SKU của sản phẩm đã tồn tại trong cửa hàng`)
                return
            }

            submit.disabled = true
            submit.textContent = 'Đang thêm...'
            closePopup.disabled = true
            showLoading()

            await new Promise(res => requestAnimationFrame(res));

            //Tiếp theo check xem product chứa biến thể sẽ được add này đã có folder id chưa
            //Nếu chưa thì tạo và đem xuống cập nhật cho product, nếu rồi thì lấy folder id đó đi lưu ảnh biến thể
            const folderIdResponse = await fetch(contextPath + `/get-folder-id?productId=${prodId}`)
            const folderIdResponseJson = await folderIdResponse.json()

            let folderId = folderIdResponseJson.folderId
            if (!folderIdResponseJson.hasImageInStorage)
                folderId = self.crypto.randomUUID();
            console.log(folderId)

            //Nếu không trùng thì ta lưu ảnh vào storage sau đó gửi url đó cùng các dữ liệu xuống servlet
            const downLoadUrl = await uploadImageToFirebase(form.variantImage.files[0], `products/${folderId}/variants`, form.sku.value.trim())

            //Sau khi lấy url từ fire storage thì ta đi cập nhật dữ liệu xuống dưới
            const variantData = {
                productId: prodId,
                folderId: folderId,
                name: form.variantNames.value.trim(),
                sku: form.sku.value.trim(),
                price: form.variantPrices.value.trim(),
                stock: form.variantStocks.value.trim(),
                gram: form.gram.value.trim(),
                color: form.color.value.trim(),
                size: form.size.value.trim(),
                imageUrl: downLoadUrl
            };

            const resultAdd = await fetch(contextPath + '/add-product-variant', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(variantData)
            })

            const resultAddJson = await resultAdd.json()
            hideLoading()
            alert(`${resultAddJson.message}`)
            submit.disabled = false
            submit.textContent = 'Cập nhật'
            closePopup.disabled = false

            closePopup.click()
            location.reload()
        } catch (e) {
            hideLoading()
            alert(`Lỗi ${e}`)

            submit.disabled = false
            submit.textContent = 'Cập nhật'
            closePopup.disabled = false
        }
    })
}

export const setupAddProductVar = (prodId) => {
    const popup = document.getElementById('popup-add-variant');
    const form = document.getElementById('variantForm');
    // Reset form trước khi mở
    form.reset();
    document.getElementById('variantPreview').innerHTML = ''
    // Hiển thị popup
    popup.style.display = 'block';
    processBtnSubmit(prodId)
}