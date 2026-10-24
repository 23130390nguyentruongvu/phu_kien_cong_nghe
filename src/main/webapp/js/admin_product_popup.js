const addProductButton = document.querySelector('.head-edit .edit-product-add-prod');
const addProductPopup = document.getElementById('popup-add-product');
const closeAddProductButton = document.getElementById('closeAddProd');

if (addProductButton && addProductPopup) {
    addProductButton.addEventListener('click', () => {
        addProductPopup.style.display = 'block';
    });
}

if (closeAddProductButton && addProductPopup) {
    closeAddProductButton.addEventListener('click', () => {
        addProductPopup.style.display = 'none';
    });
}

if (addProductPopup) {
    addProductPopup.addEventListener('click', (event) => {
        if (event.target === addProductPopup) {
            addProductPopup.style.display = 'none';
        }
    });
}
