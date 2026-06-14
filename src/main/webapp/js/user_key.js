document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('fileUpload');
    if (fileInput) {
        fileInput.addEventListener('change', function (e) {
            const file = e.target.files[0];
            if (!file) return;
            const reader = new FileReader();
            reader.onload = function (event) {
                const textarea = document.getElementById('publicKey');
                if (textarea) textarea.value = event.target.result;
            };
            reader.readAsText(file);
        });
    }
});

function openRevokeModal() {
    const modal = document.getElementById('revokeModal');
    if (modal) modal.classList.remove('hidden');
}

function closeRevokeModal() {
    const modal = document.getElementById('revokeModal');
    if (modal) modal.classList.add('hidden');
}

function executeRevoke() {
    alert('Đã thu hồi khóa thành công! Khóa hiện tại đã bị vô hiệu hóa.');
    closeRevokeModal();
}

function registerKey(event) {
    event.preventDefault();
    alert('Đã nhận khóa công khai mới thành công!');
    return false;
}

document.addEventListener('click', function (e) {
    const modal = document.getElementById('revokeModal');
    if (modal && !modal.classList.contains('hidden')) {
        const container = modal.querySelector('.modal-container');
        if (container && !container.contains(e.target)) closeRevokeModal();
    }
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') closeRevokeModal();
});
