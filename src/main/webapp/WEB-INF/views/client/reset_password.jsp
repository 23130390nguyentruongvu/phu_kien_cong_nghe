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
<c:if test="${empty requestScope.token}">
    <div class="container">
        <div class="card">
            <h2>${requestScope.message}</h2>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.token}">
    <div class="container">
        <div class="card">
            <h2>Thiết lập mật khẩu mới</h2>
            <p>Vui lòng nhập mật khẩu mới để tiếp tục truy cập hệ thống.</p>

            <form action="update-password-servlet" method="POST" id="resetForm">
                <input type="hidden" name="token" value="${requestScope.token}">

                <div class="input-group">
                    <label for="password">Mật khẩu mới</label>
                    <input type="password" id="password" name="newPassword" required
                           placeholder="Mật khẩu 8-16 ký tự, có chữ hoa, số và ký tự đặc biệt">
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

</c:if>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/reset_password.js" type="module">
</script>
</body>
</html>