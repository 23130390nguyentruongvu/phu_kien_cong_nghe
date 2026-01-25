<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_detail.css">
</head>
<body>
<div class="overlay"></div>
<c:if test="${empty requestScope.orderDetail}">
    <div class="order-detail-container">
        <h2>Đơn hàng không hợp lệ hoặc không tồn tại</h2>
    </div>
</c:if>
<c:if test="${not empty requestScope.orderDetail}">
    <div class="order-detail-container">
        <div class="order-detail-header">
            <h2>Chi tiết đơn hàng</h2>
        </div>

        <div class="order-detail-main">
            <div class="order-left">
                <div class="order-info">
                    <div class="order-code-date">
                        <p><strong>Mã đơn hàng:</strong>${requestScope.orderDetail.orderId}</p>
                        <p><strong>Ngày tạo:</strong> ${requestScope.orderDetail.orderDateFormat}</p>
                        <p><strong>Ngày giao:</strong> ${requestScope.orderDetail.deliveryDateFormat}</p>
                    </div>
                    <div class="order-status">
                        <span>${requestScope.orderDetail.statusOrder}</span>
                    </div>
                </div>

                <div class="customer-info">
                    <h3>Thông tin khách hàng</h3>
                    <p><strong>Tên người nhận:</strong> ${requestScope.orderDetail.receiverName}</p>
                    <p><strong>Số điện thoại:</strong> ${requestScope.orderDetail.phoneNumber}</p>
                    <p>
                        <strong>Địa chỉ:</strong> ${requestScope.orderDetail.addressDetail}
                    </p>

                </div>

                <div class="order-products">
                    <table>
                        <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th>Thành tiền</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty requestScope.orderDetail.items}">
                            <c:forEach var="item" items="${requestScope.orderDetail.items}">
                                <tr>
                                    <td>
                                            ${item.name}
                                        <div class="product-code">${item.type}</div>
                                    </td>
                                    <td>${item.quantity}</td>
                                    <td>${item.priceVariantFormat}</td>
                                    <td>${item.totalPriceFormat}</td>
                                </tr>
                            </c:forEach>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>

            <div class="order-right">
                <div class="payment-method">
                    <h3>Phương thức thanh toán</h3>
                    <p>${requestScope.orderDetail.methodPayment}</p>
                </div>

                <div class="order-summary">
                    <h3>Tóm tắt đơn hàng</h3>
                    <p>Giá sản phẩm tạm tính: ${requestScope.orderDetail.totalOrderFormat}</p>
                    <p>Phí vận chuyển: ${requestScope.orderDetail.shipFeeFormat}</p>
                    <hr/>
                    <p class="total"><strong>Tổng cộng: ${requestScope.orderDetail.totalMustPayFormat}</strong></p>
                </div>

                <div class="order-buttons">
                    <button class="cancel-btn" data-id="${requestScope.orderDetail.orderId}">Hủy đơn</button>
                    <button class="close-btn">Đóng</button>
                </div>
            </div>
        </div>
    </div>
</c:if>
</body>
<script>
    //close
    document.querySelector('.close-btn').addEventListener('click', () => {
        location.href = '${pageContext.request.contextPath}/order-history'
    })

    //cancel order
    const cancelOrder = document.querySelector('.cancel-btn');
    const isPending = ${requestScope.orderDetail.isStatusPending};

    // Set thuộc tính disabled (boolean)
    cancelOrder.disabled = !isPending;
    cancelOrder.innerText = isPending ? 'Hủy đơn' : 'Không thể hủy đơn';
    //click hủy đơn
    cancelOrder.addEventListener('click', async () => {
        if (!confirm('Bạn có chắc muốn hủy đơn hàng này chứ?')) return;

        try {
            const response = await fetch('${pageContext.request.contextPath}/cancel-order', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams({'orderId': cancelOrder.dataset.id})
            });

            const responseJson = await response.json()
            alert(responseJson.message)
            window.location.reload()
        } catch (e) {
            alert(e)
        }
    });
</script>
</html>
