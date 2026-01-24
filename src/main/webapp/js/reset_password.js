import {hideLoading, showLoading} from "./overlay_processing.js";

const contextPath = window.contextPath

document.getElementById('resetForm').addEventListener('submit', async function (e) {
    e.preventDefault()
    try {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const errorMessage = document.getElementById('error-message');
        const token = document.getElementById('resetForm').token.value

        // Xóa thông báo lỗi cũ
        errorMessage.innerText = "";

        //Regex kiểm tra độ mạnh mật khẩu
        const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+={}\[\]:;"'<>,.?/-]).{8,16}$/;

        if (!passwordRegex.test(password)) {
            errorMessage.innerText = "Mật khẩu phải từ 8-16 ký tự, bao gồm ít nhất 1 chữ hoa, 1 số và 1 ký tự đặc biệt.";
            return;
        }

        // Kiểm tra mật khẩu xác nhận có khớp không
        if (password !== confirmPassword) {
            errorMessage.innerText = "Xác nhận mật khẩu không khớp. Vui lòng kiểm tra lại.";
            return;
        }
        showLoading()

        const resetPass = await fetch(`${contextPath}/reset-password`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({token: token, newPassword: password})
        });

        const result = await resetPass.json()
        alert(result.message)
    } catch (e) {
        alert(e)
    } finally {
        hideLoading()
    }
});