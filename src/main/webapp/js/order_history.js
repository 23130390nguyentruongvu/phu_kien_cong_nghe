const contextPath = window.contextPath

// document.querySelectorAll('.wrap-content-order').forEach(btn => {
//     btn.addEventListener('click', (ev) => {
//         const orderId = btn.dataset.id
//         window.location.href = contextPath + `/view-order-detail?orderId=${orderId}`
//     });
// });

// Sign order button - stop propagation so it doesn't trigger the order detail navigation
document.querySelectorAll('.btn-sign-order').forEach(btn => {
    btn.addEventListener('click', (ev) => {
        ev.stopPropagation();
        const orderId = btn.dataset.orderId;
        // TODO: implement sign order logic
        window.location.href = contextPath + `/process-sign-order?orderId=${orderId}`;
    });
});