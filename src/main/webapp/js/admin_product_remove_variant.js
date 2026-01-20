import { storage, ref, deleteObject } from "./firebase.js";

export async function deleteSkuImage(folderId, sku) {
    // Nếu không có SKU thì không cần xóa
    if (!sku) return { success: false, message: "Không tìm thấy mã SKU để xóa ảnh." };

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
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ 'variantId': variantId })
    }).then(res => res.json());
}