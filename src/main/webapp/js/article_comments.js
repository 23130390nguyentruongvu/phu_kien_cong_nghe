const commentsToggle = document.getElementById('toggle-article-comments');
const commentsPanel = document.getElementById('article-comment-list');
const commentForm = document.querySelector('.comment-form');

if (commentForm) {
    const fields = ['content', 'name', 'email'];
    const setError = (fieldName, message) => {
        const field = commentForm.elements.namedItem(fieldName);
        const error = commentForm.querySelector(`[data-error-for="${fieldName}"]`);
        error.textContent = message;
        field.classList.toggle('input-invalid', Boolean(message));
        field.setAttribute('aria-invalid', String(Boolean(message)));
    };

    const validateComment = () => {
        const content = commentForm.elements.namedItem('content').value.trim();
        const name = commentForm.elements.namedItem('name').value.trim();
        const email = commentForm.elements.namedItem('email').value.trim();
        const errors = {
            content: content ? '' : 'Vui lòng nhập nội dung bình luận.',
            name: !name ? 'Vui lòng nhập tên hiển thị.'
                : [...name].length > 50 ? 'Tên hiển thị không được vượt quá 50 ký tự.' : '',
            email: !email ? 'Vui lòng nhập email.'
                : [...email].length > 191 || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
                    ? 'Email không hợp lệ.' : ''
        };
        fields.forEach(field => setError(field, errors[field]));
        return fields.filter(field => errors[field]);
    };

    commentForm.addEventListener('submit', event => {
        const invalidFields = validateComment();
        if (invalidFields.length) {
            event.preventDefault();
            commentForm.elements.namedItem(invalidFields[0]).focus();
        }
    });

    fields.forEach(fieldName => {
        commentForm.elements.namedItem(fieldName).addEventListener('input', () => {
            const invalidFields = validateComment();
            if (!invalidFields.includes(fieldName)) {
                setError(fieldName, '');
            }
        });
    });
}

if (commentsToggle && commentsPanel) {
    let commentsLoaded = false;
    commentsToggle.addEventListener('click', async () => {
        const shouldOpen = commentsPanel.hidden;
        commentsPanel.hidden = !shouldOpen;
        commentsToggle.setAttribute('aria-expanded', String(shouldOpen));
        commentsToggle.textContent = shouldOpen
            ? 'Ẩn bình luận'
            : `Xem bình luận (${commentsToggle.dataset.count})`;
        if (!shouldOpen || commentsLoaded) return;

        commentsPanel.textContent = 'Đang tải bình luận...';
        try {
            const response = await fetch(commentsPanel.dataset.commentsUrl);
            if (!response.ok) throw new Error('Không thể tải bình luận. Vui lòng thử lại.');
            commentsPanel.innerHTML = await response.text();
            commentsLoaded = true;
        } catch (error) {
            commentsPanel.textContent = error.message;
        }
    });
}
