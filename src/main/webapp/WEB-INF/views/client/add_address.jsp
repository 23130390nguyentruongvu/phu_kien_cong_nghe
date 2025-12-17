<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm địa chỉ mới</title>
    <link rel="stylesheet" href="../../../shared/nav_account.css">
    <link rel="stylesheet" href="../../../shared/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="../../../css/add_edit_address.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main id="main" class="main">
        <div class="nav-account">
            <div class="wrap-base-info-user">
                <span class="img-user-account"><img src="../../../assets/image/logo.webp" loading="lazy"></span>
                <span class="user-name-account"><strong>MyUserName</strong></span>
            </div>
            <div class="wrap-nav-link">
                <ul>
                    <li class="nav-link-item "><i class="fa-solid fa-user"></i>Tài khoản</li>
                    <li class="nav-link-item"><i class="fa-solid fa-clock-rotate-left"></i>Lịch sử đơn hàng</li>
                    <li class="nav-link-item link-selected"><i class="fa-solid fa-location-dot"></i>Địa chỉ</li>
                    <li class="nav-link-item"><i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</li>
                </ul>
            </div>
        </div>
        <!--    close account nav-->
        <!--        open main content-->
        <div class="main-container">
            <div class="add-address-content">
                <div class="container">
                    <div class="title"><h3>Địa chỉ mới</h3></div>
                    <form class="form-address" id="form-address" method="post">
                        <div class="form-main">
                            <div class="name">
                                <input type="text" class="input-name" placeholder="&nbsp;Họ và tên">
                            </div>
                            <div class="phone">
                                <input type="tel" class="input-phone" placeholder="&nbsp;Số điện thoại">
                            </div>
                            <div class="province-district" >
                                <div class="select-province">
                                <select class="input-province" name="province">
                                    <option></option>
                                    <option>An Giang</option>
                                    <option>TP.Hồ Chí Minh</option>
                                    <option>Hà Nội</option>
                                </select>
                                </div>
                                <div class="select-district">
                                <select class="input-district" name="district">
                                    <option></option>
                                </select>
                                </div>
                            </div>
                            <div class="address-detail">
                                <input class="input-address-details" type="text" placeholder="&nbsp;Địa chỉ chi tiết">
                            </div>
<!--                            <div class="checkbox-status-d">-->
<!--                                <input class="checkbox-status" type="checkbox" id="check">-->
<!--                                <label for="check">Đặt làm mặc định</label>-->
<!--                            </div>-->
                            <div class="btn-done-cancel">
                                <button class="btn-cancel">Trở lại</button>
                                <button class="btn-done">Hoàn thành</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
<script src="../../../js/header.js"></script>
</html>