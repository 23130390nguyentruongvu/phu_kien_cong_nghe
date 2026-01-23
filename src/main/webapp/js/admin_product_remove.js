import {storage, ref, deleteObject, listAll} from "./firebase.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

document.querySelectorAll(".edit-product-remove").forEach(function (el) {
    el.addEventListener('click', async (ev) => {
        const id = el.dataset.id
        const name = el.dataset.name
        const isDelete = confirm(`Bạn có chắc muốn xóa sản phẩm ${name} không?`)
        if (!isDelete) return;
        showLoading()
        await new Promise(res => requestAnimationFrame(res));
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

                    hideLoading()
                    alert(`Thành công: ${map.message}. \nHệ thống: ${storageMsg}`);
                    location.reload(); // Reload để cập nhật giao diện
                } else {
                    hideLoading()
                    alert(map.message)
                }
            })
            .catch(err => {
                hideLoading()
                alert(err)
            })
    })
})

export async function deleteProductFolder(folderId) {
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

export const deleteImagesByUrls = async (urls) => {
    if (!urls || urls.length === 0) {
        return {success: true, message: "Không có ảnh nào để xóa."};
    }

    try {
        const deletePromises = urls.map(async (imageUrl) => {
            //đảm bảo url đấy phải có định dạng của storage, tránh lỗi
            if (imageUrl && imageUrl.includes("firebasestorage.googleapis.com")) {
                try {
                    const imageRef = ref(storage, imageUrl);
                    await deleteObject(imageRef);
                    console.log(`Đã xóa thành công: ${imageUrl}`);
                } catch (error) {
                    console.warn(`Lỗi khi xóa file Firebase: ${imageUrl}`, error.code);
                }
            } else {
                console.log(`Bỏ qua URL không phải của Firebase: ${imageUrl}`);
            }
        });

        await Promise.all(deletePromises);

        return {
            success: true,
            message: "Hoàn tất xử lý dọn dẹp ảnh Storage."
        };
    } catch (error) {
        console.error("Lỗi hệ thống khi xử lý mảng ảnh:", error);
        return {success: false, message: error.message};
    }
};