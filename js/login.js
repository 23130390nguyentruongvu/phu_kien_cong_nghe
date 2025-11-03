document.addEventListener('DOMContentLoaded', () => {


    const forgotLink = document.getElementById('forgot-password-link');
    const modalForgot = document.getElementById('forgot-password-modal');
    const modalVerify = document.getElementById('verify-code-modal');


    const forgotForm = modalForgot ? modalForgot.querySelector('#recovery-form') : null;
    const verifyForm = modalVerify ? modalVerify.querySelector('#verify-form') : null;


    if (!forgotLink || !modalForgot || !modalVerify || !forgotForm || !verifyForm) {
        console.error("error");
        return;
    }

    const openModal = function (modalElement) {
        modalElement.classList.remove('hidden');
    }

    const closeModal = function (modalElement) {
        modalElement.classList.add('hidden');
    }



    const CloseClick = function (e) {
        const targetModal = e.target.closest('.modal');
        if (targetModal) {
            closeModal(targetModal);
        }
    };


    forgotLink.addEventListener('click', function (e) {
        e.preventDefault();
        openModal(modalForgot);
    });


    forgotForm.addEventListener('submit', function (e) {
        e.preventDefault();

        closeModal(modalForgot);
        openModal(modalVerify);
    });


    verifyForm.addEventListener('submit', function (e) {
        e.preventDefault();
        alert('');
        closeModal(modalVerify);
    });


    modalForgot.querySelector('.close-btn').addEventListener('click', CloseClick);
    modalForgot.querySelector('.modal-backdrop').addEventListener('click', CloseClick);


    modalVerify.querySelector('.close-btn').addEventListener('click', CloseClick);
    modalVerify.querySelector('.modal-backdrop').addEventListener('click', CloseClick);
});
