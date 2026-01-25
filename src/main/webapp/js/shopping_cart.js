const updateCartItem = (id, change, path) => {
    // Tạo một form ẩn
    const form = document.createElement('form');
    form.method = 'post';
    form.action = path;

    // Tạo các input chứa dữ liệu
    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    idInput.value = id;

    const qtyInput = document.createElement('input');
    qtyInput.type = 'hidden';
    qtyInput.name = 'quantity';
    qtyInput.value = change;

    form.appendChild(idInput);
    form.appendChild(qtyInput);
    document.body.appendChild(form);
    form.submit();
}

const removeCartItem = (id, isDelete, path) => {
    // Tạo một form ẩn
    const formDelete = document.createElement('form');
    formDelete.method = 'post';
    formDelete.action = path;

    // Tạo các input chứa dữ liệu
    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    idInput.value = id;

    formDelete.appendChild(idInput);
    document.body.appendChild(formDelete);
    formDelete.submit();
}

function handleDelete(id, name, path) {
    // Hiển thị popup xác nhận (Confirm box)
    const result = confirm("Bạn có chắc chắn muốn xóa sản phẩm '" + name + "' khỏi giỏ hàng không?");
    if (result) {
        removeCartItem(id, result, path)
    }
}
// su kien nhan thanh toan
document.addEventListener("DOMContentLoaded", function () {
    const btnCheckOut = document.getElementById('btnGotoCheckout');
    if (btnCheckOut) {
        btnCheckOut.addEventListener('click', function () {
            // Lấy URL đã được JSP tính toán sẵn
            const targetUrl = btnCheckOut.getAttribute('data-url');
            window.location.href = targetUrl;
        });
    }
});