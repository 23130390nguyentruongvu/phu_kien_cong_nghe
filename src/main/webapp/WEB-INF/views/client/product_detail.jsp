<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${product.name}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product_detail.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/item_product.css">
    <script src="${pageContext.request.contextPath}/js/product_detail.js"></script>

</head>
<body>

<%--//TRY CATCH lỗi ABC--%>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main>
        <div class="container">
            <jsp:include page="/WEB-INF/views/common/category_sidebar.jsp"/>

            <div class="content">
                <div class="container-product two-column">
                    <div class="product-image">
                        <c:if test="${not empty product.images}">
                            <!-- Ảnh chính -->
                            <img
                                    id="mainProductImage"
                                    src="${product.images[0].urlImage}"
                                    alt="${product.name}"
                                    class="main-image"
                            />

                            <!-- Danh sách ảnh phụ -->
                            <div class="thumbnail-list">
                                <c:forEach items="${product.images}" var="img" varStatus="st">
                                    <img
                                            class="thumb ${st.first ? 'active' : ''}"
                                            src="${img.urlImage}"
                                            data-pv-id="${img.pvId}"
                                    />
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="product-details">
                        <div class="title-product">${product.name}</div>

                        <div class="price-product">
                        <span class="current-price">
                            ${product.defaultVariant.priceByFormat}
                        </span>
                        </div>

                        <ul class="product-info">
                            <li><strong>Bảo hành:</strong> ${product.warranty} tháng</li>

                            <c:if test="${not empty product.subDescription}">
                                <li class="sub-info">
                                        ${product.subDescription}
                                </li>
                            </c:if>
                        </ul>

                        <form method="POST" action="${pageContext.request.contextPath}/add-cart">

                            <input type="hidden" name="id" value="${product.defaultVariant.id}"/>

                            <input type="hidden" name="name" value="${product.name}"/>

                            <c:if test="${not empty product.variants}">
                                <div class="variant-section">
                                    <div class="variant-title">Chọn loại:</div>

                                    <div class="variant-list">
                                        <c:forEach items="${product.variants}" var="v">

                                            <c:set var="variantImage" value=""/>
                                            <c:forEach items="${product.images}" var="img">
                                                <c:if test="${img.pvId == v.id}">
                                                    <c:set var="variantImage" value="${img.urlImage}"/>
                                                </c:if>
                                            </c:forEach>

                                            <label class="variant-item ${v.id == product.defaultVariant.id ? 'active' : ''}">
                                                <input
                                                        type="radio"
                                                        name="id"
                                                        value="${v.id}"
                                                    ${v.id == product.defaultVariant.id ? 'checked' : ''}
                                                        hidden
                                                />
                                                <img src="${variantImage}" alt="${v.name}"/>
                                                <span>${v.name}</span>
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>

                            <div class="action-row">
                                <div class="quantity-row">
                                    <button type="button" class="quantity-btn" data-action="minus">-</button>

                                    <input
                                            type="number"
                                            name="quantity"
                                            value="1"
                                            min="1"
                                            max="100"
                                            class="quantity-input"
                                    />

                                    <button type="button" class="quantity-btn" data-action="plus">+</button>
                                </div>

                                <button type="submit" class="add-to-cart-btn">
                                    THÊM VÀO GIỎ HÀNG
                                </button>
                            </div>
                        </form>

                        <ul class="product-info">
                            <li>SKU: ${product.defaultVariant.sku}</li>
                            <li>Danh mục: ${product.categoryName}</li>
                            <li><strong>Free ship HCM cho đơn &gt;500k &amp; &lt;5km</strong></li>
                            <li>&gt; Giao hàng nhanh toàn quốc 2–4 ngày</li>
                            <li>&gt; Thanh toán COD / Chuyển khoản</li>
                            <li>&gt; Hoàn tiền nếu sản phẩm sai mô tả</li>
                            <li>&gt; Bảo hành đổi mới</li>
                            <li>&gt; Hỗ trợ 24/7</li>
                        </ul>
                    </div>
                </div>

                <div class="product-description-container">
                    <h3>Mô tả sản phẩm</h3>
                    <li>${product.description}</li>
                </div>

                <div class="review-container">
                    <h3>Đánh giá sản phẩm</h3>
                    <c:if test="${empty requestScope.reviews}">
                        <h4>Chưa có đánh giá</h4>
                    </c:if>
                    <c:if test="${not empty requestScope.reviews}">
                        <div class="reviews-container">
                            <c:forEach var="review" items="${requestScope.reviews}">
                                <div class="review-item">
                                    <div class="review-header">
                                        <div class="user-info">
                                            <span class="user-email">${review.email}</span>

                                            <span class="review-date">${review.formattedDate}</span>
                                        </div>

                                        <div class="star-rating">
                                            <c:forEach begin="1" end="${review.numStar}">
                                                <span class="star filled">★</span>
                                            </c:forEach>

                                            <c:forEach begin="${review.numStar + 1}" end="5">
                                                <span class="star empty">★</span>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <div class="review-body">
                                        <p class="review-text">${review.evaluate}</p>
                                    </div>

                                    <div class="review-footer">
                                        <c:if test="${not empty review.name}">
                                            <small class="customer-name">Khách hàng: <strong>${review.name}</strong></small>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    <br/>
                    <p>
                       Các trường bắt
                        buộc được đánh dấu <span class="required">*</span>
                    </p>
                    <br/>
                    <form class="review-form" method="POST" action="/submit-review">
                        <!-- RATING -->
                        <label for="rating"
                        >Đánh giá của bạn <span class="required">*</span></label
                        >

                        <div class="rating-stars">
                            <input
                                    type="radio"
                                    name="rating"
                                    id="star1"
                                    value="1"
                                    required
                            />
                            <label for="star1" title="1 sao">&#9733;</label>

                            <input type="radio" name="rating" id="star2" value="2"/>
                            <label for="star2" title="2 sao">&#9733;</label>

                            <input type="radio" name="rating" id="star3" value="3"/>
                            <label for="star3" title="3 sao">&#9733;</label>

                            <input type="radio" name="rating" id="star4" value="4"/>
                            <label for="star4" title="4 sao">&#9733;</label>

                            <input type="radio" name="rating" id="star5" value="5"/>
                            <label for="star5" title="5 sao">&#9733;</label>
                        </div>

                        <label for="comment"
                        >Nhận xét <span class="required">*</span></label
                        >
                        <textarea
                                id="comment"
                                name="comment"
                                class="review-textarea"
                                rows="5"
                                required
                        ></textarea>

                        <div class="form-row-container">
                            <div class="form-row">
                                <label for="name">Tên <span class="required">*</span></label>
                                <input
                                        type="text"
                                        id="name"
                                        name="name"
                                        class="review-input"
                                        required
                                />
                            </div>

                            <div class="form-row">
                                <label for="email"
                                >Email <span class="required">*</span></label
                                >
                                <input
                                        type="email"
                                        id="email"
                                        name="email"
                                        class="review-input"
                                        required
                                />
                            </div>
                        </div>

                        <button type="submit" class="submit-review-btn">
                            GỬI ĐÁNH GIÁ
                        </button>
                    </form>
                </div>

                <div class="related-container">
                    <h3>Sản phẩm liên quan</h3>

                    <div class="wrap-center-content">
                        <div class="products-grid-5">

                            <c:if test="${not empty relatedProducts}">
                                <c:forEach var="p" items="${relatedProducts}">
                                    <div class="container-product-item">
                                        <div class="item-wrap">
                                            <div class="container-item">

                                                <div class="image-product-item">
                                                    <a
                                                            href="${pageContext.request.contextPath}/product-detail?id=${p.productId}"
                                                    >
                                                        <img
                                                                src="${p.imageMain}"
                                                                loading="lazy"
                                                                decoding="async"
                                                                alt="${p.name}"
                                                        />
                                                    </a>
                                                </div>

                                                <div class="title-product-item">
                                                    <a style="text-decoration: none; color: black"

                                                       href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">
                                                            ${p.name}
                                                    </a>
                                                </div>

                                                <div class="price-product-item">
                                                        ${p.minPriceByFormat}
                                                </div>

                                            </div>
                                        </div>

                                        <div class="wrap-btn-search-similar">
                                            <button class="search-similar"
                                                    onclick="location.href='${pageContext.request.contextPath}/product-category?id=${product.categoryId}'">
                                                SẢN PHẨM TƯƠNG TỰ
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>

                            <c:if test="${empty relatedProducts}">
                                <div>Không có sản phẩm liên quan</div>
                            </c:if>

                        </div>
                    </div>

                </div>

            </div>

        </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>
