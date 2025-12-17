<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="../../../shared/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="../../../css/shopping_cart.css">

</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main>
        <div class="container-shopping-cart">
            <div class="content-center">
                <div class="title-shopping-cart"><h2>Giỏ hàng</h2></div>
                <div class="info-flex">
                    <div class="info-items">
                        <form class="items-form">
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
                                <tr class="row-item">
                                    <td class="icon-remove"><i class="fa-regular fa-circle-xmark"></i></td>
                                    <td class="image-product">
                                        <a href="">
                                            <img src="assets/image/fake_products/item_pkdt_4.webp">
                                        </a>
                                    </td>
                                    <td class="product-name">
                                        <div class="wrap-product-name">
                                            <a href="">Kính Cường Lực Panda</a>
                                        </div>
                                    </td>
                                    <td class="product-price">
                                        <div class="price-amount">100.000<span class="underline-dong">đ</span></div>
                                    </td>
                                    <td class="product-quantity">
                                        <div class="quantity-wrap">
                                            <div class="quantity-input">
                                                <button id="quantityDecItemCart" class="btn-dec-item-quantity btn"
                                                        type="button">
                                                    -
                                                </button>
                                                <span class="num-quantity-item">100</span>
                                                <button id="quantityIncItemCart" class="btn-inc-item-quantity btn"
                                                        type="button">
                                                    +
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="product-subtotal">
                                        <div class="subtotal-price-amount">100.000đ</div>
                                    </td>
                                </tr>


                                </tbody>
                                <!--close body table-->
                            </table>
                        </form>
                    </div>
                    <!--open bill-->
                    <div class="bill-products">
                        <h2>Tổng cộng phải thanh toán</h2>
                        <table>
                            <tbody>
                            <tr class="bill-subtotal">
                                <th><h4>Tạm tính</h4></th>
                                <td><span class="bill-subtoal-amount">100.000đ</span></td>
                            </tr>
                            <tr class="bill-voucher">
                                <th><h4>Discount</h4></th>
                                <td><span class="bill-voucher-discount">- 0đ</span>
                                </td>
                            </tr>
                            <tr class="bill-total">
                                <th><h4>Tổng cộng (Discount)</h4></th>
                                <td><span class="bill-toal-amount">100.000đ</span></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="process-checkout">
                            <div class="bill-voucher">
                                <span>
                                    <form>
                                        <input id="inputVoucherAtCart" class="input" placeholder="Nhập mã voucher">
                                        <button id="btnApplyVoucherAtCart" class="btn-submit-voucher"
                                                type="submit">Áp dụng</button>
                                    </form>
                                </span>
                            </div>
                            <div class="go-to-checkout">
                                <button id="btnGotoCheckout" class="submit-checkout">
<!--                                    mai mốt bỏ a đi-->
                                    <a href="payment.jsp" style="color: white">TIẾN HÀNH THANH TOÁN</a>

                                </button>
                            </div>
                        </div>
                    </div>
                    <!--close bill-->
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>  <script src="../../../js/header.js"></script>
</html>