<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lí đơn hàng</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_order.css">
</head>
<body>
<div class="wrap-all-content">
    <!--    open nav admin-->
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <!--    close nav admin-->
    <!--    open main content admin-->
    <div class="main-content-admin">
        <h1 class="title-for-page">Quản lí đơn hàng</h1>
        <div class="wrap-find-info-order">
            <form action="${pageContext.request.contextPath}/admin-order" method="get">
                <input type="text" name="keySearch" class="input-order-id"
                       value="${empty requestScope.keySearch?'':requestScope.keySearch}"
                       placeholder="Tìm kiếm theo mã đơn hàng hoặc tên người nhận">
                <button type="submit" class="submit-data">Tìm kiếm</button>
            </form>
        </div>
        <div class="board-res-order">
            <c:if test="${empty requestScope.orders}">
                <div>
                    <h3>Không có đơn hàng nào</h3>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.orders}">
                <table>
                    <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Mã KH</th>
                        <th>Người nhận</th>
                        <th>Số điện thoại</th>
                        <th>Địa chỉ</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Ngày đặt</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${requestScope.orders}">
                        <tr>
                            <td>#${order.orderId}</td>
                            <td>${order.userId}</td>
                            <td>${order.receiverName}</td>
                            <td>${order.phoneNumber}</td>
                            <td class="address-cell" title="${order.addressDetail}">${order.addressDetail}</td>
                            <td class="price-cell">${order.totalPriceFormat}</td>
                            <td>
                                <span class="status-order ${order.statusOrder}">${order.statusDisplay}</span>
                            </td>
                            <td>${order.orderDateFormat}</td>
                            <td>
                                <span class="edit-order">
                                    <span class="edit-order-update" data-id="${order.orderId}"
                                          title="Sửa thông tin đơn hàng"><i class="fa-solid fa-pen-to-square"></i></span>
                                    <span class="edit-order-add-item" data-id="${order.orderId}"
                                          title="Thêm sản phẩm vào đơn"><i class="fa-solid fa-circle-plus"></i></span>
                                    <span class="edit-order-remove" data-id="${order.orderId}"
                                          title="Xóa đơn hàng"><i class="fa-solid fa-circle-minus"></i></span>
                                    <span class="edit-order-view" data-id="${order.orderId}"
                                          title="Xem chi tiết đơn hàng"><i class="fa-solid fa-eye"></i></span>
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
    <!--    close main content admin-->

    <!--    TODO: Popup xem chi tiết đơn hàng -->
    <div id="popup-order-detail" class="popup" style="display:none;">
        <div class="popup-content">
            <h2>Chi tiết đơn hàng #<span id="display-order-id"></span></h2>
            <div id="order-detail-container">
                <p>Đang tải dữ liệu...</p>
            </div>
            <button type="button" id="closeOrderDetail" class="close-popup-btn">Đóng</button>
        </div>
    </div>

    <!--    TODO: Popup sửa thông tin đơn hàng -->
    <div id="popup-edit-order" class="popup" style="display:none;">
        <div class="popup-content edit-order-content">
            <h2>Chỉnh sửa đơn hàng #<span id="edit-order-id"></span></h2>
            <form id="editOrderForm">
                <div class="content-edit-order">
                    <p>Chức năng đang phát triển...</p>
                </div>
                <div class="popup-actions">
                    <button type="submit" id="submit-update-order">Cập nhật</button>
                    <button type="button" id="closeEditOrder">Đóng</button>
                </div>
            </form>
        </div>
    </div>

    <!--    TODO: Popup thêm sản phẩm vào đơn -->
    <div id="popup-add-order-item" class="popup" style="display:none;">
        <div class="popup-content">
            <h2>Thêm sản phẩm vào đơn hàng #<span id="add-item-order-id"></span></h2>
            <form id="addOrderItemForm" method="post">
                <input type="text" class="form-input" name="productName" placeholder="Tên sản phẩm" required>
                <br>
                <input type="number" class="form-input" name="quantity" placeholder="Số lượng" required>
                <br>
                <input type="number" class="form-input" name="price" placeholder="Đơn giá" required>
                <br>
                <div class="wrap-button-cancel-submit">
                    <button type="submit" id="submitAddOrderItem" class="submit">Thêm</button>
                    <button type="button" id="closeAddOrderItem" class="close">Đóng</button>
                </div>
            </form>
        </div>
    </div>

    <!--    TODO: Popup xác nhận xóa đơn hàng -->
    <div id="popup-confirm" class="popup" style="display:none;">
        <div class="popup-content confirm-content">
            <h2>Xác nhận xóa</h2>
            <p id="confirmMessage">Bạn có chắc chắn muốn xóa đơn hàng này?</p>
            <form id="confirmForm" method="post">
                <div class="wrap-button-cancel-submit">
                    <button type="submit" id="confirmYes" class="submit">Đồng ý</button>
                    <button type="button" id="confirmNo" class="close">Hủy</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/admin_order.js"></script>
</html>
