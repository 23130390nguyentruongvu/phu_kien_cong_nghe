import {hideLoading, showLoading} from "./overlay_processing.js";
import {getDownloadURL, ref, storage, uploadBytesResumable} from "./firebase.js";

const contextPath = window.contextPath
document.addEventListener('DOMContentLoaded', () => {
    async function uploadImageToFirebase(file, folderPath, fileName) {
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

    const avatarInput = document.getElementById('avatarInput');

    avatarInput.addEventListener('change', async (ev) => {
        const input = ev.target;
        try {
            if (input.files && input.files[0]) {
                const file = input.files[0];

                const id = input.dataset.id
                const username = input.dataset.username
                console.log(contextPath)

                //Kiểm tra định dạng
                if (!file.type.startsWith('image/')) {
                    alert("Vui lòng chọn một file ảnh!");
                    return;
                }
                showLoading()

                //kiểm tra người dùng này đã có folder id chưa
                const folderIdRes = await fetch(contextPath + `/get-folder-id-user?userId=${id}`)

                const folderIdJson = await folderIdRes.json()
                let folderId = folderIdJson.folderId
                //Nếu người dùng có folder id thì lấy folder id đó đi cập nhật lại trên storage
                //Nếu không ta tạo uuid và tạo ảnh trên storage rồi update cho user
                if (!folderIdJson.hasImageInStorage) {
                    folderId = self.crypto.randomUUID();
                }
                const urlDownload = await uploadImageToFirebase(input.files[0], `users/${folderId}`, username)

                const result = await fetch(contextPath + '/update-avatar-user', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: new URLSearchParams({'userId': id, 'url': urlDownload, 'folderId': folderId})
                });
                const resultJson = await result.json()
                alert(resultJson.message)
                location.reload()
            }
        } catch (e) {
            alert(e)
        } finally {
            hideLoading()
        }
    });
})