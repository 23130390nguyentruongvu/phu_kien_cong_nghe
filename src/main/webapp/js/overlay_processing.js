// 1. Tự động tạo cấu trúc HTML cho lớp phủ khi trang load admin_product.js
(function createLoadingOverlay() {
    const overlay = document.createElement('div');
    overlay.id = 'loading-overlay';
    overlay.innerHTML = `
        <div class="loader"></div>
        <div style="font-size: 20px; font-weight: bold;">Đang xử lý...</div>
    `;
    document.body.appendChild(overlay);
})();

// 2. Hàm công khai để bật/tắt
export const showLoading = () => {
    document.getElementById('loading-overlay').style.display = 'flex';
};

export const hideLoading = () => {
    document.getElementById('loading-overlay').style.display = 'none';
};