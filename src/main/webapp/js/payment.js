import { showLoading, hideLoading } from './overlay_proccing.js';

document.addEventListener("DOMContentLoaded", function() {
    const btnChangeAddress = document.getElementsByClassName('change-address')[0];
    if (btnChangeAddress) {
        btnChangeAddress.addEventListener('click', (e) => {
            window.location.href = window.APP_CONFIG ? window.APP_CONFIG.addressUrl : 'address-user';
        });
    }

    const orderBtn = document.getElementById("place-order");
    const config = window.APP_CONFIG || {};
    if (orderBtn && !config.hasAddress) {
        orderBtn.addEventListener("click", function(e) {
            e.preventDefault();
            if (confirm("Bạn chưa có địa chỉ giao hàng mặc định. Bạn có muốn đi thêm địa chỉ không?")) {
                window.location.href = config.addressUrl;
            }
        });
    }

    // 4. Xử lý hiệu ứng Loading khi Submit form
    const formCheckout = document.querySelector('form[name="checkout"]');
    if (formCheckout) {
        formCheckout.addEventListener("submit", function(e) {
            // Kiểm tra lại một lần nữa trước khi hiện loading
            if (config.hasAddress) {
                showLoading();
            } else {
                e.preventDefault();
                alert("Vui lòng thêm địa chỉ trước khi thanh toán!");
            }
        });
    }
});