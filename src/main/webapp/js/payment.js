import {showLoading, hideLoading} from './overlay_processing.js';

const contextPath = window.contextPath

document.addEventListener("DOMContentLoaded", function () {
    const btnChangeAddress = document.getElementsByClassName('change-address')[0];
    if (btnChangeAddress) {
        btnChangeAddress.addEventListener('click', (e) => {
            window.location.href = window.APP_CONFIG ? window.APP_CONFIG.addressUrl : 'address-user';
        });
    }

    const orderBtn = document.getElementById("place-order");
    const config = window.APP_CONFIG || {};
    if (orderBtn && !config.hasAddress) {
        orderBtn.addEventListener("click", function (e) {
            e.preventDefault();
            if (confirm("Bạn chưa có địa chỉ giao hàng mặc định. Bạn có muốn đi thêm địa chỉ không?")) {
                window.location.href = config.addressUrl;
            }
        });
    }

    // Xử lý hiệu ứng Loading khi Submit form
    const formCheckout = document.getElementById('formCheckout');
    if (formCheckout) {
        formCheckout.addEventListener("submit", async function (e) {
            e.preventDefault()
            try {
                showLoading()
                const response = await fetch(`${contextPath}/checkout`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: new URLSearchParams({
                        selectedAddressId: formCheckout.selectedAddressId.value,
                        note: formCheckout.note.value,
                        paymentMethodId: formCheckout.paymentMethodId.value
                    })
                });

                const result = await response.json()
                alert(result.message)
                if (result.success) {
                    window.location.href = contextPath + '/'
                }
            } catch (e) {
                alert(e)
            } finally {
                hideLoading()
            }
        });
    }
});