<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kho voucher</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css?v=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/voucher.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer">
</head>
<body class="voucher-store-page">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main class="voucher-store">
    <header class="voucher-store-heading">
        <span>ƯU ĐÃI DÀNH CHO BẠN</span>
        <h1>Kho voucher</h1>
        <p>Voucher còn hạn và còn lượt sử dụng. Chọn sản phẩm phù hợp rồi áp dụng mã tại trang thanh toán.</p>
    </header>
    <c:choose>
        <c:when test="${empty vouchers}">
            <div class="voucher-empty"><i class="fa-solid fa-ticket"></i><h2>Chưa có voucher khả dụng</h2>
                <p>Hãy quay lại sau để xem các chương trình ưu đãi mới.</p></div>
        </c:when>
        <c:otherwise>
            <div class="voucher-store-grid">
                <c:forEach var="voucher" items="${vouchers}">
                    <article class="voucher-card">
                        <div class="voucher-card-discount">
                            <c:choose>
                                <c:when test="${voucher.discount_type == 'PERCENT'}">
                                    <strong><fmt:formatNumber value="${voucher.discount_value}" maxFractionDigits="2"/>%</strong>
                                    <span>GIẢM GIÁ</span>
                                </c:when>
                                <c:when test="${voucher.discount_type == 'FREESHIP'}">
                                    <strong><i class="fa-solid fa-truck-fast"></i></strong><span>FREESHIP</span>
                                </c:when>
                                <c:otherwise>
                                    <strong><fmt:formatNumber value="${voucher.discount_value}" pattern="#,###"/></strong>
                                    <span>₫ GIẢM</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="voucher-card-info">
                            <h2><c:out value="${voucher.name}"/></h2>
                            <p><c:out value="${voucher.description}"/></p>
                            <div class="voucher-code-row"><span>Mã: <strong><c:out value="${voucher.code}"/></strong></span>
                                <button type="button" class="voucher-copy" data-code="${fn:escapeXml(voucher.code)}">Sao chép</button>
                            </div>
                            <small>
                                <c:choose>
                                    <c:when test="${voucher.discount_type == 'FREESHIP'}">Áp dụng giảm phí vận chuyển</c:when>
                                    <c:otherwise>Áp dụng giảm giá sản phẩm</c:otherwise>
                                    </c:choose>
                                    · Đơn tối thiểu <fmt:formatNumber value="${voucher.min_order_value}" pattern="#,###"/>₫ (chưa gồm phí ship)
                                <c:if test="${voucher.discount_type == 'PERCENT' && not empty voucher.max_discount_amount}">
                                    · Giảm tối đa <fmt:formatNumber value="${voucher.max_discount_amount}" pattern="#,###"/>₫
                                </c:if>
                            </small>
                            <c:if test="${not empty voucher.category_names || not empty voucher.product_names}">
                                <p class="voucher-scope">
                                    <c:if test="${not empty voucher.category_names}">Danh mục: <c:out value="${voucher.category_names}"/></c:if>
                                    <c:if test="${not empty voucher.category_names && not empty voucher.product_names}"> Hoặc </c:if>
                                    <c:if test="${not empty voucher.product_names}">Sản phẩm: <c:out value="${voucher.product_names}"/></c:if>
                                </p>
                            </c:if>
                            <c:if test="${empty voucher.category_names && empty voucher.product_names}">
                                <p class="voucher-scope">Áp dụng cho toàn bộ sản phẩm</p>
                            </c:if>
                            <small class="voucher-expiry">Hạn dùng đến <fmt:formatDate value="${voucher.end_date}" pattern="dd/MM/yyyy HH:mm"/></small>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/voucher_store.js" defer></script>
</body>
</html>
