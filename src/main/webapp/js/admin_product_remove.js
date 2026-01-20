import {storage, ref, deleteObject, listAll} from "./firebase.js";

document.querySelectorAll(".edit-product-remove").forEach(function (el) {
    el.addEventListener('click', (ev) => {
        const id = el.dataset.id
        const name = el.dataset.name
        const isDelete = confirm(`Bạn có chắc muốn xóa sản phẩm ${name} không?`)
        if (!isDelete) return;
        fetch(contextPath + "/remove-product", {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({'productId': id})
        })
            .then(async response => {
                const map = await response.json()
                if (map.success) {
                    let storageMsg = "không cần dọn";

                    if (map.folderId) {
                        // Chờ đợi việc dọn dẹp hoàn tất
                        storageMsg = await deleteProductFolder(map.folderId);
                    }

                    alert(`Thành công: ${map.message}. \nHệ thống: ${storageMsg}`);
                    location.reload(); // Reload để cập nhật giao diện
                } else
                    alert(map.message)
            })
            .catch(err => alert(err))
    })
})

async function deleteProductFolder(folderId) {
    try {
        //Lấy danh sách tất cả file trong folderId (main/ và variants/)

        const subFolders = ['main', 'variants'];
        const deletePromises = [];

        for (const sub of subFolders) {
            const subRef = ref(storage, `products/${folderId}/${sub}`);
            const list = await listAll(subRef);

            // Thêm các file tìm thấy vào hàng chờ xóa
            list.items.forEach((fileRef) => {
                deletePromises.push(deleteObject(fileRef));
            });
        }

        // xóa các file song song
        await Promise.all(deletePromises);

        return "Đã xóa toàn bộ ảnh trên Firebase.";
    } catch (error) {
        return "Lỗi dọn dẹp Storage: " + error.message;
    }
}