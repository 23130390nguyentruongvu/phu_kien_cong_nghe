<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán</title>
    <link rel="stylesheet" href="../../../css/payment.css">
    <link rel="stylesheet" href="../../../shared/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<main id="main" class="thanh-toan">
    <div class="header-page">
        <h1 class="entry-title">Thanh Toán</h1>
    </div>
    <div id="content" class="site-content">
        <div class="container">
            <div class="row">
                <form name="checkout" method="post" class="checkout">
                    <div id="customer_details">
                        <h3>Địa chỉ nhận hàng</h3>
                        <div class="address">
                            <table class="user-address">
                                <tr>
                                    <td>
                                        <p>
                                            <strong>
                                                <span class="name-user">Nguyễn Trường Vũ</span>
                                                <span class="phone-number">(+84) 385018300</span>
                                            </strong>
                                        </p>
                                        <p>
                                            <span class="address-detail">
                                                Số nhà 418
                                            </span>
                                            <span class="district">
                                                Nhơn Mỹ
                                            </span>
                                            <span class="province">
                                                An Giang
                                            </span>
                                        </p>
                                        <p class="focus">Mặc định</p>
                                        <button type="button" class="change-address">Thay đổi</button>
                                    </td>
                                </tr>
                            </table>
                            <div class="btn-add">
                                <button class="btn-address">Thêm</button>
                            </div>
                            <textarea rows="5" placeholder="Ghi chú sản phẩm" class="note"></textarea>
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
                                <tr class="cart-item">
                                    <td class="product-name">
                                        <div class="product-image">
                                            <div class="ast-product-name">
                                                Củ sạc Xiaomi 90W chính hãng
                                            </div>
                                        </div>&nbsp;
                                        <strong class="product-quantity">&nbsp;x1</strong>
                                    </td>
                                    <td class="product-total">
                                <span class="price-amount">
                                                <bdi>100.000&nbsp;
                                                <span class="current-price">
                                                </span>₫</bdi>
                                            </span>
                                    </td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr class="cart-subtotal">
                                    <th>Tạm tính</th>
                                    <td>
                                            <span class="price-amount">
                                                <bdi>100.000&nbsp;
                                                <span class="current-price">
                                                </span>₫</bdi>
                                            </span>
                                    </td>
                                </tr>
                                <tr class="ship-fee">
                                    <th>Phí ship</th>
                                    <td>
                                            <span class="price-amount">
                                                <bdi>30.000&nbsp;
                                                <span class="current-price">
                                                </span>₫</bdi>
                                            </span>
                                    </td>
                                </tr>
                                <tr class="voucher">
                                    <th>Chiết khấu</th>
                                    <td><p class="price-voucher">
                                        -10.000 </span>₫</bdi>
                                    </p></td>
                                </tr>
                                <tr class="order-total">
                                    <th>Tổng</th>
                                    <td>
                                        <strong>
                                                <span class="price-amount">
                                                <bdi>120.000&nbsp;
                                                <span class="current-price">
                                                </span>₫</bdi>
                                            </span>
                                        </strong>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                            <div id="payment" class="checkout-payment">
                                <ul class="payment-methods">
                                    <li class="method-cod">
                                        <input id="payment-method-cod" type="radio" class="input-radio" name="payment_method" checked="checked">
                                        <label for="payment-method-cod">Thanh toán khi nhận hàng</label>
                                        <div class="cod-sub">
                                            <p>Trả tiền mặt khi giao hàng</p>
                                        </div>
                                    </li>
                                    <li class="method-bank">
                                        <input id="payment-method-bank" type="radio" class="input-radio" name="payment_method">
                                        <label for="payment-method-bank">Chuyển khoản ngân hàng trực tiếp</label>
                                        <div class="bank-sub">
                                            <p>Thực hiện thanh toán vào ngay tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã đơn hàng của bạn trong phần Nội dung thanh toán. Đơn hàng sẽ đươc giao sau khi tiền đã chuyển.</p>
                                        </div>
                                    </li>
                                </ul>
                                <div class="place-order">
                                    <div class="term-and-conditions">
                                        <p>Thông tin cá nhân của bạn sẽ được sử dụng để xử lý đơn hàng, tăng trải nghiệm sử dụng website, và cho các mục đích cụ thể khác đã được mô tả trong
                                            <a href="#">chính sách riêng tư</a> của chúng tôi.</p>
                                    </div>
                                </div>
                                <button type="submit" class="button_order" name="checkout-place-order" id="place-order" value="Đặt hàng">Đặt hàng</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
<script src="../../../js/payment.js"></script>
<script src="../../../js/header.js"></script>
</html>