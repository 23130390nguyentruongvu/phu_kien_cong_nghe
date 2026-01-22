// document.querySelector('.head-edit .edit-user-add-user').onclick = () => {
//     const popup = document.getElementById('popup-add-user');
//     popup.querySelector('form').reset();
//     popup.style.display = 'block';
// };
//
// document.getElementById('closeAddProd').onclick = () => {
//     document.getElementById('popup-add-user').style.display = 'none';
// };
//
// function openUpdatePopup(id, fullName, userName, email, avatar, role) {
//     const popup = document.getElementById('popup-update-user');
//
//     document.getElementById('edit-id').value = id;
//     document.getElementById('name-user').value = fullName;
//
//     const roleSelect = document.getElementById("role-edit");
//     if(roleSelect){
//         roleSelect.value = role;
//     }
//     const userNameInput = document.getElementById('user-name');
//     userNameInput.value = userName;
//     userNameInput.readOnly = true;
//     userNameInput.style.backgroundColor = "#eeeeee"; // Tạo hiệu ứng bị khóa
//
//     const emailInput = document.getElementById('email-edit');
//     emailInput.value = email;
//     emailInput.readOnly = true;
//     emailInput.style.backgroundColor = "#eeeeee";
//
//     document.getElementById('avatar-edit').value = avatar;
//     document.getElementById("file-edit").value = "";
//     document.getElementById('role-edit').value = role;
//
//     // Hiện popup
//     popup.style.display = 'block';
//
//     document.getElementById('closeUpdate').onclick = () => {
//         document.getElementById('popup-update-user').style.display = 'none';
//     };
// }
const popup = document.getElementById('popup-user');
const form = document.getElementById('user-form');
const title = document.getElementById('popup-title');
const passGroup = document.getElementById('password-group');


document.querySelector('.edit-user-add-user').onclick = () => {
    form.reset();
    form.action = "add-user";
    title.innerText = "Thêm User mới";
    document.getElementById('user-id').value = "";
    document.getElementById('user-username').readOnly = false;
    document.getElementById('user-username').style.backgroundColor = "#fff";
    passGroup.style.display = "block"; // Hiện mật khẩu khi thêm
    popup.style.display = 'block';
};

function openUpdatePopup(id, fullName, userName, email, role) {
    form.reset();
    form.action = "edit-user";
    title.innerText = "Chỉnh sửa người dùng";

    document.getElementById('user-id').value = id;
    document.getElementById('user-fullname').value = fullName;
    document.getElementById('user-username').value = userName;
    document.getElementById('user-email').value = email;
    document.getElementById('user-role').value = role;

    document.getElementById('user-username').readOnly = true;
    document.getElementById('user-username').style.backgroundColor = "#eeeeee";
    document.getElementById("user-email").readOnly = true;
    document.getElementById('user-email').style.backgroundColor = "#eeeeee";

    passGroup.style.display = "none";

    popup.style.display = 'block';
}

function closePopup() {
    popup.style.display = 'none';
}