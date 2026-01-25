<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
        <!--    open account nav-->
        <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
        <!--    close account nav-->
        <!--        open main content-->
        <div class="main-content">
             <span class="filter">
                    <form class="form-filter" method="get" action="${pageContext.request.contextPath}/order-history"
                          onchange="this.submit()">
                        <select name="filter-by" class="filter-by">
                            <option value="" ${empty requestScope.filterBy ? 'selected' : ''}>Tất cả trạng thái</option>
                            <option value="completed" ${requestScope.filterBy == 'completed' ? 'selected' : ''}>Đã giao</option>
                            <option value="cancel" ${requestScope.filterBy == 'cancel' ? 'selected' : ''}>Đã hủy</option>
                            <option value="shipping" ${requestScope.filterBy == 'shipping' ? 'selected' : ''}>Đang giao hàng</option>
                            <option value="pending" ${requestScope.filterBy == 'pending' ? 'selected' : ''}>Đang chờ xử lí</option>
                        </select>
                    </form>
                </span>
            <c:if test="${empty requestScope.orders}">
                <div class="wrap-content-order" data-id="${-1}">
                    <h3>Không tìm thấy đơn hàng nào</h3>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.orders}">
                <c:forEach var="order" items="${requestScope.orders}">
                    <div class="wrap-content-order" data-id="${order.orderId}">
                        <div class="header-order">
                        <span class="status-order"><strong>Trạng thái:
                            <em class="${order.status}">${order.status}</em>
                        </strong>
                        </span>
                            <span class="address-shipping"><em><i
                                    class="fa-regular fa-truck"></i> ${order.address}</em></span>

                        </div>
                        <c:forEach var="orderDetail" items="${order.orderDetails}">
                            <div class="per-cart-item">
                    <span class="img-cart-item"><img src="${orderDetail.urlImage}"
                                                     loading="lazy"></span>
                                <span class="info-base-cart-item">
                    <span class="name-cart-item"><strong>${orderDetail.name}</strong></span>
                    <div class="info-cart-item">${orderDetail.type}</div>
                        <div class="info-quantity-cart-item">Số lượng: ${orderDetail.quantity}</div>
                    <div class="price-cart-item">${orderDetail.priceByFormat}</div>
                </span>
                            </div>
                        </c:forEach>
                        <div class="footer-order">
                            <p class="price-total-pay-order"><i class="fa-solid fa-dollar-sign"></i>Tổng số tiền:
                                    ${order.priceFormat}
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <!--        close main content-->
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/order_history.js"></script>
</html>