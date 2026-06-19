import {showLoading, hideLoading} from './overlay_processing.js';

document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('fileUpload');
    const form = document.getElementById('registerKeyForm');

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

    //form
    if (form) {
        form.addEventListener('submit', function (event) {
            event.preventDefault();

            const isConfirmed = confirm("Xác nhận kích hoạt khóa?");
            if(!isConfirmed) return
            showLoading()

            const formData = new FormData(form);
            const data = {
                keyName: formData.get('keyName'),
                nameAlgorithm: formData.get('algorithm'),
                publicKey: formData.get('publicKey'),
                status: "ACTIVE",
                createAt: new Date().toISOString().slice(0, 19),
                revokedAt: null,
                userId: CURRENT_USER_ID
            };

            fetch(window.contextPath+ '/json-add-user-key', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        hideLoading()
                        throw new Error('Lỗi Server!');
                    }
                    return response.json();
                })
                .then(res => {
                    hideLoading()

                    if (res.success === true) {
                        alert('Kích hoạt khóa công khai thành công!');
                        window.location.reload();
                    } else {
                        alert(res.message || 'Có lỗi xảy ra khi lưu khóa.');
                    }
                })
                .catch(error => {
                    hideLoading()

                    console.error('Error:', error);
                    alert('Lỗi hệ thống!');
                });
        });
    }
});

function executeRevokeModal(userKeyId) {
    if (!userKeyId) {
        alert('Không tìm thấy thông tin khóa cần thu hồi!');
        return;
    }

    if (!CURRENT_USER_ID) {
        alert('Vui lòng đăng nhập để thực hiện chức năng này!');
        return;
    }

    const isConfirmed = confirm("Xác nhận thu hồi khóa này? Chúng tôi sẽ kiểm tra và gửi đường dẫn cho bạn qua email!");
    if (!isConfirmed) return;


    showLoading();

    const data = {
        id: ""+userKeyId,
        userId: ""+ CURRENT_USER_ID
    };

    fetch(window.contextPath + '/json-revoke-user-key', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                hideLoading();
                throw new Error('Lỗi Server!');
            }
            return response.json();
        })
        .then(res => {
            hideLoading();
            if (res.success === true || res.status === 'success') {
                alert(res.message);
                window.location.reload();
            } else {
                alert(res.message || 'Có lỗi xảy ra khi thu hồi khóa.');
            }
        })
        .catch(error => {
            hideLoading();
            console.error('Error:', error);
            alert('Lỗi hệ thống! Không thể kết nối tới máy chủ.');
        });
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

window.executeRevokeModal = executeRevokeModal;
