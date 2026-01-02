<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh Mục Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product_category.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/product_sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/item_product.css">


</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
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
                <span class="name-category"><h1>${requestScope.CategoryName}</h1></span>
                <span class="ordering">
                    <form class="form-ordering" method="get"
                          action="${pageContext.request.contextPath}/product-category">
                        <input type="hidden" name="id" value="${requestScope.categoryId}">
                        <select name="order-by" class="order-by" onchange="this.form.submit()">
                            <option value="" ${empty requestScope.orderBy?'selected':''}>Sắp xếp</option>
                            <option value="newest-product" ${requestScope.orderBy == 'newest-product'?'selected':''}>
                                Sắp xếp theo sản phẩm mới nhất
                            </option>
                            <option value="oldest-product" ${requestScope.orderBy == 'oldest-product'?'selected':''}>
                                Sắp xếp theo sản phẩm cũ nhất
                            </option>
                            <option value="cheap-to-exp" ${requestScope.orderBy == 'cheap-to-exp'?'selected':''}>
                                Sắp xếp theo giá rẻ đến mắc
                            </option>
                            <option value="exp-to-cheap" ${requestScope.orderBy == 'exp-to-cheap'?'selected':''}>
                                Sắp xếp theo giá mắc đến rẻ
                            </option>
                            <option value="low-to-high-score-evaluate" ${requestScope.orderBy == 'low-to-high-score-evaluate'?'selected':''}>
                                Sắp xếp theo đánh giá thấp đến cao
                            </option>
                            <option value="high-to-low-score-evaluate" ${requestScope.orderBy == 'high-to-low-score-evaluate'?'selected':''}>
                                Sắp xếp theo đánh giá cao đến thấp
                            </option>
                        </select>
                    </form>
                </span>
            </div>
            <!--            close wrap-above-content-->
            <!--            open wrap-center-content-->
            <div class="wrap-center-content">
                <div class="products-grid-5">
                    <c:if test="${not empty requestScope.ProductsResult}">
                        <c:forEach var="product" items="${requestScope.ProductsResult}">
                            <div class="container-product-item">
                                <div class="item-wrap">
                                    <div class="container-item">
                                        <div class="image-product-item">
                                            <img src="${product.imageMain}" loading="lazy"
                                                 decoding="async">
                                        </div>
                                        <div class="title-product-item">${product.name}</div>
                                        <div class="price-product-item">${product.minPriceByFormat}</div>
                                    </div>
                                </div>
                                <div class="wrap-btn-search-similar">
                                    <form>
                                        <input type="hidden" name="productId" value="${product.productId}">
                                        <button type="submit" class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.ProductsResult}">
                        <div>Không có sản phẩm tương ứng</div>
                    </c:if>
                </div>
            </div>
            <!--            close wrap-center-content-->
            <!--            open wrap-below-content-->
            <c:if test="${not empty requestScope.ProductsResult}">
                <div class="wrap-below-content">
                    <div class="show-more-product">
                        <form>
                            <button type="button" class="btn-show-more" name="show-more-product">Xem thêm sản phẩm
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<%--<script src="../../../js/header.js"></script>--%>
</html>