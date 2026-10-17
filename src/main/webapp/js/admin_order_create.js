document.addEventListener("DOMContentLoaded", () => {
    const customer = document.getElementById("create-order-customer");
    const address = document.getElementById("create-order-address");
    const items = document.getElementById("create-order-items");
    const addItem = document.getElementById("add-order-item");
    const form = document.getElementById("createOrderForm");
    const firstItem = items?.querySelector(".create-order-item");
    if (!customer || !address || !items || !addItem || !form || !firstItem) return;

    const filterAddresses = () => {
        const userId = customer.value;
        let visibleCount = 0;
        for (const option of address.options) {
            if (!option.value) continue;
            const matches = option.dataset.userId === userId && Boolean(userId);
            option.hidden = !matches;
            option.disabled = !matches;
            if (matches) visibleCount++;
        }
        address.value = "";
        address.disabled = !visibleCount;
        address.options[0].textContent = visibleCount
            ? "Chọn địa chỉ nhận hàng"
            : userId ? "Khách hàng chưa có địa chỉ" : "Chọn khách hàng trước";
    };
    customer.addEventListener("change", filterAddresses);
    filterAddresses();

    const updateRemoveButtons = () => {
        const rows = items.querySelectorAll(".create-order-item");
        rows.forEach((row) => {
            row.querySelector(".remove-order-item").disabled = rows.length === 1;
        });
    };

    addItem.addEventListener("click", () => {
        const row = firstItem.cloneNode(true);
        row.querySelector('select[name="variantIds"]').value = "";
        row.querySelector('input[name="quantities"]').value = "1";
        items.append(row);
        updateRemoveButtons();
    });

    items.addEventListener("click", (event) => {
        const removeButton = event.target.closest(".remove-order-item");
        if (!removeButton || items.querySelectorAll(".create-order-item").length === 1) return;
        removeButton.closest(".create-order-item").remove();
        updateRemoveButtons();
    });

    form.addEventListener("submit", (event) => {
        const selectedVariants = [...items.querySelectorAll('select[name="variantIds"]')]
            .map((select) => select.value);
        if (selectedVariants.some((id) => !id)
            || new Set(selectedVariants).size !== selectedVariants.length) {
            event.preventDefault();
            window.alert("Chọn sản phẩm cho từng dòng và không lặp lại biến thể.");
        }
    });
    updateRemoveButtons();
});
