<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý cửa hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_content.css">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <main class="main-content-admin content-admin">
        <header class="page-heading">
            <div><span class="eyebrow">THÔNG TIN WEBSITE</span><h1>Quản lý cửa hàng</h1></div>
            <p>Thông tin cửa hàng đang hoạt động được dùng trên trang liên hệ.</p>
        </header>
        <c:if test="${param.status == 'created' || param.status == 'updated' || param.status == 'deleted'}">
            <div class="notice success" role="status">
                <c:choose>
                    <c:when test="${param.status == 'created'}">Đã thêm cửa hàng.</c:when>
                    <c:when test="${param.status == 'updated'}">Đã cập nhật cửa hàng.</c:when>
                    <c:otherwise>Đã xóa cửa hàng.</c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <section class="admin-card">
            <h2>Thêm cửa hàng</h2>
            <form class="editor-form grid-form" action="${pageContext.request.contextPath}/admin/shop-info" method="post">
                <input type="hidden" name="action" value="create">
                <label>Tên cửa hàng <input name="shop_name" maxlength="50" required></label>
                <label>Địa chỉ <input name="shop_address" maxlength="191" required></label>
                <label>Số điện thoại <input name="shop_phone_number" maxlength="20" required></label>
                <label>Hotline <input name="shop_hotline" maxlength="20" required></label>
                <label>Email <input name="shop_email" type="email" maxlength="191"></label>
                <label>Đường dẫn ảnh <input name="img_url" required></label>
                <label>Facebook URL <input name="facebook_url" type="url"></label>
                <label>Instagram URL <input name="instagram_url" type="url"></label>
                <label class="check-label"><input type="checkbox" name="status" value="1"> Đặt làm cửa hàng đang hoạt động</label>
                <button class="button primary" type="submit">Thêm cửa hàng</button>
            </form>
        </section>

        <section class="admin-card">
            <div class="section-heading">
                <h2>Danh sách cửa hàng</h2>
                <span data-testid="search-results-count">${fn:length(shops)} cửa hàng
                    <c:if test="${not empty searchQuery}"> · kết quả cho “${fn:escapeXml(searchQuery)}”</c:if>
                </span>
            </div>
            <form class="record-search" action="${pageContext.request.contextPath}/admin/shop-info" method="get">
                <label for="shop-search">Tìm cửa hàng</label>
                <div class="record-search-controls">
                    <input id="shop-search" type="search" name="q" value="${fn:escapeXml(searchQuery)}"
                           placeholder="Tìm theo mã, tên, địa chỉ, điện thoại hoặc email">
                    <button class="button primary" type="submit">Tìm kiếm</button>
                    <c:if test="${not empty searchQuery}">
                        <a class="button secondary" href="${pageContext.request.contextPath}/admin/shop-info">Xóa tìm kiếm</a>
                    </c:if>
                </div>
            </form>
            <c:choose>
                <c:when test="${empty shops}">
                    <p class="empty-state" data-testid="search-empty-state">
                        <c:choose>
                            <c:when test="${not empty searchQuery}">Không tìm thấy cửa hàng phù hợp với “${fn:escapeXml(searchQuery)}”.</c:when>
                            <c:otherwise>Chưa có thông tin cửa hàng.</c:otherwise>
                        </c:choose>
                    </p>
                </c:when>
                <c:otherwise>
                    <div class="record-list">
                        <c:forEach var="shop" items="${shops}">
                            <article class="record">
                                <div class="record-summary">
                                    <div><h3>${fn:escapeXml(shop.shop_name)}
                                        <c:if test="${shop.status}"><span class="badge">Đang hoạt động</span></c:if>
                                        <c:if test="${not shop.status}"><span class="badge muted">Tạm ẩn</span></c:if>
                                    </h3><p>${fn:escapeXml(shop.shop_address)}</p>
                                        <small>Mã #${shop.id} · ${fn:escapeXml(shop.shop_phone_number)} · ${fn:escapeXml(shop.shop_email)}</small>
                                    </div>
                                    <details class="record-edit">
                                        <summary class="button secondary">Chỉnh sửa</summary>
                                        <form class="editor-form grid-form compact" action="${pageContext.request.contextPath}/admin/shop-info" method="post">
                                            <input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${shop.id}">
                                            <label>Tên cửa hàng <input name="shop_name" maxlength="50" value="${fn:escapeXml(shop.shop_name)}" required></label>
                                            <label>Địa chỉ <input name="shop_address" maxlength="191" value="${fn:escapeXml(shop.shop_address)}" required></label>
                                            <label>Số điện thoại <input name="shop_phone_number" maxlength="20" value="${fn:escapeXml(shop.shop_phone_number)}" required></label>
                                            <label>Hotline <input name="shop_hotline" maxlength="20" value="${fn:escapeXml(shop.shop_hotline)}" required></label>
                                            <label>Email <input name="shop_email" type="email" maxlength="191" value="${fn:escapeXml(shop.shop_email)}"></label>
                                            <label>Đường dẫn ảnh <input name="img_url" value="${fn:escapeXml(shop.img_url)}" required></label>
                                            <label>Facebook URL <input name="facebook_url" type="url" value="${fn:escapeXml(shop.facebook_url)}"></label>
                                            <label>Instagram URL <input name="instagram_url" type="url" value="${fn:escapeXml(shop.instagram_url)}"></label>
                                            <label class="check-label"><input type="checkbox" name="status" value="1" ${shop.status ? 'checked' : ''}> Đang hoạt động</label>
                                            <button class="button primary" type="submit">Lưu thay đổi</button>
                                        </form>
                                    </details>
                                    <form action="${pageContext.request.contextPath}/admin/shop-info" method="post" data-confirm="Xóa cửa hàng này?">
                                        <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${shop.id}">
                                        <input type="hidden" name="q" value="${fn:escapeXml(searchQuery)}">
                                        <button class="button danger" type="submit">Xóa</button>
                                    </form>
                                </div>
                            </article>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin_content.js" defer></script>
</body>
</html>
