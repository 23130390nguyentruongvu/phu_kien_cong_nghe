import {getAuth, createUserWithEmailAndPassword, sendEmailVerification, reload} from "./firebase.js";
import {hideLoading, showLoading} from "./overlay_processing.js";

const auth = getAuth();
const contextPath = window.contextPath

document.getElementById('registrationForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    let isValid = true;
    const form = this;
    const name = document.getElementsByName('full_name')[0];
    const email = document.getElementsByName('email')[0];
    const username = document.getElementsByName('user_name')[0];
    const password = document.getElementById('password1');
    const password_confirm = document.getElementById('password2');

    const passwordErr1 = document.getElementById('pw1Error');
    const passwordErr2 = document.getElementById('pw2Error');
    const nameErr = document.getElementById('nameError');
    const emailErr = document.getElementById('emailError');
    const usernameErr = document.getElementById('usernameErr');
    // Reset trạng thái
    document.querySelectorAll('.error-msg').forEach(er => er.innerText = "");
    document.querySelectorAll('input').forEach(er => er.classList.remove('invalid'));

    // 1. Validate Form cơ bản (Client-side)
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+={}\[\]:;"'<>,.?/-]).{8,16}$/;
    if (!passwordRegex.test(password.value)) {
        passwordErr1.innerText = "Mật khẩu 8-16 ký tự, có chữ hoa, số và ký tự đặc biệt";
        password.classList.add('invalid');
        isValid = false;
    }
    if (password.value !== password_confirm.value) {
        passwordErr2.innerText = "Mật khẩu xác nhận không khớp!";
        password_confirm.classList.add('invalid');
        isValid = false;
    }
    const usernameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+$/;
    if (!usernameRegex.test(username.value)) {
        usernameErr.innerText = "Username phải có chữ cái và không có ký tự đặc biệt";
        username.classList.add('invalid');
        isValid = false;
    }

    if (!isValid) return;

    try {
        // loading
        showLoading()

        //kiểm tra sự tồn tại của username và email
        const checkRes = await fetch(`${contextPath}/check-username-email?email=${email.value}&username=${username.value}`);
        const checkData = await checkRes.json();

        if (checkData.email || checkData.username) {
            if (checkData.email) {
                emailErr.innerText = "Email này đã được đăng ký!";
                email.classList.add('invalid');
            } else {
                usernameErr.innerText = "Username này đã tồn tại!";
                username.classList.add('invalid');
            }
//            hideLoading()
            return;
        }

        //Đăng ký trên Firebase Auth
        //xác thực tính hợp lệ email và password
        const userCredential = await createUserWithEmailAndPassword(auth, email.value, password.value);
        //Thành công thì tạo user trên hệ thống nhưng chưa xác thực mail
        const user = userCredential.user;

        //Gửi email xác thực
        await sendEmailVerification(user);

        //Thay đổi giao diện: Ẩn Form, hiện Màn hình chờ để đợi người dùng xác thực mail
        hideLoading()
        form.style.display = 'none';
        const verifySection = document.createElement('div');
        verifySection.id = "verify-step";
        verifySection.innerHTML = `
            <div style="text-align: center; border: 1px solid #ddd; padding: 30px; border-radius: 8px;">
                <h3 style="color: #2c3e50;">Xác thực Email</h3>
                <p>Một liên kết đã được gửi tới <b>${email.value}</b>.</p>
                <p>Vui lòng click vào link để kích hoạt tài khoản.</p>
                <div class="loader-small">Trong quá trình xác thực vui lòng không đóng trình duyệt</div>
            </div>
        `;
        verifySection.innerHTML += `
    <p>Không nhận được email? <a href="#" id="resend-btn" style="color: blue;">Gửi lại ngay</a></p>
`;
        form.parentNode.appendChild(verifySection);
        document.getElementById('resend-btn').addEventListener('click', async (e) => {
            e.preventDefault();
            await sendEmailVerification(user);
            alert("Email xác thực đã được gửi lại!");
        });

        //Kiểm tra trạng thái xác thực mỗi 3 giây
        const checkInterval = setInterval(async () => {
            await reload(user); // Làm mới thông tin user từ Firebase
            if (user.emailVerified) {
                clearInterval(checkInterval);

                //Xác thực thành công -> Lưu chính thức vào db
                const finalData = new URLSearchParams();
                finalData.append("full_name", name.value);
                finalData.append("email", email.value);
                finalData.append("user_name", username.value);
                finalData.append("password", password.value);
                finalData.append("uid", user.uid);
                finalData.append("confirm_password", password_confirm.value)

                const saveRes = await fetch(`${contextPath}/register`, {
                    method: 'POST',
                    body: finalData
                });

                const saveResult = await saveRes.json();
                alert(saveResult.message)
                if (saveResult.success) {
                    window.location.href = `${contextPath}/login`;
                }
            }
        }, 3000);

    } catch (error) {
        console.error("Firebase Error:", error);
        hideLoading()
        if (error.code === 'auth/email-already-in-use') {
            emailErr.innerText = "Email đã được sử dụng bởi một tài khoản khác.";
        } else {
            alert("Đã xảy ra lỗi: " + error.message);
        }
    }
});