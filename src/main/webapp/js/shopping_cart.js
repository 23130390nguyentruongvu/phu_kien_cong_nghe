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