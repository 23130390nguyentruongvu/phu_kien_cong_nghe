document.addEventListener("submit", (event) => {
    const form = event.target;
    if (!(form instanceof HTMLFormElement)) return;

    const confirmation = form.dataset.confirm;
    if (confirmation && !window.confirm(confirmation)) {
        event.preventDefault();
        return;
    }

    const submitButton = event.submitter;
    if (submitButton instanceof HTMLButtonElement) {
        submitButton.disabled = true;
        submitButton.textContent = "Đang lưu...";
    }
});

document.querySelectorAll('select[name="discount_type"]').forEach((select) => {
    const valueInput = select.form?.querySelector('input[name="discount_value"]');
    if (!valueInput) return;

    const updatePercentLimit = () => {
        if (select.value === "PERCENT") {
            valueInput.max = "100";
        } else {
            valueInput.removeAttribute("max");
        }
    };

    select.addEventListener("change", updatePercentLimit);
    updatePercentLimit();
});
