document.getElementById('registrationForm').addEventListener('submit', function (e){
let isValid = true;
    const name = document.getElementsByName('full_name')[0];
    const email = document.getElementsByName('email')[0];
    const username = document.getElementsByName('user_name')[0];
    const password = document.getElementById('password1');
    const  password_confirm = document.getElementById('password2');

    const passwordErr1 = document.getElementById('pw1Error');
    const passwordErr2 = document.getElementById('pw2Error');
    const nameErr = document.getElementById('nameError');

    document.querySelector('.error-msg').forEach(er => er.innerText = "");
    document.querySelectorAll('input').forEach(er => er.classList.remove('invalid'));

    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+={}\[\]:;"'<>,.?/-]).{8,16}$/;


    if(!passwordRegex.test(password.value)){
        passwordErr1.innerText = "Mật khẩu phải có từ 8-16 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt";
        password.classList.add('invalid');
        isValid = false;
    }
    if(password.value !== password_confirm.value){
        passwordErr2.innerText = "Mật khẩu không khớp với mật khẩu đã nhập!"
        password_confirm.classList.add('invalid');
        isValid = false;
    }
    if(password.value.trim() === ""){
        document.getElementById('nameError').innerText = "Vui lòng điền đầy đủ thông tin"
        isValid = false;
    }
    if(isValid === false){
        e.preventDefault();
    }
});