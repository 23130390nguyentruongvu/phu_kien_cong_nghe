<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.orderDetail}">
    <p style="color:red;">Không tìm thấy đơn hàng</p>
</c:if>
<c:if test="${not empty requestScope.orderDetail}">
    <div class="order-detail-info">
        <div class="info-item">
            <div class="info-label">Mã đơn hàng</div>
            <div class="info-value">#${requestScope.orderDetail.orderId}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Người nhận</div>
            <div class="info-value">${requestScope.orderDetail.receiverName}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Số điện thoại</div>
            <div class="info-value">${requestScope.orderDetail.phoneNumber}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Địa chỉ</div>
            <div class="info-value">${requestScope.orderDetail.addressDetail}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Trạng thái</div>
            <div class="info-value"><span class="status-order ${requestScope.orderDetail.statusOrder}">${requestScope.orderDetail.statusDisplay}</span></div>
        </div>
        <div class="info-item">
            <div class="info-label">Phương thức thanh toán</div>
            <div class="info-value">${requestScope.orderDetail.methodPayment}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Ngày đặt</div>
            <div class="info-value">${requestScope.orderDetail.orderDateFormat}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Ngày giao dự kiến</div>
            <div class="info-value">${requestScope.orderDetail.deliveryDateFormat}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Phí ship</div>
            <div class="info-value">${requestScope.orderDetail.shipFeeFormat}</div>
        </div>
        <div class="info-item">
            <div class="info-label">Tổng tiền</div>
            <div class="info-value" style="color:#d63384;font-weight:bold;">${requestScope.orderDetail.totalMustPayFormat}</div>
        </div>
    </div>
    <h3>Danh sách sản phẩm</h3>
    <table class="order-items-table">
        <thead>
            <tr>
                <th>Sản phẩm</th>
                <th>Biến thể</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>Thành tiền</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${requestScope.orderDetail.items}">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.type}</td>
                    <td>${item.quantity}</td>
                    <td class="price-cell">${item.priceVariantFormat}</td>
                    <td class="price-cell">${item.totalPriceFormat}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
