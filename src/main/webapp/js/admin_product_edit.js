import {uploadImageToFirebase} from "./admin_product_add.js";
import {deleteImagesByUrls} from "./admin_product_remove.js"

// Khi chọn file thì hiển thị preview
document.getElementById('addImageInput').addEventListener('change', function (event) {
    const preview = document.getElementById('editProductPreview');
    preview.innerHTML = ''; // clear cũ

    Array.from(event.target.files).forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function (e) {
            const div = document.createElement('div');
            div.classList.add('image-item', 'edit-image-upload');
            div.innerHTML = `
                <img src="${e.target.result}" alt="Ảnh sản phẩm" 
                     style="width:80px;height:80px;object-fit:cover;margin:5px;">
                <br>
                Ảnh chính <input type="radio" style="width: 20px; height: 20px" class="mainImage" name="mainImage" value="${index}">
            `;

            preview.appendChild(div);
        };
        reader.readAsDataURL(file);
    });
    //nếu admin không còn chọn ảnh lên thì trong trường hợp radio trước đó đã được chọn ở ảnh thêm nhưng sau đó không thêm
    //ảnh nữa thì ta sẽ set lại vị trí mặc định là 0 để đảm bảo ảnh chính luôn được chon
    if (event.target.files.length === 0) {
        let posCheck = 0
        const radios = document.querySelectorAll('#popup-edit .content-edit-product .mainImage')
        const checkboxRemove = document.querySelectorAll('#popup-edit .content-edit-product .edit-remove-img')
        radios.forEach((btn, index) => {
            if (btn.checked) {
                posCheck = index
            }
        })
        if (radios.length > 0) {
            radios[posCheck].checked = true;
            checkboxRemove[posCheck].checked = false;
        }
    }
});

function validateProductForm() {
    // const fileSize = document.getElementById('addImageInput').files.length
    // const mainImageRadio = document.querySelectorAll('#popup-edit .content-edit-product .mainImage')
    // const checkboxRemove = document.querySelectorAll('#popup-edit .content-edit-product .edit-remove-img')

    const name = document.getElementById('name-product').value.trim();
    const warranty = document.getElementById('warranty').value.trim();
    const shortDesc = document.getElementById('short-desc').value.trim();
    const longDesc = document.getElementById('long-desc').value.trim();

    let errors = [];


    // Kiểm tra tên sản phẩm
    if (name.length < 3) {
        errors.push("Tên sản phẩm phải có ít nhất 3 ký tự.");
    }

    // Kiểm tra thời hạn bảo hành
    if (!warranty || isNaN(warranty) || Number(warranty) <= 0) {
        errors.push("Thời hạn bảo hành phải là số dương.");
    }

    // Kiểm tra mô tả ngắn
    if (shortDesc.length < 5) {
        errors.push("Mô tả ngắn phải có ít nhất 5 ký tự.");
    }

    // Kiểm tra mô tả dài
    if (longDesc.length < 10) {
        errors.push("Mô tả dài phải có ít nhất 10 ký tự.");
    }

    // Hiển thị kết quả
    if (errors.length > 0) {
        alert("Có lỗi:\n- " + errors.join("\n- "));
        return false; // không cho submit
    }

    return true; // hợp lệ
}

async function handleUpdateImages(files, folderId) {
    // Chuyển FileList thành Array để dùng .map()
    const fileArray = Array.from(files);

    try {
        console.log("Bắt đầu tải ảnh mới...");

        // Tạo danh sách các Promise upload
        const uploadPromises = fileArray.map((file, index) => {
            const fileName = `prod_${Date.now()}_${index}`;
            const folderPath = `products/${folderId}/main`;

            return uploadImageToFirebase(file, folderPath, fileName);
        });

        // Đợi tất cả hoàn thành -> Kết quả trả về là mảng URL đúng thứ tự đã chọn
        const newImageUrls = await Promise.all(uploadPromises);

        return newImageUrls

    } catch (error) {
        throw error
    }
}

export const setEvent = () => {
    //reset preview image
    const preview = document.getElementById('editProductPreview');
    preview.innerHTML = ''; // clear cũ
    const imageInput = document.getElementById('addImageInput')
    imageInput.value = "";

    //set sự kiện click vào checkbox xóa (đảm bảo admin không chọn xóa được ảnh chính)
    document.querySelectorAll('#popup-edit .content-edit-product .edit-remove-img')
        .forEach(checkbox => {
            checkbox.addEventListener('change', () => {
                if (!checkbox.checked) return;
                const imageItem = checkbox.closest('.image-item');
                if (imageItem) {
                    const radio = imageItem.querySelector('input[type="radio"][name="mainImage"]');
                    if (radio.checked) {
                        alert("Ảnh chính của sản phẩm không thể bị xóa, vui lòng chọn lại ảnh chính và thử lại!")
                        checkbox.checked = false
                    }
                }
            })
        })

    //set sự kiện khi click vào radio chọn ảnh chính sẽ hủy checked xóa
    document.querySelectorAll('#popup-edit .content-edit-product .mainImage')
        .forEach(radio => {
            radio.addEventListener('change', () => {
                if (!radio.checked) return;
                const imageItem = radio.closest('.image-item');
                if (imageItem) {
                    const checkbox = imageItem.querySelector('input[type="checkbox"][name="edit-remove-img"]');
                    if (checkbox.checked) {
                        checkbox.checked = false
                    }
                }
            })
        })
}


