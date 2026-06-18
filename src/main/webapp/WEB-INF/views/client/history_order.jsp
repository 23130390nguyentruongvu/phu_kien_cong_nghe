<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lịch Sử Đơn Hàng</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/history_order.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main>
    <div class="wrap-content-all">
        <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
        <div class="main-content">
             <span class="filter">
                  <form class="form-filter" method="get" action="${pageContext.request.contextPath}/order-history"
                        onchange="this.submit()">
                        <select name="filter-by" class="filter-by">
                            <option value="" ${empty requestScope.filterBy ? 'selected' : ''}>Tất cả trạng thái</option>
                            <option value="pending_signature" ${requestScope.filterBy == 'pending_signature' ? 'selected' : ''}>Chờ khách hàng ký số</option>
                            <option value="signed" ${requestScope.filterBy == 'signed' ? 'selected' : ''}>Đã ký số - Chờ Admin duyệt</option>
                            <option value="waiting_re_sign" ${requestScope.filterBy == 'waiting_re_sign' ? 'selected' : ''}>Đơn bị sửa - Chờ khách ký lại</option>
                            <option value="approved" ${requestScope.filterBy == 'approved' ? 'selected' : ''}>Admin đã duyệt đơn</option>
                            <option value="rejected" ${requestScope.filterBy == 'rejected' ? 'selected' : ''}>Admin từ chối duyệt</option>
                            <option value="security_alert" ${requestScope.filterBy == 'security_alert' ? 'selected' : ''}>CẢNH BÁO: Vi phạm tính toàn vẹn!</option>
                            <option value="completed" ${requestScope.filterBy == 'completed' ? 'selected' : ''}>Đã giao</option>
                            <option value="cancel" ${requestScope.filterBy == 'cancel' ? 'selected' : ''}>Đã hủy</option>
                            <option value="pending" ${requestScope.filterBy == 'pending' ? 'selected' : ''}>Đang chờ xử lí</option>
                        </select>

                        <select style="margin-left: 18px" name="verifyFilter" class="filter-by"
                                onchange="this.form.submit()">
                    <option value="both" ${requestScope.verifyFilter == 'both' || empty requestScope.verifyFilter ? 'selected' : ''}>Đơn xác thực và không xác thực</option>
                    <option value="verify" ${requestScope.verifyFilter == 'verify' ? 'selected' : ''}>Đơn xác thực</option>
                    <option value="un-verify" ${requestScope.verifyFilter == 'un-verify' ? 'selected' : ''}>Đơn không xác thực</option>
                </select>
                    </form>
                </span>
            <c:if test="${empty requestScope.orders}">
                <div class="wrap-content-order">
                    <h3>Không tìm thấy đơn hàng nào</h3>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.orders}">
                <c:forEach var="order" items="${requestScope.orders}">
                    <div class="wrap-content-order" data-id="${order.id}">
                        <div class="header-order">
                            <div class="header-order-top">
                                <c:choose>
                                    <c:when test="${order.verify == false}">
                                        <span class="verify-icon no-verify"><i style="color: #dc2626"
                                                                               class="fa-solid fa-minus"></i>
</span>
                                    </c:when>
                                    <c:when test="${order.verify == true}">
                                        <span class="verify-icon verified"><i
                                                class="fa-solid fa-circle-check"></i></span>
                                    </c:when>

                                </c:choose>

                                <c:if test="${order.statusOrder == 'pending_signature' || order.statusOrder == 'waiting_re_sign'}">
                                    <button type="button" class="btn-sign-order" data-order-id="${order.id}">
                                        <i class="fa-solid fa-file-signature"></i>
                                        <span class="btn-sign-text">Ký đơn hàng</span>
                                    </button>
                                </c:if>
                            </div>
                            <span class="status-order">
                                <strong>Mã đơn hàng:</strong> <em>#${order.id}</em> |
                                <strong>Trạng thái:</strong>
                                <em class="${order.statusOrder}">${order.statusOrder}</em>
                            </span>

                            <c:if test="${not empty order.note}">
                                <div class="order-note">
                                    <i class="fa-solid fa-note-sticky"></i>
                                    <span>${order.note}</span>
                                </div>
                            </c:if>

                            <span class="address-shipping">
                                <em>
                                    <i class="fa-solid fa-truck"></i>
                                    Người nhận: ${order.addressOrderSnapshot.receiverName} (${order.addressOrderSnapshot.phoneNumber}) - ${order.addressOrderSnapshot.addressDetail}
                                </em>
                            </span>
                        </div>

                        <c:forEach var="orderDetail" items="${order.orderDetailSnapshots}">
                            <div class="per-cart-item">
                                <span class="info-base-cart-item">
                                    <%-- Tên sản phẩm lưu trong Snapshot --%>
                                    <span class="name-cart-item"><strong>${orderDetail.productNameSnapshot}</strong></span>

                                    <%-- Thông tin phân loại cấu hình sản phẩm --%>
                                    <div class="info-cart-item">
                                        Phân loại: ${orderDetail.variantNameSnapshot}
                                        <c:if test="${not empty orderDetail.colorSnapshot}"> | Màu: ${orderDetail.colorSnapshot}</c:if>
                                        <c:if test="${not empty orderDetail.sizeSnapshot}"> | Kích thước: ${orderDetail.sizeSnapshot}</c:if>
                                    </div>

                                    <div class="info-quantity-cart-item">Số lượng: ${orderDetail.quantity}</div>

                                    <div class="price-cart-item">
                                        Đơn giá: <fmt:formatNumber value="${orderDetail.variantPriceSnapshot}"
                                                                   type="currency" currencySymbol="đ"
                                                                   maxFractionDigits="0"/>
                                    </div>
                                </span>
                            </div>
                        </c:forEach>

                        <div class="footer-order">
                            <p class="price-total-pay-order">
                                <i class="fa-solid fa-dollar-sign"></i>Tổng số tiền:
                                <fmt:formatNumber value="${order.totalMustPay}" type="currency" currencySymbol="đ"
                                                  maxFractionDigits="0"/>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<script>
    const CURRENT_USER_ID = ${sessionScope.user.id != null ? sessionScope.user.id : "null"};
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/order_history.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/sidebar_account_change_avatar.js"></script>
</html>