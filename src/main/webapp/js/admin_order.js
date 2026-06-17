// Popup sửa thông tin đơn hàng
document.querySelectorAll('.edit-order-update').forEach(btn => {
    btn.addEventListener('click', async () => {
        const orderId = btn.dataset.id;
        document.getElementById('edit-order-id').textContent = orderId;
        popupCommon.open('popup-edit-order');
        popupCommon.showLoading('content-edit-order');

        try {
            const response = await fetch(window.contextPath + '/get-order-edit?orderId=' + orderId);
            if (!response.ok) throw new Error('Server error: ' + response.status);
            const html = await response.text();
            document.getElementById('content-edit-order').innerHTML = html;
        } catch (err) {
            console.error('Lỗi khi load form edit:', err);
            popupCommon.showError('content-edit-order', 'Không thể tải form chỉnh sửa đơn hàng');
        }
    });
});

document.getElementById('editOrderForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = {};
    const orderDetailIds = [];
    const variantIds = [];
    const quantities = [];

    formData.forEach((value, key) => {
        if (key === 'orderDetailId') {
            orderDetailIds.push(value);
        } else if (key === 'variantId') {
            variantIds.push(value);
        } else if (key === 'quantity') {
            quantities.push(value);
        } else {
            data[key] = value;
        }
    });
    data['orderDetailIds'] = orderDetailIds;
    data['variantIds'] = variantIds;
    data['quantities'] = quantities;

    if (!confirm('Sau khi chỉnh sửa, đơn hàng sẽ chuyển trạng thái chờ người dùng ký lại. Tiếp tục?')) return;

    try {
        const response = await fetch(window.contextPath + '/update-order', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        const result = await response.json();
        alert(result.message);
        if (result.success) {
            popupCommon.close('popup-edit-order');
            window.location.reload();
        }
    } catch (err) {
        console.error('Lỗi khi cập nhật đơn hàng:', err);
        alert('Lỗi: ' + err.message);
    }
});

document.getElementById('closeEditOrder').addEventListener('click', () => popupCommon.close('popup-edit-order'));
