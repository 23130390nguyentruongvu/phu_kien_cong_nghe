x<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">

</head>
<body>
    <main id="main">
        <div class="page-header">
            <div class="page-breadcrumbs">
                <div class="container">
                    <ul class="breadcrumb">
                        <li class="home">
                            <a href="#" class="home_text">Trang Chủ</a>
                        </li>
                        <li class="step">/</li>
                       Đăng Ký
                    </ul>
                </div>
            </div>
            <h1 class="page-title">Đăng Ký Tài Khoản</h1>
        </div>
        <div class="content-register">
            <div class="container">
                <div class="form-register">
                <p class="title-register">Đăng Ký</p>
                    <c:if test="${not empty error}">
                    <p class="server-error" style="color: red; text-align: center;">${error}</p>
                    </c:if>
                <form class="register" id="registrationForm" action="${pageContext.request.contextPath}/register" method="post">
                    <input type="text" name="full_name" required = "required" placeholder="Họ và tên">
                    <span class="error-msg" id="nameError"></span>

                    <input type="email" name="email" required = "required" placeholder="Email">
                    <span class="error-msg" id="emailError"></span>

                    <input type="text" name="user_name" required = "required" placeholder="Username">
                    <span class="error-msg" id="usernameErr"></span>

                    <input type="password" name="password" required = "required" placeholder="Mật khẩu" id="password1">
                    <span class="error-msg" id="pw1Error"></span>

                    <input type="password" name="confirm_password" required = "required" placeholder="Nhập lại mật khẩu" id="password2">
                    <span class="error-msg" id="pw2Error"></span>

                    <input type="submit" value="Đăng Ký" class="btn-register">
                    <br>
                    <a href="${pageContext.request.contextPath}/login" class="have-account">Đã có tài khoản?</a>
                </form>
                </div>
            </div>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/register.js"></script>
</body>
</html>