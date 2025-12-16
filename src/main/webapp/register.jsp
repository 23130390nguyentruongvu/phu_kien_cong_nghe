<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" href="css/register.css">

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
                <form class="register" id="registrationForm">
                    <input type="text" required = "required" placeholder="Họ và tên">
                    <br>
                    <input type="email" required = "required" placeholder="Email">
                    <br>
                    <input type="text" required = "required" placeholder="Username">
                    <br>
                    <input type="password" required = "required" placeholder="Mật khẩu" id="password1">
                    <br>
                    <input type="password" required = "required" placeholder="Nhập lại mật khẩu" id="password2">
                    <br>
                    <input type="submit" value="Đăng Ký" class="btn-register">
                    <br>
                    <a href="/phu_kien_cong_nghe/pages/trong_tin/page_login/login.html" class="have-account">Đã có tài khoản?</a>
                </form>
                </div>
            </div>
        </div>
    </main>
    <script src="js/register.js"></script>
</body>
</html>