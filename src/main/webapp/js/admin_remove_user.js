import {deleteImageByPath} from "./admin_user.js?v=20261028-local-avatar";
import {hideLoading, showLoading} from "./overlay_processing.js";

document.querySelectorAll('.edit-user-remove').forEach(btn => {
    btn.addEventListener('click', async () => {
        try {
            const userId = btn.dataset.id
            const username = btn.dataset.username
            if (!confirm('Bạn có chắc muốn xóa người dùng này không?')) return;
            showLoading()

            const avatarUrl = btn.closest('tr').querySelector('.avatar-img').src
            const isLocalAvatar = avatarUrl.includes('/images/user/')
                || avatarUrl.includes('/admin-user-avatars/')
            if (!isLocalAvatar) {
                //Lấy folder id
                const getFolderId = await fetch(contextPath + `/get-folder-id-user?userId=${userId}`);
                const getFolderIdJson = await getFolderId.json()

                if (getFolderIdJson.hasImageInStorage) {
                    const resDelete = await deleteImageByPath(`users/${getFolderIdJson.folderId}/${username}`)
                    if (!resDelete.success) throw new Error(resDelete.message)
                }
            }

            const response = await fetch(contextPath + '/delete-user', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({userId: userId})
            });
            const responseJson = await response.json()
            alert(responseJson.message)
        } catch (e) {
            alert(e.message)
        } finally {
            hideLoading()
            location.reload()
        }

    });

});