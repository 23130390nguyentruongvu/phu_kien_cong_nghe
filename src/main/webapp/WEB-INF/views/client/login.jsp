<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</head>
<body>
<div class="header">

</div>

<main id="main">
    <div class="page-header">
        <div class="page-breadcrumbs">
            <div class="container">
                <ul class="breadcrumb">
                    <li class="home">
                        <a href="${pageContext.request.contextPath}/" class="home_text">Trang Chủ</a>
                    </li>
                    <li class="step">/</li>
                    <li class="account">
                        Đăng Nhập
                    </li>
                </ul>
            </div>
        </div>
        <h1 class="page-title">Tài Khoản</h1>
    </div>
    <div class="content-register">
        <div class="container">
            <div class="form-login">
                <p class="title_login">Đăng Nhập</p>
                <c:if test="${not empty error}">
                    <div class="alert-error"
                         style="color: #ff4d4d; border: 1px solid #ff4d4d; padding: 10px; margin-bottom: 15px; border-radius: 5px; text-align: center; background-color: #fff2f2;">
                            ${error}
                    </div>
                </c:if>
                <form class="login" action="${pageContext.request.contextPath}/login" method="post">
                    <input type="text" name="loginInfo" required="required" placeholder="Tên đăng nhập hoặc email">
                    <br>
                    <input type="password" name="password" required="required" placeholder="Mật khẩu">
                    <br>
                    <input class="submit" name="submit" type="submit" value="Đăng Nhập">
                    <br>
                    <a class="new-account" href="${pageContext.request.contextPath}/register">Đăng
                        ký</a>
                    <br>
                    <a href="javascript:void(0)" class="forgot-pass">Quên mật khẩu?</a>
                </form>
            </div>
        </div>
    </div>
</main>
<div class="footer">

</div>

<%--<div id="forgot-password-modal" class="modal hidden">--%>

<%--    <div class="modal-backdrop"></div>--%>

<%--    <div class="modal-content">--%>

<%--        <span class="close-btn">&times;</span>--%>

<%--        <h2>Khôi phục Mật khẩu</h2>--%>
<%--        <p>Vui lòng nhập email hoặc số điện thoại của bạn:</p>--%>

<%--        <form id="recovery-form" action="#" method="post">--%>
<%--            <input type="text" id="recovery-input" name="recovery_info" placeholder="Email hoặc SĐT" required>--%>
<%--            <button type="submit">Gửi Mã Xác Minh</button>--%>
<%--        </form>--%>

<%--    </div>--%>
<%--</div>--%>

<%--<div id="verify-code-modal" class="modal hidden">--%>

<%--    <div class="modal-backdrop"></div>--%>

<%--    <div class="modal-content">--%>

<%--        <span class="close-btn" data-modal-target="verify">&times;</span>--%>

<%--        <h2>Nhập Mã Xác Minh</h2>--%>
<%--        <p>Mã xác minh đã được gửi đến Email/SĐT của bạn. Vui lòng kiểm tra và nhập mã:</p>--%>

<%--        <form id="verify-form" action="#" method="post">--%>
<%--            <input type="text" id="verification-code" name="code" placeholder="Nhập mã xác minh (6 chữ số)" required>--%>

<%--            <button type="submit">Xác Minh</button>--%>
<%--            <p class="resend-link">--%>
<%--                <a href="#" id="resend-code">Gửi lại mã</a>--%>
<%--            </p>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script type="module" src="${pageContext.request.contextPath}/js/forgot_password.js"></script>
</html>
