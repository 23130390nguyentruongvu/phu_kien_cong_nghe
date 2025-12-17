<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ Phụ Kiện Công Nghệ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/item_product.css">

</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main>
        <!--        open slider show-->
        <div class="slider-show-wrap">
            <div class="slider-show">
                <div class="slider-show-items">
                    <div class="slider-show-item"><img src="../../../assets/image/item_carousel_1.webp"></div>
                    <div class="slider-show-item"><img src="../../../assets/image/item_carousel_2.webp"></div>
                    <div class="slider-show-item"><img src="../../../assets/image/item_carousel_3.webp"></div>
                    <div class="slider-show-item"><img src="../../../assets/image/item_carousel_4.webp"></div>
                </div>
            </div>

        </div>
        <div class="buttons">
            <div class="left-nav button" onclick="navLeft()"><i class="fa-solid fa-less-than"></i></div>
            <div class="right-nav button" onclick="rightNav()"><i class="fa-solid fa-greater-than"></i></div>
        </div>
        <div class="index-dots">

        </div>
        <!--        close slider show-->
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
        <!--        open product sections-->
        <section id="newProducts" class="product-section">
            <div class="title-section"><span>SẢN PHẨM MỚI</span></div>
            <div class="list-product-by-section">
                <!--                item 1-->
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
                <!--                item 2-->
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
                <!--                item 3-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_bcth_5.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Bộ chia tín hiệu camera, 2 camera lề, 1 camera lùi</div>
                            <div class="price-product-item">100.000.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 4-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_pkmt_1.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Giá đỡ laptop kim loại</div>
                            <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 5-->
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
                <!--                item 6-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_pkmt_6.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Túi chống sốc cho laptop</div>
                            <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                end item-->
            </div>

        </section>

        <section id="featuredProducts" class="product-section">
            <div class="title-section"><span>SẢN PHẨM NỔI BẬT</span></div>
            <div class="list-product-by-section">
                <!--                item 1-->
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
                <!--                item 2-->
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
                <!--                item 3-->
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
                <!--                item 4-->
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
                <!--                item 5-->
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
                <!--                item 6-->
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
                <!--                end item-->
            </div>
        </section>

        <section id="phoneProducts" class="product-section">
            <div class="title-section"><span>PHỤ KIỆN ĐIỆN THOẠI</span></div>
            <div class="list-product-by-section">
                <!--                item 1-->
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
                <!--                item 2-->
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
                <!--                item 3-->
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
                <!--                item 4-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_pkdt_4.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Kính cường lực kinh kông</div>
                            <div class="price-product-item">10.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 5-->
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
                <!--                item 6-->
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
                <!--                end item-->
            </div>
            <div class="show-more-section">
                <button id="showMorePhoneProducts" name="showMorePhoneProducts" class="showMoreSection" type="button">
                    Xem thêm
                </button>
            </div>
        </section>

        <section id="computerProducts" class="product-section">
            <div class="title-section"><span>PHỤ KIỆN MÁY TÍNH</span></div>
            <div class="list-product-by-section">
                <!--                item 1-->
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
                <!--                item 2-->
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
                <!--                item 3-->
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
                <!--                item 4-->
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
                <!--                item 5-->
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
                <!--                item 6-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_pkmt_6.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Túi chống sốc cho laptop</div>
                            <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                end item-->
            </div>
            <div class="show-more-section">
                <button id="showMoreComputerProducts" name="showMoreComputerProducts" class="showMoreSection"
                        type="button">Xem thêm
                </button>
            </div>
        </section>

        <section id="signalSplitterProducts" class="product-section">
            <div class="title-section"><span>BỘ CHIA TÍN HIỆU</span></div>
            <div class="list-product-by-section">
                <!--                item 1-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_bcth_1.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Switch để bàn TP-Link LS1005G 5 cổng 10/100/1000Mbps</div>
                            <div class="price-product-item">45.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 2-->
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
                <!--                item 3-->
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
                <!--                item 4-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_bcth_4.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Bộ gộp tín hiệu HDMI 2.0 4k@60Hz</div>
                            <div class="price-product-item">10.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 5-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_bcth_5.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Bộ chia tín hiệu camera, 2 camera lề, 1 camera lùi</div>
                            <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                item 6-->
                <div class="container-product-item">
                    <div class="item-wrap">
                        <div class="container-item">
                            <div class="image-product-item">
                                <img src="../assets/image/fake_products/item_bcth_6.webp" loading="lazy"
                                     decoding="async">
                            </div>
                            <div class="title-product-item">Bộ khuếch đại truyền hình cáp, chia tín hiệu tivi</div>
                            <div class="price-product-item">100.000<span class="underline_dong">đ</span></div>
                        </div>
                    </div>
                    <div class="wrap-btn-search-similar">
                        <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                    </div>
                </div>
                <!--                end item-->
            </div>
            <div class="show-more-section">
                <button id="showMoreSplitterProducts" name="showMoreSplitterProducts" class="showMoreSection"
                        type="button">Xem thêm
                </button>
            </div>
        </section>
        <!--        close product sections-->
        <section id="articleOfAdmin" class="product-section">
            <div class="title-section"><span>TIN TỨC</span></div>
            <div class="list-article-of-admin">
