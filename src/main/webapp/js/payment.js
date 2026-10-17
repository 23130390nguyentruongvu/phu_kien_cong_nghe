import {showLoading, hideLoading} from './overlay_processing.js';

const contextPath = window.contextPath

document.addEventListener("DOMContentLoaded", function () {
    const voucherSection = document.querySelector(".checkout-vouchers");
    const updateVoucherTotal = () => {
        if (!voucherSection) return;
        const selectedVoucher = voucherSection.querySelector('input[name="voucherId"]:checked');
        const discount = selectedVoucher ? Number(selectedVoucher.dataset.discount || 0) : 0;
        const baseTotal = Number(voucherSection.dataset.subtotal || 0)
            + Number(voucherSection.dataset.shipping || 0);
        const discountLine = document.getElementById("voucher-discount-line");
        const discountAmount = document.getElementById("voucher-discount-amount");
        const total = document.getElementById("payment-total");
        if (discountLine) discountLine.hidden = discount <= 0;
        if (discountAmount) {
            discountAmount.textContent = `−${new Intl.NumberFormat("vi-VN", {maximumFractionDigits: 0}).format(discount)}₫`;
        }
        if (total) {
            const payable = Math.max(0, baseTotal - discount);
            total.textContent = `${new Intl.NumberFormat("vi-VN", {maximumFractionDigits: 0}).format(payable)}₫`;
        }
    };
    if (voucherSection) {
        voucherSection.addEventListener("change", (event) => {
            if (event.target.matches('input[name="voucherId"]')) updateVoucherTotal();
        });
        updateVoucherTotal();
    }

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
                        paymentMethodId: formCheckout.paymentMethodId.value,
                        voucherId: formCheckout.querySelector('input[name="voucherId"]:checked')?.value || ""
                    })
                });

                const result = await response.json()
                hideLoading();
                if(result.success){
                    alert(result.message)
                    if(confirm("Bạn có muốn ký đơn hàng?")){
                        window.location.href = contextPath + '/order-history'
                    }else{
                        window.location.href = contextPath + '/'
                    }
                }else{
                    alert(result.message)
                }

            } catch (e) {
                alert(e)
            } finally {
                hideLoading()
            }
        });
    }
});