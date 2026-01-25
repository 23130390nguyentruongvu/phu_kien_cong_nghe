const contextPath = window.contextPath

document.querySelectorAll('.wrap-content-order').forEach(btn => {
    btn.addEventListener('click', (ev) => {
        const orderId = btn.dataset.id
        window.location.href = contextPath + `/view-order-detail?orderId=${orderId}`
    });
});