<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chào Mừng Admin</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_overview.css">
</head>
<body>
<div class="wrap-all-content">
    <!--    open nav admin-->
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <!--    close nav admin-->
    <!--    open main content admin-->
    <div class="main-content-admin">
        <h1 class="title-for-page">Tổng quan</h1>
        <div class="wrap-row-item-dashboard">
            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Tổng người dùng</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">${requestScope.totalUser}</strong></span>
                <span class="wrap-icon-item-dashboard total-user"><i
                        class="fa-solid fa-users"></i></span>
                </span>
            </span>

            <span class="item-dashboard total-products">
                <p class="title-item-dashboard">Tổng sản phẩm bày bán</p>
                <span class="wrap-small-info">
                     <span class="wrap-quantity"><strong class="quantity">${requestScope.totalVariant}</strong></span>
                <span class="wrap-icon-item-dashboard total-product"><i
                        class="fa-solid fa-boxes-stacked"></i></span></span>
            </span>

            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Đơn giao thành công</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">${requestScope.orderDelivered}</strong></span>
                <span class="wrap-icon-item-dashboard total-order-completed"><i class="fa-solid fa-check"></i></span>
                </span>
            </span>

            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Tổng doanh thu</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">${requestScope.revenue}</strong></span>
<%--                <span class="wrap-icon-item-dashboard total-revenue"  style="margin-left: 100px">--%>
<%--                <i class="fa-solid fa-dollar-sign"></i>--%>
<%--                </span>--%>
                </span>
            </span>
        </div>
        <div class="wrap-order-recent-by-week">
            <div class="filter-by-week">
                <form action="${pageContext.request.contextPath}/overview" method="get" onchange="this.submit()">
                    <select name="filter-by-week" id="filter-by-week-select">
                        <option value="" ${requestScope.option eq '' ? 'selected':''}>Tất cả</option>
                        <option value="1-week" ${requestScope.option eq '1-week' ? 'selected':''}>Lấy các đơn hàng 1
                            tuần
                            trước
                        </option>
                        <option value="2-week" ${requestScope.option eq '2-week' ? 'selected':''}>Lấy các đơn hàng 2
                            tuần trước
                        </option>
                        <option value="3-week" ${requestScope.option eq '3-week' ? 'selected':''}>Lấy các đơn hàng 3
                            tuần trước
                        </option>
                        <option value="4-week" ${requestScope.option eq '4-week' ? 'selected':''}>Lấy các đơn hàng 4
                            tuần
                            trước
                        </option>
                    </select>
                </form>
            </div>
            <div class="board-res-filter-by-week">
                <c:if test="${empty requestScope.orders}">
                    Không thấy đơn hàng nào
                </c:if>
                <c:if test="${not empty requestScope.orders}">
                    <table>
                        <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Id khách hàng</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái đơn</th>
                            <th>Ngày tạo đơn</th>
                            <th>Ngày giao</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${requestScope.orders}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.userId}</td>
                                <td>${order.totalPriceFormat}</td>
                                <td><span class="status-order ${order.statusOrder}">${order.statusOrder}</span></td>
                                <td>${order.orderDateFormat}</td>
                                <td>${order.deliveryDateFormat}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
    <!--    close main content admin-->
</div>
</body>
</html>