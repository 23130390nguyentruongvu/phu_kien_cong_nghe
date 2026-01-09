document.querySelector('.head-edit .edit-user-add-user').onclick = () => {
    const popup = document.getElementById('popup-add-user');
    popup.querySelector('form').reset();
    popup.style.display = 'block';
};

document.getElementById('closeAddProd').onclick = () => {
    document.getElementById('popup-add-user').style.display = 'none';
};

function openUpdatePopup(id) {
    const popup = document.getElementById('popup-update-user');
    const form = document.getElementById('editUserForm');

    fetch(`manage-user?action=getUserJson&id=${id}`)
        .then(response => response.json())
        .then(data => {

            document.getElementById('edit-id').value = data.id;
            document.getElementById('name-user').value = data.fullName;
            document.getElementById('user-name').value = data.userName;
            document.getElementById('email-edit').value = data.email;
            document.getElementById('avatar-edit').value = data.avatar;
            document.getElementById('role-edit').value = data.role;


            const statusText = data.status === 'active' ? 'Đang hoạt động' : 'Đang bị khóa';
            document.querySelector('#set-active span').innerText = statusText;

            popup.style.display = 'block';
        })
        .catch(err => {
            console.error("Lỗi:", err);
            alert("Không thể tải dữ liệu người dùng!");
        });
}

document.getElementById('closeUpdate').onclick = () => {
    document.getElementById('popup-update-user').style.display = 'none';
};

function openViewPopup(id) {
    const popup = document.getElementById('popup-view-user');

    fetch(`manage-user?action=getUserJson&id=${id}`)
        .then(response => response.json())
        .then(data => {
            const tbody = popup.querySelector('tbody');
            tbody.innerHTML = `
                <tr>
                    <td>${data.id}</td>
                    <td><img src="${data.avatar || 'assets/image/logo.webp'}" width="50"></td>
                    <td>${data.fullName}</td>
                    <td>${data.userName}</td>
                    <td>${data.email}</td>
                    <td>******</td>
                    <td>${data.status}</td>
                    <td>Admin View Only</td>
                </tr>
            `;
            popup.style.display = 'block';
        });
}

document.getElementById('closeUserView').onclick = () => {
    document.getElementById('popup-view-user').style.display = 'none';
};

window.onclick = (event) => {
    if (event.target.classList.contains('popup')) {
        event.target.style.display = 'none';
    }
};