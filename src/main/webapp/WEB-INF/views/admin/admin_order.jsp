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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_order.css?v=3">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <div class="main-content-admin">
        <h1 class="title-for-page">Quản lí đơn hàng</h1>
        <c:if test="${not empty param.createdOrderId}">
            <div class="order-create-feedback success">
                Đã tạo đơn hàng #${param.createdOrderId}. Trạng thái là <strong>Chờ khách hàng ký số</strong>; chưa áp dụng voucher.
            </div>
        </c:if>
        <c:if test="${param.createError == 'invalid' || param.createError == 'items'}">
            <div class="order-create-feedback error">Thông tin tạo đơn không hợp lệ. Hãy chọn khách hàng, địa chỉ, phương thức thanh toán và ít nhất một biến thể với số lượng hợp lệ.</div>
        </c:if>
        <c:if test="${param.createError == 'business'}">
            <div class="order-create-feedback error">Không thể tạo đơn: khách hàng, địa chỉ, phương thức thanh toán hoặc tồn kho không hợp lệ.</div>
        </c:if>
        <c:if test="${param.createError == 'server'}">
            <div class="order-create-feedback error">Có lỗi hệ thống khi tạo đơn. Vui lòng thử lại hoặc kiểm tra log server.</div>
        </c:if>
        <details class="create-order-panel">
            <summary><i class="fa-solid fa-circle-plus"></i> Tạo đơn hàng cho khách hàng</summary>
            <form id="createOrderForm" action="${pageContext.request.contextPath}/admin-order" method="post">
                <input type="hidden" name="action" value="create">
                <div class="create-order-fields">
                    <label>Khách hàng
                        <select id="create-order-customer" name="customerId" required>
                            <option value="">Chọn khách hàng</option>
                            <c:forEach var="customer" items="${requestScope.createCustomers}">
                                <option value="${customer.id}"><c:out value="${customer.full_name}"/> (@<c:out value="${customer.user_name}"/>) · #${customer.id}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>Địa chỉ nhận hàng
                        <select id="create-order-address" name="addressId" required disabled>
                            <option value="">Chọn khách hàng trước</option>
                            <c:forEach var="address" items="${requestScope.createAddresses}">
                                <option value="${address.id}" data-user-id="${address.user_id}">
                                    <c:out value="${address.receiver_name}"/> · <c:out value="${address.phone_number}"/> ·
                                    <c:out value="${address.address_detail}"/>, <c:out value="${address.province_city}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>Phương thức thanh toán
                        <select name="paymentMethodId" required>
                            <option value="">Chọn phương thức</option>
                            <c:forEach var="method" items="${requestScope.createPaymentMethods}">
                                <option value="${method.id}"><c:out value="${method.name_method}"/></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="create-order-items-heading">
                    <h3>Sản phẩm trong đơn</h3>
                    <button type="button" id="add-order-item" class="create-order-add-item">+ Thêm sản phẩm</button>
                </div>
                <div id="create-order-items">
                    <div class="create-order-item">
                        <label>Sản phẩm / biến thể
                            <select name="variantIds" required>
                                <option value="">Chọn sản phẩm</option>
                                <c:forEach var="variant" items="${requestScope.createVariants}">
                                    <option value="${variant.id}">
                                        <c:out value="${variant.product_name}"/> — <c:out value="${variant.variant_name}"/>
                                        (<c:out value="${variant.sku}"/>, còn ${variant.stock}) ·
                                        <fmt:formatNumber value="${variant.price}" pattern="#,###"/>₫
                                    </option>
                                </c:forEach>
                            </select>
                        </label>
                        <label>Số lượng
                            <input name="quantities" type="number" min="1" value="1" required>
                        </label>
                        <button type="button" class="remove-order-item" aria-label="Xóa dòng sản phẩm">Xóa</button>
                    </div>
                </div>
                <label class="create-order-note">Ghi chú
                    <textarea name="note" maxlength="191" rows="2"></textarea>
                </label>
                <p class="create-order-hint">Đơn tạo tại admin luôn ở trạng thái chờ khách hàng ký số. Số lượng tồn kho được giữ ngay khi tạo đơn; không áp dụng voucher.</p>
                <button type="submit" class="create-order-submit">Tạo đơn và giữ tồn kho</button>
            </form>
        </details>
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
<script src="${pageContext.request.contextPath}/js/admin_order_create.js" defer></script>
<script type="module" src="${pageContext.request.contextPath}/js/admin_order.js"></script>
</html>