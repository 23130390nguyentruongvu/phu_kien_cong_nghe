<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh Mục Sản Phẩm</title>
    <link rel="stylesheet" href="shared/product_sidebar.css">
    <link rel="stylesheet" href="shared/main.css">
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="css/product_category.css">
    <link rel="stylesheet" href="shared/item_product.css">

</head>
<body>
<div id="header">
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
                    <a ><i class="fa-solid fa-cart-shopping"><span id="badgeNumItemCart"
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
                            <li id="menuProducts" class="menu-item active"><a href="product_category.html">DANH MỤC SẢN PHẨM<i
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
</div>
<main>
    <div class="container">
        <div class="sidebar-product">
            <aside class="widget widget_search">
                <h3>DANH MỤC SẢN PHẨM</h3>
                <div class="search-box">
                    <input type="search" placeholder="Tìm sản phẩm…"/>
                    <button><i class="fa-solid fa-magnifying-glass"></i></button>
                </div>
            </aside>

            <aside class="widget widget_product_categories">
                <ul class="product-categories">
                    <li class="category">
                        <a href="#" class="category-title">Bộ Chia Tín Hiệu</a>
                        <ul class="sub-category">
                            <li><a href="#">Bộ Chia AV</a></li>
                            <li><a href="#">Bộ Chia HDMI</a></li>
                            <li><a href="#">Bộ Chia VGA</a></li>
                            <li><a href="#">Bộ Chia USB</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Bộ Chuyển Đổi Tín Hiệu</a>
                        <ul class="sub-category">
                            <li><a href="#">Chuyển Đổi Audio Quang</a></li>
                            <li><a href="#">Chuyển Đổi HDMI</a></li>
                            <li><a href="#">Chuyển Đổi VGA</a></li>
                            <li><a href="#">Chuyển Đổi USB</a></li>
                            <li><a href="#">Cáp DisplayPort</a></li>
                            <li><a href="#">Đầu Nối HDMI, VGA</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Dây Cáp Tín Hiệu</a>
                        <ul class="sub-category">
                            <li><a href="#">Cáp Âm Thanh</a></li>
                            <li><a href="#">Cáp HDMI</a></li>
                            <li><a href="#">Cáp VGA</a></li>
                            <li><a href="#">Cáp USB</a></li>
                            <li><a href="#">Cáp Lập Trình</a></li>
                            <li><a href="#">Cáp DVI</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Phụ Kiện Điện Thoại</a>
                        <ul class="sub-category">
                            <li><a href="#">Cáp HDMI Cho Điện Thoại</a></li>
                            <li><a href="#">Cáp OTG</a></li>
                            <li><a href="#">Kính 3D VR Shinecon</a></li>
                            <li><a href="#">Thiết Bị Bluetooth</a></li>
                            <li><a href="#">Đồ Chơi SmartPhone</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Phụ Kiện Máy Tính</a>
                        <ul class="sub-category">
                            <li><a href="#">Card Màn Hình</a></li>
                            <li><a href="#">Card Sound USB</a></li>
                            <li><a href="#">Card PCI Express</a></li>
                            <li><a href="#">Chuột Bay, Micro, Webcam</a></li>
                            <li><a href="#">Đầu Đọc Thẻ</a></li>
                            <li><a href="#">Túi Chống Sốc</a></li>
                            <li><a href="#">Đồ Chơi Laptop PC</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Phụ Kiện Xe</a>
                        <ul class="sub-category">
                            <li><a href="#">Phụ Kiện Xe Đạp</a></li>
                            <li><a href="#">Phụ Kiện Ô Tô</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Thiết Bị Mạng</a>
                        <ul class="sub-category">
                            <li><a href="#">Bút Soi Quang</a></li>
                            <li><a href="#">Dao Cắt Quang</a></li>
                            <li><a href="#">Kìm Bấm Mạng</a></li>
                            <li><a href="#">Máy Test Mạng</a></li>
                            <li><a href="#">Dây Cáp Mạng</a></li>
                        </ul>
                    </li>

                    <li class="category">
                        <a href="#" class="category-title">Thiết Bị Ngoại Vi</a>
                        <ul class="sub-category">
                            <li><a href="#">Máy Trợ Giảng, Bút Trình Chiếu</a></li>
                            <li><a href="#">Phụ Kiện Đàn Guitar</a></li>
                            <li><a href="#">Nguồn Sạc</a></li>
                            <li><a href="#">Thiết Bị Khác</a></li>
                        </ul>
                    </li>
                </ul>
            </aside>
        </div>
        <div class="content">
<!--            open wrap-above-content-->
            <div class="wrap-above-content">
                <span class="name-category"><h1>Danh Mục Sản Phẩm</h1></span>
                <span class="ordering">
                    <form class="form-ordering">
                        <select name="order-by" class="order-by">
                            <option value="newest-product" >Sắp xếp theo sản phẩm mới nhất</option>
                            <option value="oldest-product">Sắp xếp theo sản phẩm cũ nhất</option>
                            <option value="cheap-to-exp">Sắp xếp theo giá rẻ đến mắc</option>
                            <option value="exp-to-cheap">Sắp xếp theo giá mắc đến rẻ</option>
                            <option value="low-to-high-score-evaluate">Sắp xếp theo đánh giá thấp đến cao</option>
                            <option value="high-to-low-score-evaluate" selected>Sắp xếp theo đánh giá cao đến thấp</option>
                        </select>
                    </form>
                </span>
            </div>
<!--            close wrap-above-content-->
<!--            open wrap-center-content-->
            <div class="wrap-center-content">
                <div class="products-grid-5">
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_1.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Giá đỡ điện thoại kim loại</div>
                                <div class="price-product-item">45.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_bcth_1.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Switch để bàn TP-Link LS1005G 5 cổng 10/100/1000Mbps</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_bcth_2.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Bộ chia tín hiệu AV (Video và Audio) 1 ra 8</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_bcth_3.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Bộ chia tín hiệu âm thanh RCA 2 trong 4 R/L</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_4.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Lót chuột họa tiết hoa hồn</div>
                                <div class="price-product-item">10.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_1.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Giá đỡ điện thoại kim loại</div>
                                <div class="price-product-item">45.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_bcth_4.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Bộ gộp tín hiệu HDMI 2.0 4k@60Hz</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_3.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Quạt tản nhiệt điện thoại</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_2.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Ốp lưng điện thoại IPhone</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_4.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Kính cường lực kinh kông</div>
                                <div class="price-product-item">11.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_5.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Dây đeo cho điện thoại</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkdt_6.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Dây đính đá gắn vào điện thoại</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_1.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Giá đỡ laptop kim loại</div>
                                <div class="price-product-item">45.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_2.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Tai nghe chụp G58/G70 Pro</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_3.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Dụng cụ 10in1 vệ sinh máy tính</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_5.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Webcam</div>
                                <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="../assets/image/fake_products/item_pkmt_4.webp" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">Lót chuột họa tiết hoa hồng</div>
                                <div class="price-product-item">10.000<span class="underline_dong">đ</span></div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                </div>
            </div>
<!--            close wrap-center-content-->
<!--            open wrap-below-content-->
            <div class="wrap-below-content">
                <div class="show-more-product">
                    <form>
                        <button type="button" class="btn-show-more" name="show-more-product">Xem thêm sản phẩm</button>
                    </form>
                </div>
            </div>
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
</body>
<script src="js/header.js"></script>
</html>