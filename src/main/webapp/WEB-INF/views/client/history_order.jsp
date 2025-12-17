<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lịch Sử Đơn Hàng</title>
    <link rel="stylesheet" href="../../../shared/main.css"/>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="../../../shared/nav_account.css">
    <link rel="stylesheet" href="../../../css/history_order.css">

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<main>
    <div class="wrap-content-all">
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
                    <li class="nav-link-item link-selected">
                        <i class="fa-solid fa-clock-rotate-left"></i>
                        <a href="history_order.html">Lịch sử đơn hàng</a>
                    </li>
                    <li class="nav-link-item">
                        <i class="fa-solid fa-location-dot"></i>
                        <a href="address_user.jsp">Địa chỉ</a>
                    </li>
                    <li class="nav-link-item">
                        <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
                    </li>
                </ul>
            </div>
        </div>
        <!--    close account nav-->
        <!--        open main content-->
        <div class="main-content">
             <span class="filter">
                    <form class="form-filter">
                        <select name="filter-by" class="filter-by">
                            <option value="status-order-completed" selected>Tất cả trạng thái</option>
                            <option value="status-order-completed">Đã giao</option>
                            <option value="status-order-cancel">Đã hủy</option>
                            <option value="status-order-shipping">Đang chờ giao hàng</option>
                            <option value="status-order-processing">Đang chờ Xử lí</option>
                        </select>
                    </form>
                </span>
            <div class="wrap-content-order">
                <div class="header-order">
                    <span class="status-order"><strong>Trạng thái: <em class="completed">Đã giao</em></strong></span>
                    <span class="address-shipping"><em><i class="fa-regular fa-truck"></i> Đại Học Nông Lâm TP.HCM</em></span>

                </div>
                <div class="per-cart-item">
                    <span class="img-cart-item"><img src="assets/image/fake_products/item_bcth_1.webp"
                                                     loading="lazy"></span>
                    <span class="info-base-cart-item">
                    <span class="name-cart-item"><strong>Switch</strong></span>
                    <div class="info-cart-item">Đen, 10kg, 30x40cm</div>
                        <div class="info-quantity-cart-item">Số lượng: 2</div>
                    <div class="price-cart-item">2000đ</div>
                </span>
                </div>
                <div class="footer-order">
                    <p class="price-total-pay-order"><i class="fa-solid fa-dollar-sign"></i>Tổng số tiền:
                        2000đ
                    </p>
                </div>
            </div>
            <div class="wrap-content-order">
                <div class="header-order">
                    <span class="status-order"><strong>Trạng thái: <em class="completed">Đã giao</em></strong></span>
                    <span class="address-shipping"><em><i class="fa-regular fa-truck"></i> 233 An Giang, Cái Bè - Chợ đầu mối, Long Xuyên, Đồng Nai, Cần Thơ</em></span>
                </div>
                <div class="per-cart-item">
                    <span class="img-cart-item"><img src="assets/image/fake_products/item_bcth_1.webp"
                                                     loading="lazy"></span>
                    <span class="info-base-cart-item">
                    <span class="name-cart-item"><strong>Switch</strong></span>
                    <div class="info-cart-item">Đen, 10kg, 30x40cm</div>
                        <div class="info-quantity-cart-item">Số lượng: 2</div>
                    <div class="price-cart-item">2000đ</div>
                </span>
                </div>
                <div class="per-cart-item">
                    <span class="img-cart-item"><img src="assets/image/fake_products/item_bcth_2.webp"
                                                     loading="lazy"></span>
                    <span class="info-base-cart-item">
                    <span class="name-cart-item"><strong>FJGEAR</strong></span>
                    <div class="info-cart-item">Đen, 10kg, 70x40cm</div>
                        <div class="info-quantity-cart-item">Số lượng: 1</div>
                    <div class="price-cart-item">2000đ</div>
                </span>
                </div>
                <div class="per-cart-item">
                    <span class="img-cart-item"><img src="assets/image/fake_products/item_bcth_4.webp"
                                                     loading="lazy"></span>
                    <span class="info-base-cart-item">
                    <span class="name-cart-item"><strong>UGREEN</strong></span>
                    <div class="info-cart-item">Đen, 10gram, 40cm</div>
                        <div class="info-quantity-cart-item">Số lượng: 2</div>
                    <div class="price-cart-item">2000đ</div>
                </span>
                </div>
                <div class="footer-order">
                    <p class="price-total-pay-order"><i class="fa-solid fa-dollar-sign"></i>Tổng số tiền:
                        6000đ
                    </p>
                </div>
            </div>
        </div>
        <!--        close main content-->
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
<script src="../../../js/header.js"></script>
<script src="../../../js/order_history.js"></script>
</html>