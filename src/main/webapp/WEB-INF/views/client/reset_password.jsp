<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thiết lập mật khẩu mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset_password.css">

</head>
<body>
<div class="container">
    <div class="card">
        <h2>Thiết lập mật khẩu mới</h2>
        <p>Vui lòng nhập mật khẩu mới để tiếp tục truy cập hệ thống.</p>

        <form action="update-password-servlet" method="POST" id="resetForm">
            <input type="hidden" name="token" value="LẤY_TOKEN_TỪ_URL_Ở_ĐÂY">

            <div class="input-group">
                <label for="password">Mật khẩu mới</label>
                <input type="password" id="password" name="newPassword" required
                       placeholder="Nhập ít nhất 6 ký tự">
            </div>

            <div class="input-group">
                <label for="confirmPassword">Xác nhận mật khẩu</label>
                <input type="password" id="confirmPassword" required
                       placeholder="Nhập lại mật khẩu mới">
            </div>

            <div id="error-message" class="error"></div>

            <button type="submit" class="btn-submit">Cập nhật mật khẩu</button>
        </form>
    </div>
</div>

<script>
    // Kiểm tra mật khẩu khớp nhau trước khi submit
    document.getElementById('resetForm').onsubmit = function(e) {
        const pass = document.getElementById('password').value;
        const confirm = document.getElementById('confirmPassword').value;
        const error = document.getElementById('error-message');

        if (pass !== confirm) {
            error.innerText = "Mật khẩu xác nhận không khớp!";
            e.preventDefault(); // Chặn gửi form
        } else if (pass.length < 6) {
            error.innerText = "Mật khẩu phải từ 6 ký tự trở lên!";
            e.preventDefault();
        }
    };
</script>
</body>
</html>