// Popup xem chi tiết đơn hàng
document.querySelectorAll('.edit-order-view').forEach(btn => {
    btn.addEventListener('click', async () => {
        const orderId = btn.dataset.id;
        const popup = document.getElementById('popup-order-detail');
        const container = document.getElementById('order-detail-container');

        popup.style.display = 'block';
        document.getElementById('display-order-id').textContent = orderId;
        container.innerHTML = '<p>Đang tải dữ liệu...</p>';

        try {
            // TODO: Gọi API lấy chi tiết đơn hàng
            // const response = await fetch(`${contextPath}/get-order-detail?orderId=${orderId}`);
            // const html = await response.text();
            // container.innerHTML = html;

            // Dữ liệu mẫu tạm thời
            container.innerHTML = `
                <div class="order-detail-info">
                    <div class="info-item">
                        <div class="info-label">Mã đơn hàng</div>
                        <div class="info-value">#${orderId}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Người nhận</div>
                        <div class="info-value">Đang tải...</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Số điện thoại</div>
                        <div class="info-value">Đang tải...</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Địa chỉ</div>
                        <div class="info-value">Đang tải...</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Trạng thái</div>
                        <div class="info-value">Đang tải...</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Tổng tiền</div>
                        <div class="info-value">Đang tải...</div>
                    </div>
                </div>
                <table class="order-items-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Biến thể</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td colspan="5">Chức năng đang phát triển...</td></tr>
                    </tbody>
                </table>
            `;
        } catch (error) {
            console.error("Lỗi khi tải chi tiết đơn hàng:", error);
            container.innerHTML = `<p style="color:red;">Lỗi: ${error.message}</p>`;
        }
    });
});

document.getElementById('closeOrderDetail').onclick = () => {
    document.getElementById('popup-order-detail').style.display = 'none';
};

// Popup sửa thông tin đơn hàng
document.querySelectorAll('.edit-order-update').forEach(btn => {
    btn.addEventListener('click', async () => {
        const orderId = btn.dataset.id;
        document.getElementById('edit-order-id').textContent = orderId;
        document.getElementById('popup-edit-order').style.display = 'block';

        // TODO: Load form sửa đơn hàng
    });
});

document.getElementById('closeEditOrder').addEventListener('click', () => {
    document.getElementById('popup-edit-order').style.display = 'none';
});

// Popup thêm sản phẩm vào đơn
document.querySelectorAll('.edit-order-add-item').forEach(btn => {
    btn.addEventListener('click', () => {
        const orderId = btn.dataset.id;
        document.getElementById('add-item-order-id').textContent = orderId;
        document.getElementById('popup-add-order-item').style.display = 'block';
    });
});

document.getElementById('closeAddOrderItem').onclick = () => {
    document.getElementById('popup-add-order-item').style.display = 'none';
};

document.getElementById('addOrderItemForm').addEventListener('submit', (e) => {
    e.preventDefault();
    // TODO: Xử lý thêm sản phẩm vào đơn hàng
    alert('Chức năng đang phát triển!');
});

// Popup xóa đơn hàng
document.querySelectorAll('.edit-order-remove').forEach(btn => {
    btn.addEventListener('click', () => {
        const orderId = btn.dataset.id;
        document.getElementById('confirmMessage').textContent =
            `Bạn có chắc chắn muốn xóa đơn hàng #${orderId}?`;
        document.getElementById('confirmForm').dataset.orderId = orderId;
        document.getElementById('popup-confirm').style.display = 'block';
    });
});

document.getElementById('confirmNo').onclick = () => {
    document.getElementById('popup-confirm').style.display = 'none';
};

document.getElementById('confirmForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const orderId = e.target.dataset.orderId;
    // TODO: Gọi API xóa đơn hàng
    alert(`Chức năng xóa đơn hàng #${orderId} đang phát triển!`);
    document.getElementById('popup-confirm').style.display = 'none';
});

// Đóng popup khi click ra ngoài
document.querySelectorAll('.popup').forEach(popup => {
    popup.addEventListener('click', (e) => {
        if (e.target === popup) {
            popup.style.display = 'none';
        }
    });
});
