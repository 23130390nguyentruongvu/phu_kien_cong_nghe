<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Địa chỉ của tôi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/address_user.css" />

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main id="main" class="main">
        <!--    open account nav-->
        <div class="nav-account">
            <div class="wrap-base-info-user">
                <span class="img-user-account"><img src="../../../assets/image/logo.webp" loading="lazy"></span>
                <span class="user-name-account"><strong>MyUserName</strong></span>
            </div>
            <div class="wrap-nav-link">
                <ul>
                    <li class="nav-link-item">
                        <i class="fa-solid fa-user"></i>
                        <a href="personal_info.jsp">Tài khoản</a>
                    </li>
                    <li class="nav-link-item">
                        <i class="fa-solid fa-clock-rotate-left"></i>
                        <a href="history_order.jsp">Lịch sử đơn hàng</a>
                    </li>
                    <li class="nav-link-item link-selected">
                        <i class="fa-solid fa-location-dot"></i>
                        <a href="address_user.html">Địa chỉ</a>
                    </li>
                    <li class="nav-link-item">
                        <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
                    </li>
                </ul>
            </div>
        </div>
        <!--    close account nav-->
        <!--        open main content-->
        <div class="address-user-info">
            <div class="title">
                <h3>Địa chỉ đã lưu</h3>
                <button class="add-address"> &nbsp;+ Thêm địa chỉ mới</button>
                <script>
                    btnAddAddress = document.getElementsByClassName("add-address")[0];
                    btnAddAddress.addEventListener("click", () => {
                        window.location.href = "${pageContext.request.contextPath}/add-address";
                    })
                </script>
            </div>
            <hr>
            <div class="address-user">
                <p class="header-address">Địa chỉ</p>
                <table class="address-content">
                    <tbody>
                    <tr>
                        <td class="content">
                            <div class="name-phone">
                                <p class="name">Nguyễn Trường Vũ</p> &nbsp; | &nbsp; (+84) <p class="phone">12345678</p>
                            </div>
                            <div class="address-detail">
                                số nhà 418
                            </div>
                            <div class="district-province">
                                <p class="district">xã Nhơn Mỹ</p>
                                ,&nbsp;
                                <p class="province">tỉnh An Giang</p>
                            </div>
                            <div class="status">
                                Mặc định
                            </div>
                        </td>
                        <td class="btn">
                            <div class="update-delete">
                                <button class="update">Cập nhật</button>
                                &nbsp;
                                <button class="delete">Xóa</button>
                            </div>
                            <button class="set-status">Thiết lập mặc định</button>
                        </td>
                    </tr>

                    <tr>
                        <td class="content">
                            <div class="name-phone">
                                <p class="name">Nguyễn Trường Vũ</p> &nbsp; | &nbsp; (+84) <p class="phone">12345678</p>
                            </div>
                            <div class="address-detail">
                                số nhà 418
                            </div>
                            <div class="district-province">
                                <p class="district">xã Trảng Bom</p>
                                ,&nbsp;
                                <p class="province">tỉnh Bình Phước</p>
                            </div>
                            <div class="status">
                                Mặc định
                            </div>

                        </td>
                        <td class="btn" >
                            <div class="update-delete">
                                <button class="update">Cập nhật</button>
                                &nbsp;
                                <button class="delete">Xóa</button>
                            </div>
                                <button class="set-status">Thiết lập mặc định</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>  <script src="../../../js/header.js"></script>
</html>