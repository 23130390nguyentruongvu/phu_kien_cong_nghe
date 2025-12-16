<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Địa chỉ của tôi</title>
    <link rel="stylesheet" href="shared/main.css">
    <link rel="stylesheet" href="shared/nav_account.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="css/address_user.css">

</head>
<body>
<div class="header">
    <div class="header-container">
        <!--     open header-above-->
        <div class="header-above">
            <div class="grid-column-above">
                <div class="information">
                    <ul>
                        <li><strong>Phụ Kiện Công Nghệ</strong></li>
                        <li>Kết nối với chúng tôi:
                            <a href="" class="text-hover"><i class="fa-brands fa-facebook"></i></a>
                            <a href="" class="text-hover"><i class="fa-brands fa-instagram"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="empty"></div>
                <div id="stateUser">
                    <div class="wrap-state-user">
                        <a class="state-user-hover"><i class="fa-solid fa-circle-user"></i></i>
                            <p id="stateUserLogin">KhaBa</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!--        close header-above-->
        <!--        open header-center-->
        <div class="header-center">
            <div class="center logo">
                <a href="../index.html"><img src="../assets/image/logo.webp" style="width: 60px;height: 60px;"/></a>
            </div>
            <div class="center form-search">
                <form action="" method="get">
                    <input type="search" name="search-product" placeholder="Tìm kiếm sản phẩm..."
                           class="search-input">
                    <button class="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </form>
            </div>
            <div class="center shopping-cart">
                <a  ><i class="fa-solid fa-cart-shopping"><span id="badgeNumItemCart"
                                                                      class="badge">0</span></i></a>
            </div>
        </div>
        <!--        close header-center-->
        <!--        open header-below-->
        <div class="header-below">
            <div class="empty-left"></div>
            <div class="main-menu below-center">
                <div class="grid-column-below">
                    <ul>
                        <li id="menuHome" class="menu-item"><a href="home.jsp">TRANG CHỦ</a></li>
                        <li id="menuIntroduce" class="menu-item"><a href="introduction.jsp">GIỚI THIỆU</a></li>
                        <li id="menuProducts" class="menu-item"><a href="product_category.jsp">DANH MỤC SẢN PHẨM<i
                                class="fa-solid fa-chevron-down"></i></a>
                            <div class="show-when-hover products below-center">
                                <div class="grid-column sub-menu-products">
                                    <ul>
                                        <li id="menuItem1" class="sub-menu-item"
                                        ><a href="">Phụ Kiện
                                            Máy Tính</a></li>
                                        <li id="menuItem2" class="sub-menu-item"
                                        ><a href="">Phụ Kiện
                                            Điện Thoại</a></li>
                                        <li id="menuItem3" class="sub-menu-item"
                                        ><a href="">Dây Cáp
                                            Tín Hiệu</a></li>
                                        <li id="menuItem4" class="sub-menu-item"
                                        ><a href="">Bộ Chia
                                            Tín Hiệu</a></li>
                                        <li id="menuItem5" class="sub-menu-item"><a href="">phụ Kiện Xe</a>
                                        </li>
                                        <li id="menuItem6" class="sub-menu-item"><a href="">Thiết Bị Mạng</a>
                                        </li>
                                        <li id="menuItem7" class="sub-menu-item"><a href="">Thiết Bị Ngoại Vi</a>
                                        </li>
                                        <li id="menuItem8" class="sub-menu-item"><a href="">Bộ Chuyển Đổi Tín
                                            Hiệu</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li id="menuNews" class="menu-item"><a href="news.jsp">TIN TỨC</a></li>
                        <li id="menuContact" class="menu-item"><a href="contact.jsp">LIÊN HỆ VỚI CHÚNG TÔI</a></li>
                    </ul>
                </div>
            </div>
            <div class="empty-right"></div>
        </div>
        <!--        close header-below-->
    </div>
</div>

<main id="main" class="main">
        <!--    open account nav-->
        <div class="nav-account">
            <div class="wrap-base-info-user">
                <span class="img-user-account"><img src="assets/image/logo.webp" loading="lazy"></span>
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
                        window.location.href = 'add_address.html';
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
<div id="footer">
    <div class="footer">
        <div class="footer-container">
            <div class="footer-grid center">
                <div class="footer-column first info">
                    <ul>
                        <li><i class="fa-solid fa-phone"></i> Gọi: 0793189000</li>
                        <li><i class="fa-solid fa-phone-volume"></i> Hotline: 0793189032</li>
                        <li><i class="fa-solid fa-location-dot"></i> Địa chỉ: Kios 8 giang, phường Linh Trung, Thủ
                            Đức
                        </li>
                        <li><i class="fa-solid fa-envelope"></i> Email: pkcn@sp.love</li>
                    </ul>
                </div>
                <div class="footer-column second nav">
                    <ul>
                        <li><a href="payment_method.jsp" class="text-hover"><i class="fa-solid fa-money-bill"></i> Phương thức thanh
                            toán</a>
                        </li>
                        <li><a href="shipping_method.jsp" class="text-hover"><i class="fa-solid fa-truck"></i> Phương thức giao hàng</a>
                        </li>
                        <li><a href="warranty_policy.jsp" class="text-hover"><i class="fa-solid fa-award"></i> Chính sách bảo hành</a>
                        </li>
                        <li><a href="privacy_policy.jsp" class="text-hover"><i class="fa-solid fa-lock"></i> Chính sách bảo mật</a>
                        </li>
                        <li><a href="term_of_service.jsp" class="text-hover"><i class="fa-solid fa-pen-nib"></i> Điều khoản dịch vụ</a>
                        </li>
                        <li><a href="voucher.jsp" class="text-hover"><i class="fa-solid fa-ticket"></i> Voucher</a>
                        </li>
                    </ul>
                </div>
                <div class="footer-column third social-media">
                    <ul>
                        <li><a href="" class="text-hover"><i class="fa-brands fa-facebook"></i> Facebook</a></li>
                        <li><a href="" class="text-hover"><i class="fa-brands fa-instagram"></i> Instagram</a></li>
                    </ul>
                </div>
                <div class="footer-column 4th logo">
                    <img src="../assets/image/logo.webp" alt="">
                </div>
            </div>
            <div class="site-below">
                <p>Copyright © 2025 Phụ Kiện Công Nghệ</p>
            </div>
        </div>
    </div>
</div>

</body>  <script src="js/header.js"></script>
</html>