<!--                article 1-->
                <div class="wrap-article-item">
                    <div class="title-article-of-admin">
                        <h3>Bùng nổ về kính thực tế ảo 2025</h3>
                    </div>
                    <div class="sub-description">
                        <p>Ngày nay kính thực tế ảo đang được nhiều người ưa chuộng, nắm được xu hướng đó, các ông lớn
                            về công nghệ
                            như Meta đã chạy đua để cho ra hàng loạt các mẫu mã kính với giá từ vài triệu đến hàng chục
                            - trăm triệu.
                            Điển hình là kính thực tế Orion do Meta ra mắt, đây là chiếc kính được nghiên cứu trong 5
                            năm qua</p>
                    </div>
                    <div class="read-more-article"><em><a href="">Đọc thêm</a></em></div>
                    <div class="wrap-footer-article">
                        <hr>
                        <div class="post-date">Ngày đăng: 20/10/2025</div>
                        <div class="num-comment">Không có bình luận</div>
                    </div>
                </div>
<!--                article 2-->
                <div class="wrap-article-item">
                    <div class="title-article-of-admin">
                        <h3>Các thiết bị mạng trong hệ thống</h3>
                    </div>
                    <div class="sub-description">
                        <p>Các Thiết bị mạng trong hệ thống mạng bạn cần biết Mạng gia đình hay mạng văn phòng hoặc
                            các mạng lớn hơn đều gọi chung là một hệ thống mạng. Và trong đó chứa rất nhiều phụ kiện,
                            Thiết bị mạng khác nhau. Hôm nay, Phụ Kiện Công Nghệ</p>
                    </div>
                    <div class="read-more-article"><em><a href="">Đọc thêm</a></em></div>
                    <div class="wrap-footer-article">
                        <hr>
                        <div class="post-date">Ngày đăng: 20/10/2025</div>
                        <div class="num-comment">Không có bình luận</div>
                    </div>
                </div>
<!--                article 3-->
                <div class="wrap-article-item">
                    <div class="title-article-of-admin">
                        <h3>Bộ dụng cụ dán phim cách nhiệt</h3>
                    </div>
                    <div class="sub-description">
                        <p>Bộ dụng cụ dán phim cách nhiệt gồm những gì? Bộ dụng cụ dán phim cách nhiệt đóng vai trò quan trọng giúp những
                            người kỹ thuật viên dán phim hiệu quả, không bị bong tróc hay thừa bong bóng khí.
                            Vậy bộ dụng cụ dán phim này gồm những</p>
                    </div>
                    <div class="read-more-article"><em><a href="">Đọc thêm</a></em></div>
                    <div class="wrap-footer-article">
                        <hr>
                        <div class="post-date">Ngày đăng: 11/10/2025</div>
                        <div class="num-comment">1 bình luận</div>
                    </div>
                </div>
