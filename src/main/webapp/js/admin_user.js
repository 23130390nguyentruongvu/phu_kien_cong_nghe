// Mở popup thêm user
document.querySelector('.head-edit .edit-user-add-user').onclick = () => {
    document.getElementById('popup-add-user').style.display = 'block';
};

document.getElementById('closeAddProd').onclick = () => {
    document.getElementById('popup-add-user').style.display = 'none';
};
// popup xem user
document.querySelectorAll('.edit-user-show-var').forEach(btn => {
    btn.addEventListener('click', () => {
        document.getElementById('popup-view-user').style.display = 'block';
    });
});
document.getElementById('closeUserView').onclick = () => {
    document.getElementById('popup-view-user').style.display = 'none';
};
document.querySelectorAll('.edit-user-update').forEach(btn => {
    btn.addEventListener('click', () => {
        document.getElementById('popup-update-user').style.display = 'block';
    });
});
document.getElementById('closeUpdate').onclick = () => {
    document.getElementById('popup-update-user').style.display = 'none';
}
