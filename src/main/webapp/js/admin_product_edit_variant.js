import {uploadImageToFirebase} from "./admin_product_add.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

const validateVariantForm = (form) => {
    const name = form.variantNames.value.trim();
    const price = form.variantPrices.value.trim();
    const stock = form.variantStocks.value.trim();
    const gram = form.gram.value.trim();
    const color = form.color.value.trim();
    const size = form.size.value.trim();

    let errors = [];

    // Kiểm tra tên biến thể
    if (name.length < 3) {
        errors.push("Tên biến thể phải có ít nhất 3 ký tự.");
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

    if (errors.length > 0) {
        alert("Có lỗi:\n- " + errors.join("\n- "));
        return false;
    }
    return true;
};

export const setupEvent = (varId, prodId) => {
    const close = document.getElementById('closeEditProdVar')
    const submit = document.getElementById('submitEditProdVar')
    //Close
    close.addEventListener('click', () => {
        document.getElementById('popup-edit-variant').style.display = 'none';
    })

    document.querySelector('input[name="editVariantImage"]').addEventListener('change', (ev) => {
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
    submit.addEventListener('click', async (e) => {
        e.preventDefault()
        try {
            const form = document.getElementById('editVariantForm')
            //kiểm tra form validate
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
                urlImage = await uploadImageToFirebase(fileInput[0], folderPath, form.variantSku.value.trim())
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
            alert(resultJson.message)
            submit.disabled = false
            submit.textContent = 'Chấp nhận'
            close.disabled = false

            if (resultJson.success) {
                close.click()
                document.getElementById('closeVariants').click()
                location.reload()
            }

        } catch (e) {
            submit.disabled = false
            submit.textContent = 'Chấp nhận'
            close.disabled = false
            hideLoading()
            alert(`Lỗi: ${e}`)
        }
    });
}