<!--                article 4-->
                <div class="wrap-article-item">
                    <div class="title-article-of-admin">
                        <h3>Kết nối Đàn Piano điện với Máy tính</h3>
                    </div>
                    <div class="sub-description">
                        <p>Hướng dẫn kết nối Đàn Piano điện với Máy tính Làm Thế Nào Để Kết Nối Đàn Piano Điện Với Máy Tính?
                            Hãy cùng Phụ Kiện Công Nghệ tìm hiểu nhé. – Trong thời đại công nghệ số, làm thế
                            nào để kết nối đàn piano điện với máy tính</p>
                    </div>
                    <div class="read-more-article"><em><a href="">Đọc thêm</a></em></div>
                    <div class="wrap-footer-article">
                        <hr>
                        <div class="post-date">Ngày đăng: 20/10/2024</div>
                        <div class="num-comment">100 bình luận</div>
                    </div>
                </div>
            </div>
        </section>
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
        <!--        open customer feedback-->
        <div id="feedback" class="customer-feedback">
            <div class="title">PHẢN HỒI CỦA KHÁCH HÀNG</div>
            <div class="container-feedback">
                <span class="customer-1">
                    <div class="customer customer-avatar">
                        <img src="assets/image/customer/customer_1.webp">
                    </div>
                    <div class="customer-name">Trường Vũ</div>
                    <div class="customer-review">
                        <em>Giá cả ở đây phù hợp, chất lượng tốt. Là dân IT nên tôi phải thường xuyên sử dụng các phụ kiện
                        để hỗ trợ công việc, đây là nơi làm cho tôi cảm thấy hài lòng. Chúc shop ngày càng buôn may bán đắt!</em>
                    </div>
                </span>
                <span class="customer customer-2">
                    <div class="customer-avatar">
                        <img src="assets/image/customer/customer_2.webp">
                    </div>
                    <div class="customer-name">Quang Duy</div>
                    <div class="customer-review">
                        <em> Mình thường hay đặt mua hàng của shop này, mình rất tin tưởng vào chất lượng sản phẩm, dịch vụ chăm
                        sóc khách hàng và sự uy tín của shop. Sẽ ủng hộ shop dài dài!</em>
                    </div>
                </span>
                <span class="customer customer-3">
                    <div class="customer-avatar">
                        <img src="assets/image/customer/customer_3.webp">
                    </div>
                    <div class="customer-name">Trọng Tín</div>
                    <div class="customer-review">
                       <em>
                           Dù đã mua qua rất nhiều shop nhưng mình vẫn thích cách làm việc của shop bên đây, tận tâm tận tình, dù
                        đôi lúc mắc lỗi trong quá trình đóng gói sản phẩm nhưng shop rất thiện chí làm việc. Sẽ ủng hộ shop dài lâu!
                       </em>
                    </div>
                </span>
            </div>
        </div>
        <!--        close customer feedback-->
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
        <!--        open commit from shop-->
        <div class="commit">
            <span class="img-shop">
                <img src="../../../assets/image/img_shop.webp">
            </span>
            <span class="content-commit">
                <h2>Phụ Kiện Công Nghệ Cam Kết:</h2>
                <ol type="1">
                    <li>
                        <strong>Giá sản phẩm:</strong>
                        Phụ Kiện Công Nghệ cam kết luôn bán giá tôt nhất trên thị trường vì vậy Quý khách không sợ bị
                        mua đắt, mua hớ. Hơn nữa giá cả được niêm yết công khai trên báo giá hàng ngày.
                    </li>                    <li>
                        <strong>Chất lượng sản phẩm:</strong>
                        Chúng tôi cam kết cung cấp cho Quý khách các loại hàng hóa có chất lượng cao, sản phẩm được
                    test kiểm tra cẩn thận trước khi bàn giao tới tay khách hàng… không bán
                    </li>                    <li>
                        <strong>Thái độ phục vụ:</strong>
                        Đến với Phụ kiện công nghệ, các Bạn sẽ được phục vụ theo đúng phương châm:
                    <strong><em>“SỰ HÀI LÒNG CỦA BẠN LÀ THÀNH CÔNG CỦA CHÚNG TÔI”</em></strong>
                    </li>                    <li>
                        <strong>Dịch vụ hoàn hảo:</strong>
                        Chúng tôi áp dụng chế độ bảo hành siêu tốt, 1 đổi 1 trong suốt thời gian bảo hành, hỗ trợ nhanh
                    24/7 khi Quý khách mua sản phẩm tại Shop chúng tôi.
                    </li>
                </ol>
            </span>
        </div>
        <!--        close commit from shop-->
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
<script src="${pageContext.request.contextPath}/js/home.js"></script>
</html>