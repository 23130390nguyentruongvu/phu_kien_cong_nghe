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
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main>
        <c:if test="${not empty requestScope.SliderShows}">
            <!-- open slider show-->

            <div class="slider-show-wrap">
                <div class="slider-show">
                    <div class="slider-show-items">
                        <c:forEach var="slidershow" items="${requestScope.SliderShows}">
                            <div class="slider-show-item"><img src="${slidershow.urlImage}"></div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="buttons">
                <div class="left-nav button" onclick="navLeft()"><i class="fa-solid fa-less-than"></i></div>
                <div class="right-nav button" onclick="rightNav()"><i class="fa-solid fa-greater-than"></i></div>
            </div>
            <div class="index-dots"></div>
            <!-- close slider show-->
        </c:if>
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
        <!--        open product sections-->
        <section id="newProducts" class="product-section">
            <div class="title-section"><span>SẢN PHẨM MỚI</span></div>
            <div class="list-product-by-section">
                <%--                show item--%>
                <c:forEach var="newProduct" items="${requestScope.NewProducts}">
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="${newProduct.imageMain}" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">${newProduct.name}</div>
                                <div class="price-product-item">${newProduct.minPriceByFormat}</div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                </c:forEach>

            </div>

        </section>

        <c:if test="${not empty requestScope.FeaturedProducts}">
            <section id="featuredProducts" class="product-section">
                <div class="title-section"><span>SẢN PHẨM NỔI BẬT</span></div>
                <div class="list-product-by-section">
                        <%--                show item--%>
                    <c:forEach var="featuredProduct" items="${requestScope.FeaturedProducts}">
                        <div class="container-product-item">
                            <div class="item-wrap">
                                <div class="container-item">
                                    <div class="image-product-item">
                                        <img src="${featuredProduct.imageMain}" loading="lazy"
                                             decoding="async">
                                    </div>
                                    <div class="title-product-item">${featuredProduct.name}</div>
                                    <div class="price-product-item">${featuredProduct.minPriceByFormat}</div>
                                </div>
                            </div>
                            <div class="wrap-btn-search-similar">
                                <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:if>


        <section id="convertVGA" class="product-section">
            <div class="title-section"><span>${requestScope.NameVGA}</span></div>
            <div class="list-product-by-section">
                <%--                show item--%>
                <c:forEach var="vga" items="${requestScope.VGAProducts}">
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="${vga.imageMain}" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">${vga.name}</div>
                                <div class="price-product-item">${vga.minPriceByFormat}</div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="show-more-section">
                <form action="product-category" method="get">
                    <button id="showMorePhoneProducts" name="id" value="${requestScope.CategoryIdVga}"
                            class="showMoreSection" type="submit">
                        Xem thêm
                    </button>
                </form>
            </div>
        </section>

        <section id="keyboard" class="product-section">
            <div class="title-section"><span>${requestScope.KeyboardName}</span></div>
            <div class="list-product-by-section">
                <%--                show item--%>
                <c:forEach var="keyboard" items="${requestScope.KeyboardProducts}">
                    <div class="container-product-item">
                        <div class="item-wrap">
                            <div class="container-item">
                                <div class="image-product-item">
                                    <img src="${keyboard.imageMain}" loading="lazy"
                                         decoding="async">
                                </div>
                                <div class="title-product-item">${keyboard.name}</div>
                                <div class="price-product-item">${keyboard.minPriceByFormat}</div>
                            </div>
                        </div>
                        <div class="wrap-btn-search-similar">
                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="show-more-section">
                <form action="product-category" method="get">
                    <button id="showMorePhoneProducts" name="id" value="${requestScope.CategoryIdVga}"
                            class="showMoreSection" type="submit">
                        Xem thêm
                    </button>
                </form>
            </div>
        </section>

        <!--        close product sections-->
        <section id="articleOfAdmin" class="product-section">
            <div class="title-section"><span>TIN TỨC</span></div>
            <div class="list-article-of-admin">
                <!--                article item-->
                <c:if test="${not empty requestScope.ArticleItems}">
                    <c:forEach var="article" items="${requestScope.ArticleItems}">
                        <div class="wrap-article-item">
                            <div class="title-article-of-admin">
                                <h3>${article.title}</h3>
                            </div>
                            <div class="sub-description">
                                <p>${article.subDescription}</p>
                            </div>
                            <div class="read-more-article"><em><a href="">Đọc thêm</a></em></div>
                            <div class="wrap-footer-article">
                                <hr>
                                <div class="post-date">Ngày đăng: ${article.postDateFormat}</div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </section>
        <hr style="margin-top: 5px; margin-bottom: 15px; background-color: rgb(4, 107, 210); height: 1px">
        <!--        open commit from shop-->
        <div class="commit">
            <span class="img-shop">
                <img src="${applicationScope.ContactShop.avatar}">
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
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/home.js"></script>
</html>