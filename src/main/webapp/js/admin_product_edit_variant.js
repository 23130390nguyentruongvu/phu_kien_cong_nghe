import {uploadImageToFirebase} from "./admin_product_add.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

const validateVariantForm = (form) => {
    const validate = (name, message) => {
        const input = form.elements.namedItem(name);
        const error = form.querySelector(`[data-error-for="${name}"]`);
        input.setAttribute('aria-invalid', message ? 'true' : 'false');
        input.classList.toggle('input-invalid', Boolean(message));
        error.textContent = message;
        return !message;
    };
    const name = form.elements.namedItem('variantNames').value.trim();
    const priceText = form.elements.namedItem('variantPrices').value.trim();
    const price = Number(priceText);
    const stock = Number(form.elements.namedItem('variantStocks').value);
    const gram = Number(form.elements.namedItem('gram').value);
    const color = form.elements.namedItem('color').value.trim();
    const size = form.elements.namedItem('size').value.trim();
    const imageInput = form.elements.namedItem('editVariantImage');
    const image = imageInput.files[0];

    const valid = [
        validate('variantNames', name.length < 3 ? 'Tên biến thể phải có ít nhất 3 ký tự.'
            : name.length > 191 ? 'Tên biến thể tối đa 191 ký tự.' : ''),
        validate('variantPrices', !/^\d+(?:\.\d{1,2})?$/.test(priceText) || !Number.isFinite(price)
            || price <= 0 || price > 9999999999999.99
            ? 'Giá phải lớn hơn 0 và có tối đa 2 chữ số thập phân.' : ''),
        validate('variantStocks', !Number.isInteger(stock) || stock < 0 || stock > 2147483647
            ? 'Tồn kho phải là số nguyên từ 0 trở lên.' : ''),
        validate('gram', !Number.isInteger(gram) || gram <= 0 || gram > 2147483647
            ? 'Trọng lượng phải là số nguyên lớn hơn 0 gram.' : ''),
        validate('color', color.length < 2 ? 'Màu sắc phải có ít nhất 2 ký tự.'
            : color.length > 50 ? 'Màu sắc tối đa 50 ký tự.' : ''),
        validate('size', !size ? 'Kích thước không được để trống.'
            : size.length > 50 ? 'Kích thước tối đa 50 ký tự.' : '')
    ];
    const imageError = image && !image.type.startsWith('image/') ? 'Tệp tải lên phải là ảnh.' : '';
    imageInput.setAttribute('aria-invalid', imageError ? 'true' : 'false');
    imageInput.classList.toggle('input-invalid', Boolean(imageError));
    form.querySelector('[data-error-for="editVariantImage"]').textContent = imageError;
    valid.push(!imageError);
    return valid.every(Boolean);
};

