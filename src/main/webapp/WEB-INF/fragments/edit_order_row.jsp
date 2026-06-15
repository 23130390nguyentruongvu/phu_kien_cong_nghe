<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.orderDetail}">
    <h3>Không tìm thấy đơn hàng</h3>
</c:if>
<c:if test="${not empty requestScope.orderDetail}">
    <input type="hidden" name="orderId" value="${requestScope.orderDetail.orderId}">
    <input type="hidden" name="addressOrderId" id="address-order-id" value="">
    <label for="edit-receiver-name">Tên người nhận:</label>
    <input type="text" id="edit-receiver-name" name="receiverName" class="form-input" value="${requestScope.orderDetail.receiverName}" required>
    <label for="edit-phone">Số điện thoại:</label>
    <input type="text" id="edit-phone" name="phoneNumber" class="form-input" value="${requestScope.orderDetail.phoneNumber}" required>
    <label for="edit-address">Địa chỉ:</label>
    <input type="text" id="edit-address" name="addressDetail" class="form-input" value="${requestScope.orderDetail.addressDetail}" required>
    <label>Trạng thái hiện tại: <strong>${requestScope.orderDetail.statusDisplay}</strong></label>
    <br>
    <h3>Chi tiết sản phẩm</h3>
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
                    <td>
                        <input type="hidden" name="orderDetailId" value="${item.orderDetailId}">
                        <input type="hidden" name="variantId" value="${item.variantId}">
                        <input type="number" name="quantity" class="edit-quantity form-input" value="${item.quantity}" min="1" style="width:80px;" data-price="${item.priceVariant}">
                    </td>
                    <td class="price-cell">${item.priceVariantFormat}</td>
                    <td class="price-cell item-total">${item.totalPriceFormat}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <script>
        document.querySelectorAll('.edit-quantity').forEach(input => {
            input.addEventListener('change', function() {
                const qty = parseInt(this.value) || 0;
                const price = parseFloat(this.dataset.price);
                const totalCell = this.closest('tr').querySelector('.item-total');
                const total = qty * price;
                totalCell.textContent = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(total);
            });
        });
    </script>
</c:if>
