<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shopping_cart.css">

</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main>
        <c:if test="${empty sessionScope.cart.cartItems}">
            <div class="container-shopping-cart">
                <div class="content-center">
                    <h3 class="title-shopping-cart">Bạn chưa có sản phẩm trong giỏ hàng</h3>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.cart.cartItems}">
            <div class="container-shopping-cart">
                <div class="content-center">
                    <div class="title-shopping-cart"><h2>Giỏ hàng</h2></div>
                    <div class="info-flex">
                        <div class="info-items">
                            <div class="items-form">
                                <table class="items-table">
                                    <!--open header table-->
                                    <thead>
                                    <tr>
                                        <th class="th-remove-item">Xóa</th>
                                        <th class="th-image-item"></th>
                                        <th class="th-product-name">Sản phẩm</th>
                                        <th class="th-price">Giá</th>
                                        <th class="th-quantity">Số lượng</th>
                                        <th class="th-subtotal">Tạm tính</th>
                                    </tr>
                                    </thead>
                                    <!--close header table-->
                                    <!--open body table-->
                                    <tbody>
                                    <c:forEach var="cartItem" items="${sessionScope.cart.cartItems}">
                                        <tr class="row-item">
                                            <td class="icon-remove"
                                                onclick="handleDelete(
                                                    ${cartItem.productVariantId},
                                                        '${cartItem.nameDetail}',
                                                        '${pageContext.request.contextPath}/delete-cart-item'
                                                        )">
                                                <i class="fa-regular fa-circle-xmark"></i></td>
                                            <td class="image-product">
                                                <a href="">
                                                    <img src="${cartItem.productVariant.urlImage}">
                                                </a>
                                            </td>
                                            <td class="product-name">
                                                <div class="wrap-product-name">
                                                    <a href="">${cartItem.nameDetail}</a>
                                                </div>
                                            </td>
                                            <td class="product-price">
                                                <div class="price-amount">${cartItem.productVariant.priceByFormat}</div>
                                            </td>
                                            <td class="product-quantity">
                                                <div class="quantity-wrap">
                                                    <div class="quantity-input">
                                                        <a href="javascript:void(0)" id="quantityDecItemCart"
                                                           onclick="updateCartItem(${cartItem.productVariantId}, -1, '${pageContext.request.contextPath}/update-cart-item')"
                                                           class="btn-dec-item-quantity btn">
                                                            -
                                                        </a>
                                                        <span class="num-quantity-item">
<%--                                                                ${cartItem.quantity}--%>
                                                        <form method="post"
                                                              action="${pageContext.request.contextPath}/update-cart-item">
                                                            <input type="number" name="quantity"
                                                                   value="${cartItem.quantity}"
                                                                   style="width: 40px; height: 40px">
                                                            <input type="hidden" name="isOne" value="false">
                                                            <input type="hidden" name="id"
                                                                   value="${cartItem.productVariantId}">
                                                        </form>
                                                        </span>

                                                        <a href="javascript:void(0)" id="quantityIncItemCart"
                                                           onclick="updateCartItem(${cartItem.productVariantId}, 1, '${pageContext.request.contextPath}/update-cart-item')"
                                                           class="btn-inc-item-quantity btn">
                                                            +
                                                        </a>

                                                    </div>
                                                </div>
                                            </td>
                                            <td class="product-subtotal">
                                                <div class="subtotal-price-amount">${cartItem.priceByFormat}</div>
                                            </td>
                                        </tr>
                                    </c:forEach>


                                    </tbody>
                                    <!--close body table-->
                                </table>
                            </div>
                        </div>
                        <!--open bill-->
                        <div class="bill-products">
                            <h2>Tổng cộng phải thanh toán</h2>
                            <table>
                                <tbody>
                                <tr class="bill-total">
                                    <th><h4>Tổng cộng (Chưa bao gồm ship)</h4></th>
                                    <td><span class="bill-toal-amount">${sessionScope.cart.priceByFormat}</span></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="process-checkout">
                                <div class="go-to-checkout">
                                    <button id="btnGotoCheckout" class="submit-checkout"
                                            data-url="${pageContext.request.contextPath}/view-payment">
                                        TIẾN HÀNH THANH TOÁN
                                    </button>
                                </div>
                            </div>
                        </div>
                        <!--close bill-->
                    </div>
                </div>
            </div>
        </c:if>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/shopping_cart.js?v=1.1"></script>
</html>