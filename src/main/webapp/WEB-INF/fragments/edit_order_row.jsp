<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty requestScope.orderSnapshot}">
    <h3>Không tìm thấy đơn hàng</h3>
</c:if>
<c:if test="${not empty requestScope.orderSnapshot}">
    <c:set var="address" value="${requestScope.orderSnapshot.addressOrderSnapshot}" />
    <input type="hidden" name="orderId" value="${requestScope.orderSnapshot.id}">
    <label for="edit-receiver-name">Tên người nhận:</label>
    <input type="text" id="edit-receiver-name" name="receiverName" class="form-input" value="${address.receiverName}" required>
    <label for="edit-phone">Số điện thoại:</label>
    <input type="text" id="edit-phone" name="phoneNumber" class="form-input" value="${address.phoneNumber}" required>
    <label for="edit-address">Địa chỉ:</label>
    <input type="text" id="edit-address" name="addressDetail" class="form-input" value="${address.addressDetail}, ${address.district}, ${address.provinceCity}" required>
    <label>Trạng thái hiện tại: <strong>${requestScope.statusDisplay}</strong></label>
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
            <c:forEach var="item" items="${requestScope.orderSnapshot.orderDetailSnapshots}">
                <tr>
                    <td>${item.productNameSnapshot}</td>
                    <td>${item.variantNameSnapshot}</td>
                    <td>
                        <input type="hidden" name="orderDetailId" value="${item.id}">
                        <input type="hidden" name="variantId" value="${item.productVariantId}">
                        <input type="number" name="quantity" class="edit-quantity form-input" value="${item.quantity}" min="1" style="width:80px;" data-price="${item.variantPriceSnapshot}">
                    </td>
                    <td class="price-cell"><fmt:formatNumber value="${item.variantPriceSnapshot}" pattern="#,###"/>₫</td>
                    <td class="price-cell item-total"><fmt:formatNumber value="${item.priceTotal}" pattern="#,###"/>₫</td>
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
