document.querySelector('.head-edit .edit-user-add-user').onclick = () => {
    const popup = document.getElementById('popup-add-user');
    popup.querySelector('form').reset();
    popup.style.display = 'block';
};

document.getElementById('closeAddProd').onclick = () => {
    document.getElementById('popup-add-user').style.display = 'none';
};

function openUpdatePopup(id, fullName, userName, email, avatar, role) {
    const popup = document.getElementById('popup-update-user');

    document.getElementById('edit-id').value = id;
    document.getElementById('name-user').value = fullName;

    const roleSelect = document.getElementById("role-edit");
    if(roleSelect){
        roleSelect.value = role;
    }
    const userNameInput = document.getElementById('user-name');
    userNameInput.value = userName;
    userNameInput.readOnly = true;
    userNameInput.style.backgroundColor = "#eeeeee"; // Tạo hiệu ứng bị khóa

    const emailInput = document.getElementById('email-edit');
    emailInput.value = email;
    emailInput.readOnly = true;
    emailInput.style.backgroundColor = "#eeeeee";

    document.getElementById('avatar-edit').value = avatar;
    document.getElementById('role-edit').value = role;

    // Hiện popup
    popup.style.display = 'block';

    document.getElementById('closeUpdate').onclick = () => {
        document.getElementById('popup-update-user').style.display = 'none';
    };
}