export const setupEvent = (varId, prodId) => {
    let close = document.getElementById('closeEditProdVar');
    const newClose = close.cloneNode(true);
    close.parentNode.replaceChild(newClose, close);
    close = newClose;

    let submit = document.getElementById('submitEditProdVar')
    const newSubmit = submit.cloneNode(true);
    submit.parentNode.replaceChild(newSubmit, submit);
    submit = newSubmit;

    //Close
    close.addEventListener('click', () => {
        document.getElementById('popup-edit-variant').style.display = 'none';
    })
    let imgInput = document.querySelector('input[name="editVariantImage"]');
    const newImgInput = imgInput.cloneNode(true);
    imgInput.parentNode.replaceChild(newImgInput, imgInput);
    imgInput = newImgInput;

    imgInput.addEventListener('change', (ev) => {
        const preview = document.getElementById('editVariantPreview');
        const inputFile = ev.target; // Lấy chính input đang chọn file
        preview.innerHTML = '';

        Array.from(inputFile.files).forEach((file) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                // Tạo container chứa ảnh + nút xoá
                const wrapper = document.createElement("div");
                Object.assign(wrapper.style, {
                    display: "inline-block",
                    position: "relative",
                    margin: "5px"
                });

                // Ảnh preview
                const img = document.createElement("img");
                img.src = e.target.result;
                img.alt = "Ảnh sản phẩm";
                Object.assign(img.style, {
                    width: "80px",
                    height: "80px",
                    objectFit: "cover"
                });

                // Nút xoá
                const btn = document.createElement("button");
                btn.innerHTML = "&times;"; // Dấu x đẹp hơn
                Object.assign(btn.style, {
                    position: "absolute",
                    top: "0",
                    right: "0",
                    background: "rgba(255, 0, 0, 0.7)",
                    color: "white",
                    border: "none",
                    borderRadius: "50%",
                    width: "20px",
                    height: "20px",
                    cursor: "pointer",
                    lineHeight: "18px",
                    textAlign: "center"
                });

                // Sự kiện click nút xoá
                btn.addEventListener("click", () => {
                    wrapper.remove();
                    inputFile.value = "";
                });

                wrapper.appendChild(img);
                wrapper.appendChild(btn);
                preview.appendChild(wrapper);
            };
            reader.readAsDataURL(file);
        });
    });

    //submit
    const form = document.getElementById('editVariantForm');
    form.addEventListener('input', () => {
        if (form.dataset.validationAttempted === 'true') validateVariantForm(form);
    });
    form.addEventListener('change', () => {
        if (form.dataset.validationAttempted === 'true') validateVariantForm(form);
    });
    form.addEventListener('submit', async (e) => {
        e.preventDefault()
        try {
            form.dataset.validationAttempted = 'true';
            if (!validateVariantForm(form)) return;
            if (!confirm(`Bạn có chắc muốn cập nhật lại dữ liệu cho biến thể mã ${varId} không?`)) return;
            submit.disabled = true
            submit.textContent = 'Đang cập nhật...'
            close.disabled = true
            showLoading()

            await new Promise(res => requestAnimationFrame(res));

            //Kiểm tra xem admin có chọn ảnh mới hay không
            const fileInput = form.editVariantImage.files
            const updateImage = fileInput.length > 0
            let folderId
            let urlImage = document.querySelector('#editVariantForm .old-variant-img').dataset.url
            if (updateImage) {
                //Nếu cần sửa lại ảnh thì tiến hành lấy folder id, nếu product chung chưa có thì ta tạo mới
                const getFolderId = await fetch(contextPath + `/get-folder-id?variantId=${varId}`)
                const getFolderIdJson = await getFolderId.json()
                folderId = getFolderIdJson.folderId
                if (!getFolderIdJson.hasImageInStorage)
                    folderId = self.crypto.randomUUID();

                const folderPath = `products/${folderId}/variants`
                //ghi đè ảnh mới lên storage và lấy url download cho urlImage
                urlImage = await uploadImageToFirebase(
                    fileInput[0], folderPath, form.elements.namedItem('variantSku').value.trim()
                )
            }

            const editVariant = {
                variantId: varId,
                productId: prodId,
                folderId: folderId,
                hasUpdateImage: updateImage,
                name: form.variantNames.value.trim(),
                price: form.variantPrices.value.trim(),
                stock: form.variantStocks.value.trim(),
                gram: form.gram.value.trim(),
                color: form.color.value.trim(),
                size: form.size.value.trim(),
                imageUrl: urlImage
            };

            const result = await fetch(contextPath + '/update-variant', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(editVariant)
            });

            const resultJson = await result.json()

            hideLoading()
            submit.disabled = false
            submit.textContent = 'Chấp nhận'
            close.disabled = false

            if (resultJson.success) {
                close.click()
                document.getElementById('closeVariants').click()
                location.reload()
            } else {
                setVariantFormError(form, 'variantPrices', resultJson.message || 'Không thể cập nhật biến thể.');
            }

        } catch (e) {
            submit.disabled = false
            submit.textContent = 'Chấp nhận'
            close.disabled = false
            hideLoading()
            setVariantFormError(form, 'variantPrices', `Không thể cập nhật biến thể: ${e.message}`);
        }
    });
}

function setVariantFormError(form, fieldName, message) {
    const input = form.elements.namedItem(fieldName);
    const error = form.querySelector(`[data-error-for="${fieldName}"]`);
    input.setAttribute('aria-invalid', 'true');
    input.classList.add('input-invalid');
    error.textContent = message;
}