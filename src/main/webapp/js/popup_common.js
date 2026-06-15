const popupCommon = {
    open(popupId) {
        const popup = document.getElementById(popupId);
        if (popup) popup.style.display = 'block';
    },
    close(popupId) {
        const popup = document.getElementById(popupId);
        if (popup) popup.style.display = 'none';
    },
    closeOnOutsideClick(event) {
        if (event.target.classList.contains('popup')) {
            event.target.style.display = 'none';
        }
    },
    initCloseButtons() {
        document.querySelectorAll('.popup-close-btn, .close-popup-btn, [data-close]').forEach(btn => {
            btn.addEventListener('click', () => {
                const popup = btn.closest('.popup');
                if (popup) popup.style.display = 'none';
            });
        });
        document.querySelectorAll('.popup').forEach(popup => {
            popup.addEventListener('click', this.closeOnOutsideClick);
        });
    },
    showLoading(containerId) {
        const container = document.getElementById(containerId);
        if (container) container.innerHTML = '<p style="text-align:center;padding:20px;">Đang tải dữ liệu...</p>';
    },
    showError(containerId, message) {
        const container = document.getElementById(containerId);
        if (container) container.innerHTML = '<p style="color:red;text-align:center;">' + message + '</p>';
    }
};

document.addEventListener('DOMContentLoaded', () => popupCommon.initCloseButtons());
