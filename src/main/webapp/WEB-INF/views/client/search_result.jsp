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

                    <form method="get" action="${pageContext.request.contextPath}/search">
                        <input type="hidden" name="keyword" value="${keyword}" />

                        <div class="filter-bar">
                            <h3>Bộ Lọc Sản Phẩm</h3>

                            <div class="filter-grid">
                                <div class="filter-item">
                                    <label for="price">Khoảng giá</label>
                                    <select name="price" id="price">
                                        <option value="" ${empty price ? 'selected' : ''}>Tất cả</option>
                                        <option value="0-100" ${price == '0-100' ? 'selected' : ''}>Dưới 100.000đ</option>
                                        <option value="100-300" ${price == '100-300' ? 'selected' : ''}>100.000đ - 300.000đ</option>
                                        <option value="300-500" ${price == '300-500' ? 'selected' : ''}>300.000đ - 500.000đ</option>
                                        <option value="500-1000" ${price == '500-1000' ? 'selected' : ''}>500.000đ - 1.000.000đ</option>
                                        <option value="1000-up" ${price == '1000-up' ? 'selected' : ''}>Trên 1.000.000đ</option>
                                    </select>
                                </div>

                                <div class="filter-item">
                                    <label for="category">Danh mục</label>
                                    <select name="category" id="category">
                                        <option value="" ${empty param.category ? 'selected' : ''}>
                                            Tất cả
                                        </option>

                                        <c:forEach var="parent"
                                                   items="${applicationScope.ParentCategories}">

                                            <option value="${parent.id}"
                                                ${param.category == parent.id ? 'selected' : ''}>
                                                    ${parent.nameCategory}
                                            </option>

                                            <c:forEach var="child"
                                                       items="${applicationScope.SubCategoryMap[parent.id]}">

                                                <option value="${child.id}"
                                                    ${param.category == child.id ? 'selected' : ''}>
                                                    └─ ${child.nameCategory}
                                                </option>

                                            </c:forEach>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="filter-item">
                                    <label for="sort">Sắp xếp</label>
                                    <select name="sort" id="sort">
                                        <option value="" ${empty sort ? 'selected' : ''}>Mặc định</option>
                                        <option value="price-asc" ${sort == 'price-asc' ? 'selected' : ''}>
                                            Giá tăng dần
                                        </option>
                                        <option value="price-desc" ${sort == 'price-desc' ? 'selected' : ''}>
                                            Giá giảm dần
                                        </option>
                                        <option value="newest" ${sort == 'newest' ? 'selected' : ''}>
                                            Mới nhất
                                        </option>
                                    </select>
                                </div>


                                <div class="filter-item">
                                    <label for="rating">Đánh giá</label>
                                    <select name="rating" id="rating">
                                        <option value="" ${empty rating ? 'selected' : ''}>Tất cả</option>

                                        <option value="5" ${rating == '5' ? 'selected' : ''}>
                                            ★★★★★ (5 sao)
                                        </option>

                                        <option value="4" ${rating == '4' ? 'selected' : ''}>
                                            ★★★★☆ trở lên
                                        </option>

                                        <option value="3" ${rating == '3' ? 'selected' : ''}>
                                            ★★★☆☆ trở lên
                                        </option>

                                        <option value="2" ${rating == '2' ? 'selected' : ''}>
                                            ★★☆☆☆ trở lên
                                        </option>

                                        <option value="1" ${rating == '1' ? 'selected' : ''}>
                                            ★☆☆☆☆ trở lên
                                        </option>
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
