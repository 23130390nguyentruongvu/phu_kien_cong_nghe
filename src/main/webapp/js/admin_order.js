// Popup xem chi tiết đơn hàng
document.querySelectorAll('.edit-order-view').forEach(btn => {
    btn.addEventListener('click', async () => {
        const orderId = btn.dataset.id;
        const container = document.getElementById('order-detail-container');

        popupCommon.open('popup-order-detail');
        document.getElementById('display-order-id').textContent = orderId;
        popupCommon.showLoading('order-detail-container');

        try {
            const response = await fetch(window.contextPath + '/get-order-detail?orderId=' + orderId);
            if (!response.ok) throw new Error('Server error: ' + response.status);
            const html = await response.text();
            container.innerHTML = html;
        } catch (error) {
            console.error('Lỗi khi tải chi tiết đơn hàng:', error);
            popupCommon.showError('order-detail-container', 'Lỗi: ' + error.message);
        }
    });
});

document.getElementById('closeOrderDetail').onclick = () => popupCommon.close('popup-order-detail');

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

// Popup thêm sản phẩm vào đơn
document.querySelectorAll('.edit-order-add-item').forEach(btn => {
    btn.addEventListener('click', () => {
        const orderId = btn.dataset.id;
        document.getElementById('add-item-order-id').textContent = orderId;
        document.getElementById('add-item-order-id-hidden').value = orderId;
        document.getElementById('add-item-variant-id').value = '';
        document.getElementById('add-item-quantity').value = 1;
        popupCommon.open('popup-add-order-item');
    });
});

document.getElementById('closeAddOrderItem').onclick = () => popupCommon.close('popup-add-order-item');

document.getElementById('addOrderItemForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = {};
    formData.forEach((value, key) => { data[key] = value; });

    if (!data.variantId || !data.quantity || parseInt(data.quantity) <= 0) {
        alert('Vui lòng nhập mã biến thể và số lượng hợp lệ');
        return;
    }

    if (!confirm('Sau khi thêm sản phẩm, đơn hàng sẽ chuyển trạng thái chờ người dùng ký lại. Tiếp tục?')) return;

    try {
        const response = await fetch(window.contextPath + '/add-order-item', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                orderId: parseInt(data.orderId),
                variantId: parseInt(data.variantId),
                quantity: parseInt(data.quantity)
            })
        });
        const result = await response.json();
        alert(result.message);
        if (result.success) {
            popupCommon.close('popup-add-order-item');
            window.location.reload();
        }
    } catch (err) {
        console.error('Lỗi khi thêm sản phẩm:', err);
        alert('Lỗi: ' + err.message);
    }
});

// Popup xóa đơn hàng
document.querySelectorAll('.edit-order-remove').forEach(btn => {
    btn.addEventListener('click', () => {
        const orderId = btn.dataset.id;
        document.getElementById('confirmMessage').textContent =
            'Bạn có chắc chắn muốn xóa đơn hàng #' + orderId + '?';
        document.getElementById('confirm-order-id').value = orderId;
        popupCommon.open('popup-confirm');
    });
});

document.getElementById('confirmNo').onclick = () => popupCommon.close('popup-confirm');

document.getElementById('confirmForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const orderId = document.getElementById('confirm-order-id').value;

    try {
        const response = await fetch(window.contextPath + '/remove-order', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({orderId: orderId})
        });
        const result = await response.json();
        alert(result.message);
        if (result.success) {
            popupCommon.close('popup-confirm');
            window.location.reload();
        }
    } catch (err) {
        console.error('Lỗi khi xóa đơn hàng:', err);
        alert('Lỗi: ' + err.message);
    }
});
