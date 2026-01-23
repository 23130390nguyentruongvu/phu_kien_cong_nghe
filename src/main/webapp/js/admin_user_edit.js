import {uploadImageToFirebase} from "./admin_user.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

const setupEventForPopup = (userId, userName, popup) => {
    //sự kiện thêm ảnh, hiện ảnh lên preview, cho phép xóa ảnh preview

    const inputFile = document.getElementById("fileEditUser");
    const previewDiv = document.getElementById("editUserPreview");

    inputFile.addEventListener("change", () => {
        previewDiv.innerHTML = ""; // xoá preview cũ

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
                img.alt = "Avatar Preview";
                Object.assign(img.style, {
                    width: "120px",
                    height: "120px",
                    objectFit: "cover"
                });

                // Nút xoá
                const btn = document.createElement("button");
                btn.innerHTML = "&times;";
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
                    inputFile.value = ""; // reset input file
                    previewDiv.innerHTML = '';
                });

                wrapper.appendChild(img);
                wrapper.appendChild(btn);
                previewDiv.appendChild(wrapper);
            };
            reader.readAsDataURL(file);
        });
    });

    //Sự kiện đóng
    document.getElementById('closeEditUser').addEventListener('click', () => popup.style.display = 'none')

    //Sự kiện submit
    document.getElementById('submitEditUser').addEventListener('click', async (e) => {
        e.preventDefault()
        try {
            const form = document.getElementById('editUserForm')
            if (!form.name.value.trim()) {
                alert('Không được để trống họ tên người dùng')
                return;
            }

            if (!confirm('Bạn có chắc chắn muốn cập nhật dữ liệu của người dùng này?')) return;
            showLoading()

            //kiểm tra admin có thay đổi ảnh user không
            const hasImage = form.fileAvatar.files.length > 0
            let folderId
            let urlDownload

            if (hasImage) {
                //get folder id
                const requestFolderId = await fetch(contextPath + `/get-folder-id-user?userId=${userId}`)
                const requestFolderIdJson = await requestFolderId.json()

                folderId = requestFolderIdJson.folderId
                if (!requestFolderIdJson.hasImageInStorage) {
                    //k có folder id => ta sẽ đi tạo folder id cho user này
                    folderId = self.crypto.randomUUID();
                }
                urlDownload = await uploadImageToFirebase(form.fileAvatar.files[0], `users/${folderId}`, userName)
            }
            const userEdit = {
                userId: userId,
                folderId: folderId,
                hasImage: hasImage,
                url: urlDownload,
                role: form.role.value.trim(),
                fullName: form.name.value.trim()
            }

            const response = await fetch(contextPath + '/edit-user', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userEdit)
            });

            const result = await response.json()
            alert(result.message)
            document.getElementById('closeEditUser').click()
            location.reload()
        } catch (e) {
            alert(e.messages)
            document.getElementById('closeEditUser').click()
            location.reload()
        } finally {
            hideLoading()
        }
    });
}

//thiết lập sự kiện click cho nút sửa
document.querySelectorAll('.edit-user-update').forEach(btn => {
    btn.addEventListener('click', async (e) => {
        const userId = btn.dataset.id;
        const userName = btn.dataset.username

        //Mở popup edit
        const requestGetUser = await fetch(contextPath + `/get-user-edit?userId=${userId}`)
        const html = await requestGetUser.text()

        const popupEdit = document.getElementById('popup-update-user')
        popupEdit.innerHTML = html
        popupEdit.style.display = 'block'

        //setup ev
        setupEventForPopup(userId, userName, popupEdit)
    });
});