export const setEventConfirm = (prodId) => {
    document.getElementById("submit-update-product")
        .addEventListener('click', async (e) => {
                e.preventDefault()

                //check các input có rỗng hay không
                const isOk = validateProductForm()
                if (!isOk) return;
                const okConfirm = confirm("Bạn có đồng ý cập nhật lại thông tin sản phẩm không?")
                if (!okConfirm) return;
                const btnSubmit = document.getElementById('submit-update-product')
                const btnCancel = document.getElementById('closeEdit')
                btnCancel.disabled = true
                btnSubmit.textContent = 'Đang xử lí...'
                btnSubmit.disabled = true

                try {
                    const files = document.getElementById('addImageInput').files
                    const imagesUpload = []
                    //lấy ra folderId
                    const response = await fetch(contextPath + `/get-folder-id?productId=${prodId}`)
                    const responseParse = await response.json()
                    let folderId = responseParse.folderId
                    let removeImageInStorage = !!responseParse.folderId //Nếu là undefined hoặc null thì nó false

                    //kiểm tra có thêm ảnh mới không, nếu có thì upload lên firestorage
                    if (files.length > 0) {
                        if (!responseParse.hasImageInStorage) {
                            folderId = self.crypto.randomUUID();
                            removeImageInStorage = false
                        }

                        const urlDownloads = await handleUpdateImages(files, folderId)
                        imagesUpload.push(...urlDownloads);
                        //Nếu đã lấy url down từ các ảnh upload thì đi cập nhật lại url mới cho các ảnh thuộc edit image upload
                        const uploadItems = document.querySelectorAll('.edit-image-upload');
                        uploadItems.forEach((item, index) => {
                            const radio = item.querySelector('.mainImage');
                            if (radio) {
                                radio.value = imagesUpload[index]; // Gán URL thật từ Firebase vào value của radio
                            }
                        });
                    }

                    //Lấy ra các url cần xóa và xóa các ảnh đó trên storage
                    const removedUrls = []
                    document.querySelectorAll('.product-images .image-item').forEach(item => {
                        const checkbox = item.querySelector('input[type="checkbox"].edit-remove-img');
                        if (checkbox && checkbox.checked) {
                            const img = item.querySelector('img');
                            if (img && img.dataset.url) {
                                removedUrls.push(img.dataset.url);
                            }
                        }
                    });

                    //tiến hành xóa ảnh nếu thỏa điều kiện
                    if (removedUrls.length > 0 && removeImageInStorage) {
                        const map = await deleteImagesByUrls(removedUrls)
                    }

                    //Lấy ra url main
                    const selectedRadio = document.querySelector('input[name="mainImage"]:checked');
                    const mainUrl = selectedRadio
                        ? selectedRadio.value
                        : document.querySelectorAll('input[name="mainImage"]')[0].value;

                    //Tạo đối tượng json
                    const productData = {
                        id: prodId,
                        active: document.getElementsByClassName('edit-isActive')[0].checked,
                        name: document.getElementById('name-product').value.trim(),
                        warrantyPeriod: parseInt(document.getElementById('warranty').value.trim()),
                        subtitle: document.getElementById('short-desc').value.trim(),
                        description: document.getElementById('long-desc').value.trim(),
                        folderId: folderId,
                        newImages: imagesUpload, // Đây là mảng các URL từ Firebase,
                        removeUrls: removedUrls,
                        imageMainUrl: mainUrl
                    };

                    const res = await fetch(contextPath + "/update-product", {
                        method: 'POST',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        body: JSON.stringify(productData)
                    })

                    const resFinal = await res.json()
                    alert(`Thông báo: ${resFinal.message}`)
                    btnCancel.disabled = false
                    btnSubmit.textContent = 'Cập nhật'
                    btnSubmit.disabled = false
                    const btnClose = document.getElementById('closeEdit');
                    if (btnClose) btnClose.click();
                    location.reload();
                } catch (e) {
                    btnCancel.disabled = false
                    btnSubmit.textContent = 'Cập nhật'
                    btnSubmit.disabled = false
                    alert(`Thông báo: ${e}`)
                }
            }
        )
}