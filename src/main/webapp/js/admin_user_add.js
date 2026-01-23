import {hideLoading, showLoading} from "./overlay_processing.js";
import {uploadImageToFirebase} from "./admin_user.js";

const formUserValidate = (form) => {
    const errors = [];

    // Khai báo Regex
    const usernameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]+$/;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+={}\[\]:;"'<>,.?/-]).{8,16}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Lấy giá trị từ form qua thuộc tính name
    const name = form.name.value.trim();
    const username = form.userName.value.trim();
    const email = form.email.value.trim();
    const password = form.password.value;
    const repassword = form["re-password"].value;

    // 1. Kiểm tra không được để trống
    if (!name) errors.push("Tên người dùng không được để trống!");
    if (!username) errors.push("Username không được để trống!");
    if (!email) errors.push("Email không được để trống!");
    if (!password) errors.push("Mật khẩu không được để trống!");
    if (!repassword) errors.push("Vui lòng nhập lại mật khẩu!");

    // 2. Kiểm tra Username
    if (username) {
        if (/\s/.test(username)) {
            errors.push("Username không được chứa khoảng trắng!");
        }
        if (username.length > 30) {
            errors.push("Username không được vượt quá 30 ký tự!");
        }
        if (!usernameRegex.test(username)) {
            errors.push("Username chỉ gồm chữ và số, và phải chứa ít nhất một chữ cái!");
        }
    }

    // 3. Kiểm tra định dạng Email
    if (email && !emailRegex.test(email)) {
        errors.push("Email không đúng định dạng (ví dụ: abc@gmail.com)!");
    }

    // 4. Kiểm tra độ mạnh mật khẩu (Password Policy)
    if (password && !passwordRegex.test(password)) {
        errors.push("Mật khẩu phải từ 8-16 ký tự, bao gồm ít nhất: 1 chữ hoa, 1 số và 1 ký tự đặc biệt (!@#$%^...)!");
    }

    // 5. Kiểm tra khớp mật khẩu
    if (password && password !== repassword) {
        errors.push("Mật khẩu xác nhận không khớp!");
    }

    if (errors.length > 0) {
        // Nối các lỗi bằng dấu xuống dòng và gạch đầu dòng cho dễ nhìn
        const fullMsg = "Phát hiện các lỗi sau:\n- " + errors.join('\n- ');
        alert(fullMsg);
        return false;
    }

    return true;
};

export const setupEvent = (form) => {
    document.getElementById('submitAddUser').addEventListener('click', async (e) => {
        e.preventDefault()
        try {
            //check validate form
            if (!formUserValidate(form)) return;
            showLoading()
            //check username hoặc email đã tồn tại hay dưới db chưa
            const resCheck = await fetch(
                contextPath + `/check-username-email?username=${form.userName.value.trim()}&email=${form.email.value.trim()}`
            )
            const resCheckJson = await resCheck.json()

            if (resCheckJson.email && resCheckJson.username) throw new Error('Email và username đã tồn tại')
            if (resCheckJson.email) throw new Error('email đã tồn tại')
            if (resCheckJson.username) throw new Error('username đã tồn tại')

            let folderId
            let urlDownload

            //Check input file có ảnh không
            const file = document.getElementById('addUserImage').files
            const hasImage = file.length > 0
            if (hasImage) {
                //có ảnh và ta sẽ đi tạo folder id cho user này
                folderId = self.crypto.randomUUID();
                urlDownload = await uploadImageToFirebase(file[0], `users/${folderId}`, form.userName.value.trim())
            }
            const userAdd = {
                folderId: folderId,
                hasImage: hasImage,
                url: urlDownload,
                role: form.role.value.trim(),
                password: form.password.value.trim(),
                username: form.userName.value.trim(),
                email: form.email.value.trim(),
                fullName: form.name.value.trim()
            }

            const response = await fetch(contextPath + '/add-user', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userAdd)
            });

            const responseJson = await response.json()
            alert(responseJson.message)
            document.getElementById('closeAddUser').click()
            location.reload()
        } catch (e) {
            alert(e.message)
        } finally {
            hideLoading()
        }
    });
}