import {storage, ref, deleteObject} from "./firebase.js";

export async function deleteSkuImage(folderId, sku) {
    // Nếu không có SKU thì không cần xóa
    if (!sku) return {success: false, message: "Không tìm thấy mã SKU để xóa ảnh."};

    const desertRef = ref(storage, `products/${folderId}/variants/${sku}`);

    try {
        await deleteObject(desertRef);
        console.log(`Đã xóa ảnh SKU: ${sku} trên Firebase`);
        return {
            success: true,
            message: `Đã xóa ảnh của SKU: ${sku} trên hệ thống lưu trữ.`
        };
    } catch (error) {
        if (error.code === 'storage/object-not-found') {
            console.warn("Ảnh không tồn tại trên Storage.");
            return {
                success: true,
                message: "Dữ liệu ảnh không tồn tại hoặc đã được xóa trước đó."
            };
        } else {
            console.error("Lỗi xóa Firebase:", error);
            return {
                success: false,
                message: "Lỗi khi xóa ảnh trên Cloud: " + error.message
            };
        }
    }
}

export async function deleteProductVariant(variantId) {
    return fetch(contextPath + "/remove-product-variant", {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: new URLSearchParams({'variantId': variantId})
    }).then(res => res.json());
}

export const setEventRemoveVariant = (sizeVariants, prodId) => {
    //xử lí sự kiện xóa biến thể
    /*
    đầu tiên gọi xuống JsonGetFolderServlet để lấy các thông tin về folderId, nếu folderId không có thì
    tức hình ảnh sản phẩm đó chưa từng được lưu trên storage thì ta chỉ việc xóa sản phẩm dưới db, còn nếu có thì
    sau khi xóa xong bên trong JsonRemoveProductServlet sẽ có trả về mã SKU để dựa vào đấy truy ra đường dẫn
    mà storage đang lưu ảnh của biến thể đó
     */
    document.querySelectorAll(".edit-product-var-remove").forEach(function (el) {
        el.addEventListener("click", async function () {

            const id = this.dataset.id; // lấy product variant id
            if (sizeVariants < 2) {
                alert("Không thể xóa biến thể " + id + " khi sản phẩm chung chỉ có " + sizeVariants + " biến thể");
            } else {
                const isDelete = confirm("Bạn có chắc chắn muốn xóa biến thể mang mã: " + id + " không?")
                if (isDelete) {
                    try {
                        // lấy thông tin folderId và SKU trước khi xóa trong DB
                        const folderRes = await fetch(`${contextPath}/get-folder-id?variantId=${id}`);
                        const folderData = await folderRes.json();

                        // Gọi Servlet xóa trong Database
                        const res = await deleteProductVariant(id);

                        if (res.success) {
                            // Nếu DB xóa xong và trước đó có ảnh trên storage thì mới xóa Firebase
                            if (folderData.hasImageInStorage) {
                                const deleteStorage = await deleteSkuImage(folderData.folderId, res.sku);
                                alert(res.message + " và " + deleteStorage.message)
                            } else
                                alert(res.message);
                            // Reload lại danh sách biến thể sau khi xóa thành công
                            await showPopupVariants(prodId);
                        } else {
                            alert("Lỗi xóa DB: " + res.message);
                        }
                    } catch (err) {
                        console.error(err);
                        alert("Lỗi hệ thống: " + err.message);
                    }

                }
            }
        });
    });
}


//set sự kiện ajax cho show popup variants
export const showPopupVariants = async (prodId) => {
    const container = document.getElementById('variant-data-container');
    const popup = document.getElementById('popup-variants');

    fetch(`${contextPath}/get-variants?productId=${prodId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi Server: " + response.status);
            }
            return response.text();
        })
        .then(html => {
            container.innerHTML = html;
            const sizeVariants = document.querySelectorAll(".product-variant-item").length
            setEventRemoveVariant(sizeVariants, prodId)

        })
        .catch(err => {
            console.log(err)
            container.innerHTML = `<tr><td colspan="7" style="color:red; text-align:center;">Không thể tải dữ liệu</td></tr>`;
        })
}
