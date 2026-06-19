<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_order.css?v=2">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <div class="main-content-admin">
        <h1 class="title-for-page">Quản lí đơn hàng</h1>
        <div class="wrap-find-info-order">
            <form action="${pageContext.request.contextPath}/admin-order" method="get">
                <input type="text" name="keySearch" class="input-order-id"
                       value="${empty requestScope.keySearch?'':requestScope.keySearch}"
                       placeholder="Tìm kiếm theo mã đơn hàng hoặc tên người nhận">
                <select name="statusFilter" class="filter-status-select" onchange="this.form.submit()">
                    <option value="" ${empty requestScope.statusFilter ? 'selected' : ''}>Tất cả trạng thái</option>
                    <option value="pending_signature" ${requestScope.statusFilter == 'pending_signature' ? 'selected' : ''}>Chờ khách hàng ký số</option>
                    <option value="signed" ${requestScope.statusFilter == 'signed' ? 'selected' : ''}>Đã ký số - Chờ duyệt</option>
                    <option value="waiting_re_sign" ${requestScope.statusFilter == 'waiting_re_sign' ? 'selected' : ''}>Chờ khách ký lại</option>
                    <option value="approved" ${requestScope.statusFilter == 'approved' ? 'selected' : ''}>Đã duyệt</option>
                    <option value="rejected" ${requestScope.statusFilter == 'rejected' ? 'selected' : ''}>Từ chối duyệt</option>
                    <option value="security_alert" ${requestScope.statusFilter == 'security_alert' ? 'selected' : ''}>Vi phạm tính toàn vẹn</option>
                    <option value="pending" ${requestScope.statusFilter == 'pending' ? 'selected' : ''}>Đang xử lý</option>
                    <option value="shipping" ${requestScope.statusFilter == 'shipping' ? 'selected' : ''}>Đang vận chuyển</option>
                    <option value="completed" ${requestScope.statusFilter == 'completed' ? 'selected' : ''}>Hoàn thành</option>
                    <option value="cancel" ${requestScope.statusFilter == 'cancel' ? 'selected' : ''}>Đã hủy</option>
                </select>

                <select style="margin-left: 18px" name="verifyFilter" class="filter-status-select" onchange="this.form.submit()">
                    <option value="both" ${requestScope.verifyFilter == 'both' || empty requestScope.verifyFilter ? 'selected' : ''}>Đơn xác thực và không xác thực</option>
                    <option value="verify" ${requestScope.verifyFilter == 'verify' ? 'selected' : ''}>Đơn xác thực</option>
                    <option value="un-verify" ${requestScope.verifyFilter == 'un-verify' ? 'selected' : ''}>Đơn không xác thực</option>
                </select>
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
                        <th>Xác thực</th>
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
                            <td class="verify-cell">
                                <c:choose>
                                    <c:when test="${order.verify == false}">
                                        <span class="verify-icon no-verify"><i style="color: #dc2626" class="fa-solid fa-minus"></i>
</span>
                                    </c:when>
                                    <c:when test="${order.verify == true}">
                                        <span class="verify-icon verified" ><i class="fa-solid fa-circle-check"></i></span>
                                    </c:when>

                                </c:choose>
                            </td>
                                <%-- Sửa thuộc tính: orderId -> id --%>
                            <td>#${order.id}</td>
                            <td>${order.userId}</td>
                                <%-- Sửa thuộc tính: lấy từ object liên kết AddressOrderSnapshot --%>
                            <td>${order.addressOrderSnapshot.receiverName}</td>
                            <td>${order.addressOrderSnapshot.phoneNumber}</td>
                            <td class="address-cell" title="${order.addressOrderSnapshot.addressDetail} ,${order.addressOrderSnapshot.district}, ${order.addressOrderSnapshot.provinceCity}">
                                    ${order.addressOrderSnapshot.addressDetail},${order.addressOrderSnapshot.district},${order.addressOrderSnapshot.provinceCity}
                            </td>
                                <%-- Sửa thuộc tính: Định dạng tiền tệ VND tự động cho totalMustPay thay vì dùng hàm cũ --%>
                            <td class="price-cell">
                                <fmt:formatNumber value="${order.totalMustPay}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                            </td>
                            <td>
                                <span class="status-order ${order.statusOrder}">${order.statusOrder}</span>
                            </td>
                                <%-- Sửa thuộc tính: Sử dụng thư viện fmt để format định dạng chuẩn cho LocalDateTime (orderDate) --%>
                            <td>
                                <fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>
                                <span class="edit-order">
                                    <%-- Sửa thuộc tính: orderId -> id --%>
                                    <span class="edit-order-update" data-id="${order.id}"
                                          title="Sửa thông tin đơn hàng"><i class="fa-solid fa-pen-to-square"></i></span>
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
    <div id="popup-edit-order" class="popup" style="display:none;">
        <div class="popup-content edit-order-content">
            <h2>Chỉnh sửa đơn hàng #<span id="edit-order-id"></span></h2>
            <form id="editOrderForm">
                <div id="content-edit-order" class="content-edit-order">
                    <p>Đang tải dữ liệu...</p>
                </div>
                <div class="popup-actions">
                    <button type="submit" id="submit-update-order" class="submit">Cập nhật</button>
                    <button type="button" id="closeEditOrder" class="close">Đóng</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/popup_common.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/admin_order.js"></script>
</html>