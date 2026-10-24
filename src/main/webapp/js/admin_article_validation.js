document.addEventListener("submit", event => {
    const form = event.target;
    if (!(form instanceof HTMLFormElement) || !form.matches(".article-editor-form")) return;

    const fields = ["title", "subdescription", "content"];
    const values = Object.fromEntries(fields.map(name =>
        [name, form.elements.namedItem(name).value.trim()]));
    let firstInvalid = null;

    fields.forEach(name => {
        const field = form.elements.namedItem(name);
        const error = form.querySelector(`[data-error-for="${name}"]`);
        let message = "";
        if (!values[name]) {
            message = "Vui lòng nhập đầy đủ tiêu đề, mô tả ngắn và nội dung bài viết.";
        } else if (name === "title" && [...values.title].length > 191) {
            message = "Tiêu đề không được vượt quá 191 ký tự.";
        }

        error.textContent = message;
        field.classList.toggle("input-invalid", Boolean(message));
        field.setAttribute("aria-invalid", String(Boolean(message)));
        if (message && !firstInvalid) firstInvalid = field;
    });

    if (firstInvalid) {
        event.preventDefault();
        firstInvalid.focus();
    }
});

document.addEventListener("input", event => {
    const field = event.target;
    if (!(field instanceof HTMLInputElement || field instanceof HTMLTextAreaElement)) return;
    const form = field.closest(".article-editor-form");
    if (!form) return;

    const error = form.querySelector(`[data-error-for="${field.name}"]`);
    if (!error) return;
    const value = field.value.trim();
    if (value && (field.name !== "title" || [...value].length <= 191)) {
        error.textContent = "";
        field.classList.remove("input-invalid");
        field.setAttribute("aria-invalid", "false");
    }
});
