<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kết quả tìm kiếm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/item_product.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_result.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/product_sidebar.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css" />
</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main>
        <div class="container">
            <jsp:include page="/WEB-INF/views/common/category_sidebar.jsp" />

            <div class="content">
                <div class="search-result-page">
                    <h3>Kết quả tìm kiếm</h3>

                    <c:if test="${not empty requestScope.keyword}">
                        <p class="result-summary">
                            Từ khóa: <strong>"${requestScope.keyword}"</strong>
                        </p>
                    </c:if>

                    <form method="get" action="">
                        <div class="filter-bar">
                            <h3>Bộ Lọc Sản Phẩm</h3>

                            <div class="filter-grid">
                                <div class="filter-item">
                                    <label for="price">Khoảng giá</label>
                                    <select name="price" id="price">
                                        <option value="">Tất cả</option>
                                        <option value="0-100">Dưới 100.000đ</option>
                                        <option value="100-300">100.000đ - 300.000đ</option>
                                        <option value="300-500">300.000đ - 500.000đ</option>
                                        <option value="500-1000">500.000đ - 1.000.000đ</option>
                                        <option value="1000-up">Trên 1.000.000đ</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <label for="price">Khoảng giá</label>
                                    <select id="prices" id="price">
                                        <option value="">Tất cả</option>
                                        <option value="0-100">Dưới 100.000đ</option>
                                        <option value="100-300">100.000đ - 300.000đ</option>
                                        <option value="300-500">300.000đ - 500.000đ</option>
                                        <option value="500-1000">500.000đ - 1.000.000đ</option>
                                        <option value="1000-up">Trên 1.000.000đ</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <label for="category">Danh mục</label>
                                    <select name="category" id="category">
                                        <option value="">Tất cả</option>
                                        <option value="pc">Phụ kiện máy tính</option>
                                        <option value="phone">Phụ kiện điện thoại</option>
                                        <option value="cable">Cáp tín hiệu</option>
                                        <option value="network">Thiết bị mạng</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <label for="sort">Sắp xếp</label>
                                    <select name="sort" id="sort">
                                        <option value="">Mặc định</option>
                                        <option value="price-asc">Giá tăng dần</option>
                                        <option value="price-desc">Giá giảm dần</option>
                                        <option value="newest">Mới nhất</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <label for="rating">Đánh giá</label>
                                    <select name="rating" id="rating">
                                        <option value="">Tất cả</option>
                                        <option value="5">★★★★★ (5 sao)</option>
                                        <option value="4">★★★★☆ trở lên</option>
                                        <option value="3">★★★☆☆ trở lên</option>
                                        <option value="2">★★☆☆☆ trở lên</option>
                                        <option value="1">★☆☆☆☆ trở lên</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <button type="submit" id="btnFilter">
                                        <i class="fa-solid fa-filter"></i> Lọc
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <c:if test="${empty ProductsResult}">
                        <div class="wrap-center-content">
                            <div style="text-align: center; padding: 60px 0; font-size: 18px; color: #666;">
                                <p>
                                    Không tìm thấy sản phẩm nào cho từ khóa
                                    <strong>"${keyword}"</strong>
                                </p>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty ProductsResult}">
                        <div class="wrap-center-content">
                            <div class="products-grid-5">
                                <c:forEach var="product" items="${ProductsResult}">
                                    <div class="container-product-item">
                                        <div class="item-wrap">
                                            <div class="container-item">
                                                <div class="image-product-item">
                                                    <a
                                                            href="${pageContext.request.contextPath}/product-detail?id=${product.productId}"
                                                    >
                                                        <img src="${product.imageMain}" loading="lazy" />
                                                    </a>
                                                </div>

                                                <div class="title-product-item">${product.name}</div>

                                                <div class="price-product-item">
                                                        ${product.minPriceByFormat}
                                                </div>
                                            </div>
                                        </div>

                                        <div class="wrap-btn-search-similar">
                                            <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
</html>
