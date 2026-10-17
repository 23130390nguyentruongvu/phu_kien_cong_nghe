<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css?v2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css?v=2">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main id="main" class="thanh-toan">
    <div class="header-page">
        <h1 class="entry-title">Thanh Toán</h1>
    </div>
    <div id="content" class="site-content">
        <div class="container">
            <div class="row">
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger"
                         style="color: red; background: #fee2e2; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
                        <i class="fa-solid fa-circle-exclamation"></i> ${requestScope.error}
                    </div>
                </c:if>
                <form id="formCheckout" name="checkout" method="post" class="checkout">
                    <c:if test="${not empty requestScope.defaultAddress}">
                        <input type="hidden" name="selectedAddressId"
                               value="${requestScope.defaultAddress.id}">
                    </c:if>
                    <div id="customer_details">
                        <h3>Địa chỉ nhận hàng</h3>
                        <div class="address">
                            <c:choose>
                                <c:when test="${not empty requestScope.defaultAddress}">
                                    <table class="user-address">
                                        <tr>
                                            <td>
                                                <p>
                                                    <strong>
                                                        <span class="name-user">${requestScope.defaultAddress.receiverName}</span>
                                                        <span class="phone-number">(+84) ${requestScope.defaultAddress.phoneNumber}</span>
                                                    </strong>
                                                </p>
                                                <p>
                                                    <span class="address-detail">${requestScope.defaultAddress.addressDetail}</span>,
                                                    <span class="district">${requestScope.defaultAddress.district}</span>,
                                                    <span class="province">${requestScope.defaultAddress.provinceCity}</span>
                                                </p>
                                                <p class="focus">Mặc định</p>
                                                <button type="button" class="btn-address"
                                                        onclick="window.location.href='${pageContext.request.contextPath}/address-user'">
                                                    Thêm/Quản lý địa chỉ
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="no-address">
                                        <p>Bạn chưa có địa chỉ nhận hàng. <a
                                                href="${pageContext.request.contextPath}/add-address?from=checkout">Thêm
                                            ngay</a></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <textarea rows="5" name="note" placeholder="Ghi chú đơn hàng..." class="note"></textarea>
                        </div>
                    </div>
                    <div class="products">
                        <h3 id="heading-product">Đơn hàng của bạn</h3>
                        <div id="order-review" class="checkout-order-review">
                            <table class="shop-table">
                                <thead>
                                <tr class="product-and-price">
                                    <th class="product-name">
                                        Sản phẩm
                                    </th>
                                    <th class="product-total">
                                        Tạm tính
                                    </th>
                                </tr>

                                </thead>
                                <tbody>

                                <c:forEach var="item" items="${sessionScope.cart.cartItems}">
                                    <tr class="cart-item">
                                        <td class="product-name">
                                            <div class="product-image">
                                                <div class="ast-product-name">
                                                        ${item.nameProduct}
                                                    <br>
                                                    <small>(Loại: ${item.productVariant.name})</small>
                                                </div>
                                            </div>&nbsp;
                                            <strong class="product-quantity">&nbsp;x${item.quantity}</strong>
                                        </td>
                                        <td class="product-total">
                                     <span class="price-amount">
                                        <fmt:formatNumber value="${item.productVariant.price * item.quantity}"
                                                          pattern="#,###"/>₫
                                </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr class="cart-subtotal">
                                    <th>Tạm tính</th>
                                    <td>
                                            <span class="price-amount">
                                                <fmt:formatNumber value="${subTotal}" pattern="#,###"/>₫
                                            </span>
                                    </td>
                                </tr>
                                <tr class="ship-fee">
                                    <th>Phí ship</th>
                                    <td>
                                     <span class="price-amount">
                                     <fmt:formatNumber value="${shipFee}" pattern="#,###"/>₫
                                     </span>
                                    </td>
                                </tr>
                                <tr class="voucher-discount-line" id="voucher-discount-line" hidden>
                                    <th>Voucher giảm</th>
                                    <td><span class="price-amount" id="voucher-discount-amount">−0₫</span></td>
                                </tr>
                                <tr class="order-total">
                                    <th>Tổng</th>
                                    <td>
                                        <strong>
                                                <span class="price-amount">
                                                <span id="payment-total" data-total="${totalOrder}">
                                                    <fmt:formatNumber value="${totalOrder}" pattern="#,###"/>₫
                                                </span>
                                            </span>
                                        </strong>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                            <section class="checkout-vouchers" data-subtotal="${subTotal}" data-shipping="${shipFee}">
                                <div class="checkout-voucher-heading">
                                    <h3><i class="fa-solid fa-ticket"></i> Voucher áp dụng cho đơn hàng</h3>
                                    <a href="${pageContext.request.contextPath}/voucher-store">Xem kho voucher</a>
                                </div>
                                <label class="checkout-voucher-option">
                                    <input type="radio" name="voucherId" value="" checked>
                                    <span>Không sử dụng voucher</span>
                                </label>
                                <c:choose>
                                    <c:when test="${empty vouchers}">
                                        <p class="checkout-voucher-empty">Chưa có voucher phù hợp với sản phẩm và giá trị đơn hàng.</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="voucher" items="${vouchers}">
                                            <label class="checkout-voucher-option">
                                                <input type="radio" name="voucherId" value="${voucher.id}"
                                                       data-discount="${voucher.discountAmount}">
                                                <span class="checkout-voucher-description">
                                                    <strong><c:out value="${voucher.name}"/> · <c:out value="${voucher.code}"/></strong>
                                                    <small>
                                                        <c:choose>
                                                            <c:when test="${voucher.discount_type == 'PERCENT'}">
                                                                Giảm <fmt:formatNumber value="${voucher.discount_value}" maxFractionDigits="2"/>%
                                                                trên sản phẩm hợp lệ
                                                            </c:when>
                                                            <c:when test="${voucher.discount_type == 'FREESHIP'}">Giảm phí vận chuyển</c:when>
                                                            <c:otherwise>
                                                                Giảm <fmt:formatNumber value="${voucher.discount_value}" pattern="#,###"/>₫
                                                                trên sản phẩm hợp lệ
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:if test="${not empty voucher.max_discount_amount && voucher.discount_type == 'PERCENT'}">
                                                            · Tối đa <fmt:formatNumber value="${voucher.max_discount_amount}" pattern="#,###"/>₫
                                                        </c:if>
                                                        · Giảm đơn này <b><fmt:formatNumber value="${voucher.discountAmount}" pattern="#,###"/>₫</b>
                                                        <c:if test="${voucher.discount_type != 'FREESHIP'}">
                                                            · Áp dụng trên <fmt:formatNumber value="${voucher.eligibleSubtotal}" pattern="#,###"/>₫ sản phẩm
                                                        </c:if>
                                                    </small>
                                                </span>
                                            </label>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </section>
                            <div id="payment" class="checkout-payment">
                                <ul class="payment-methods">
                                    <c:forEach var="method" items="${requestScope.paymentMethods}" varStatus="loop">
                                        <li class="method-item">
                                            <input id="payment-method-${method.id}" type="radio" class="input-radio"
                                                   name="paymentMethodId" value="${method.id}"
                                                ${loop.first ? 'checked="checked"' : ''}>
                                            <label for="payment-method-${method.id}">
                                                <strong>${method.nameMethod}</strong>
                                            </label>
                                            <div class="payment-box" id="box-${method.id}"
                                                 style="${loop.first ? '' : 'display:none;'}">
                                                <p>${method.subtitle}</p>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <div class="place-order">
                                    <div class="term-and-conditions">
                                        <p>Thông tin cá nhân của bạn sẽ được sử dụng để xử lý đơn hàng, tăng trải nghiệm
                                            sử dụng website, và cho các mục đích cụ thể khác đã được mô tả trong
                                            <a href="${pageContext.request.contextPath}/view-privacy-policy">chính sách
                                                bảo mật</a> của chúng tôi.</p>
                                    </div>
                                </div>
                                <button type="submit" class="button_order" id="place-order"
                                        <c:if test="${empty defaultAddress}">
                                            disabled
                                            style="background-color: #cccccc; cursor: not-allowed;"
                                            title="Vui lòng thêm địa chỉ giao hàng"
                                        </c:if>
                                >
                                    Đặt hàng
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>

<c:if test="${not empty requestScope.successMessage}">
    <script>alert("${requestScope.successMessage}");
    window.location.href = "${pageContext.request.contextPath}/";</script>
</c:if>
<script>
    var APP_CONFIG = {
        hasAddress: ${not empty defaultAddress ? 'true' : 'false'},
        addressUrl: "${pageContext.request.contextPath}/address-user"
    };
</script>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script type="module" src="${pageContext.request.contextPath}/js/payment.js"></script>
</html>