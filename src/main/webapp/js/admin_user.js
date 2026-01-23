import {setupEvent} from "./admin_user_add.js";
import {deleteObject, getDownloadURL, ref, storage, uploadBytesResumable} from "./firebase.js";

//delete
export async function deleteImageByPath(filePath) {
    if (!filePath || typeof filePath !== 'string') {
        return {
            success: false,
            message: "Đường dẫn file không hợp lệ hoặc trống."
        };
    }

    try {
        const fileRef = ref(storage, filePath);
        if (filePath.startsWith("http")) {
            return {
                success: false,
                message: "Vui lòng truyền path (ví dụ: folder/image.jpg), không phải URL."
            };
        }

        await deleteObject(fileRef);

        return {
            success: true,
            message: `Đã xóa ảnh thành công tại đường dẫn: ${filePath}`
        };

    } catch (error) {
        console.error('Lỗi khi xóa file Firebase:', error);

        let errorMsg = "Lỗi hệ thống khi xóa ảnh.";
        if (error.code === 'storage/object-not-found') {
            errorMsg = "File không tồn tại trên hệ thống (có thể đã bị xóa trước đó).";
        } else if (error.code === 'storage/unauthorized') {
            errorMsg = "Bạn không có quyền xóa file này.";
        }

        return {
            success: false,
            message: errorMsg
        };
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

const popupAdd = document.getElementById('popup-user');

//Show popup thêm user
document.querySelector('.edit-user .edit-user-add-user').addEventListener('click', () => {
    popupAdd.style.display = 'block'
    const form = document.getElementById('user-form')
    form.reset()
    setupEvent(form)
})

document.getElementById('closeAddUser').addEventListener('click', () => {
    popupAdd.style.display = 'none'
    document.getElementById('addUserImagePreview').innerHTML = ''
})

//show popup chỉnh sửa user
