document.addEventListener("click", async (event) => {
    const button = event.target.closest(".voucher-copy");
    if (!button) return;

    try {
        await navigator.clipboard.writeText(button.dataset.code);
        const label = button.textContent;
        button.textContent = "Đã sao chép";
        window.setTimeout(() => {
            button.textContent = label;
        }, 1500);
    } catch {
        window.prompt("Sao chép mã voucher:", button.dataset.code);
    }
});
