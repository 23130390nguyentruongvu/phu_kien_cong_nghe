<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sản phẩm yêu thích</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css?v=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wishlist.css?v=2">
</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main class="wishlist-page">
        <header class="wishlist-heading">
            <div>
                <p class="wishlist-eyebrow">DANH SÁCH CỦA BẠN</p>
                <h1><i class="fa-regular fa-heart"></i> Sản phẩm yêu thích</h1>
                <p>Lưu sản phẩm tại đây, chọn biến thể và thêm vào giỏ khi bạn sẵn sàng.</p>
            </div>
            <c:choose>
                <c:when test="${requestScope.wishlistGuest}">
                    <a class="wishlist-cart-link" href="${pageContext.request.contextPath}/login?continue=wishlist">
                        <i class="fa-solid fa-arrow-right-to-bracket"></i> Đăng nhập để lưu sản phẩm
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="wishlist-cart-link" href="${pageContext.request.contextPath}/view-cart">
                        <i class="fa-solid fa-cart-shopping"></i> Xem giỏ hàng
                    </a>
                </c:otherwise>
            </c:choose>
        </header>

        <c:if test="${requestScope.wishlistGuest}">
            <div class="wishlist-message guest" role="status">
                Bạn có thể xem trang này khi chưa đăng nhập. Hãy đăng nhập để lưu sản phẩm vào wishlist gắn với tài khoản của bạn.
            </div>
        </c:if>

        <c:if test="${param.success == 'added-to-cart'}">
            <div class="wishlist-message success" role="status">
                Đã thêm sản phẩm vào giỏ hàng. Sản phẩm vẫn được giữ trong wishlist.
            </div>
        </c:if>
        <c:if test="${param.success == 'removed'}">
            <div class="wishlist-message success" role="status">Đã xóa sản phẩm khỏi wishlist.</div>
        </c:if>
        <c:if test="${param.error == 'unavailable'}">
            <div class="wishlist-message error" role="alert">Biến thể đã chọn không còn đủ hàng hoặc không thuộc sản phẩm này. Vui lòng tải lại trang.</div>
        </c:if>
        <c:if test="${param.error == 'invalid'}">
            <div class="wishlist-message error" role="alert">Thông tin gửi lên không hợp lệ. Vui lòng thử lại.</div>
        </c:if>
        <c:if test="${param.error == 'server'}">
            <div class="wishlist-message error" role="alert">Không thể cập nhật wishlist do lỗi hệ thống. Vui lòng thử lại sau.</div>
        </c:if>

        <c:choose>
            <c:when test="${empty requestScope.wishlistProducts}">
                <section class="wishlist-empty">
                    <span class="wishlist-empty-icon"><i class="fa-regular fa-heart"></i></span>
                    <c:choose>
                        <c:when test="${requestScope.wishlistGuest}">
                            <h2>Đăng nhập để xem wishlist của bạn</h2>
                            <p>Sau khi đăng nhập, các sản phẩm yêu thích sẽ được lưu theo tài khoản.</p>
                            <a href="${pageContext.request.contextPath}/login?continue=wishlist">Đăng nhập</a>
                        </c:when>
                        <c:otherwise>
                            <h2>Wishlist của bạn đang trống</h2>
                            <p>Thêm sản phẩm yêu thích từ trang chi tiết để lưu lại tại đây.</p>
                            <a href="${pageContext.request.contextPath}/product-category?id=">Khám phá sản phẩm</a>
                        </c:otherwise>
                    </c:choose>
                </section>
            </c:when>
            <c:otherwise>
                <section class="wishlist-list" aria-label="Danh sách sản phẩm yêu thích">
                    <c:forEach var="item" items="${requestScope.wishlistProducts}">
                        <article class="wishlist-card">
                            <a class="wishlist-product-image"
                               href="${pageContext.request.contextPath}/product-detail?id=${item.product_id}">
                                <c:choose>
                                    <c:when test="${not empty item.image_url}">
                                        <img src="${item.image_url}" alt="${item.name}" loading="lazy">
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa-regular fa-image" aria-hidden="true"></i>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <div class="wishlist-product-info">
                                <a class="wishlist-product-name"
                                   href="${pageContext.request.contextPath}/product-detail?id=${item.product_id}">
                                    <c:out value="${item.name}"/>
                                </a>
                                <p class="wishlist-product-price">
                                    Từ <fmt:formatNumber value="${item.min_price}" pattern="#,##0"/>₫
                                </p>
                                <c:choose>
                                    <c:when test="${item.hasAvailableVariant}">
                                        <form class="wishlist-add-form"
                                              action="${pageContext.request.contextPath}/wishlist" method="post">
                                            <input type="hidden" name="action" value="move">
                                            <input type="hidden" name="productId" value="${item.product_id}">
                                            <label>
                                                <span>Chọn biến thể</span>
                                                <select name="variantId" required>
                                                    <c:forEach var="variant" items="${item.variants}">
                                                        <c:if test="${variant.stock > 0}">
                                                            <option value="${variant.id}">
                                                                <c:out value="${variant.name}"/> ·
                                                                <c:out value="${variant.sku}"/> ·
                                                                <fmt:formatNumber value="${variant.price}" pattern="#,##0"/>₫
                                                                (còn ${variant.stock})
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                            <label class="wishlist-quantity">
                                                <span>Số lượng</span>
                                                <input type="number" name="quantity" value="1" min="1" max="100" required>
                                            </label>
                                            <button class="wishlist-add-button" type="submit">
                                                <i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${item.productAvailable}">
                                                <p class="wishlist-out-of-stock">Hiện chưa có biến thể còn hàng.</p>
                                                <a class="wishlist-view-product"
                                                   href="${pageContext.request.contextPath}/product-detail?id=${item.product_id}">
                                                    Xem sản phẩm
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="wishlist-out-of-stock">Sản phẩm hiện không còn khả dụng.</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <form class="wishlist-remove-form"
                                  action="${pageContext.request.contextPath}/wishlist" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="wishlistId" value="${item.wishlist_id}">
                                <button type="submit" aria-label="Xóa sản phẩm khỏi wishlist"
                                        title="Xóa khỏi wishlist">
                                    <i class="fa-solid fa-trash-can"></i>
                                </button>
                            </form>
                        </article>
                    </c:forEach>
                </section>
            </c:otherwise>
        </c:choose>
    </main>
</div>
</body>
</html>
