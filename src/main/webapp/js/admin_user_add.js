import {hideLoading, showLoading} from "./overlay_processing.js";

const fetchAdminUserRequest = async (url, options = {}) => {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 30000);

    try {
        return await fetch(url, {...options, signal: controller.signal});
    } catch (error) {
        if (controller.signal.aborted) {
            throw new Error("Máy chủ không phản hồi sau 30 giây. Vui lòng kiểm tra kết nối cơ sở dữ liệu hoặc log máy chủ.");
        }
        throw error;
    } finally {
        clearTimeout(timeoutId);
    }
};

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
            const checkParams = new URLSearchParams({
                username: form.userName.value.trim(),
                email: form.email.value.trim()
            });
            const resCheck = await fetchAdminUserRequest(
                contextPath + `/check-username-email?${checkParams}`
            )
            if (!resCheck.ok) throw new Error(`Không thể kiểm tra tài khoản (HTTP ${resCheck.status}).`)
            const resCheckJson = await resCheck.json()

            if (resCheckJson.email && resCheckJson.username) throw new Error('Email và username đã tồn tại')
            if (resCheckJson.email) throw new Error('email đã tồn tại')
            if (resCheckJson.username) throw new Error('username đã tồn tại')

            const userAdd = new FormData(form);

            const response = await fetchAdminUserRequest(contextPath + '/add-user', {
                method: 'POST',
                body: userAdd
            });

            if (!response.ok) throw new Error(`Không thể thêm người dùng (HTTP ${response.status}).`)
            const responseJson = await response.json()
            if (!responseJson.success) throw new Error(responseJson.message)
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