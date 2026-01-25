<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div id="footer">
    <div class="footer">
        <div class="footer-container">
            <div class="footer-grid center">
                <div class="footer-column first info">
                    <ul>
                        <li><i class="fa-solid fa-phone"></i> Gọi: ${contactShop.getPhoneNumber()}</li>
                        <li><i class="fa-solid fa-phone-volume"></i> Hotline: ${contactShop.getHotline()}</li>
                        <li><i class="fa-solid fa-location-dot"></i> Địa chỉ: ${contactShop.getShopAddress()}</li>
                        <li><i class="fa-solid fa-envelope"></i> Email: ${contactShop.getEmail()}</li>
                    </ul>
                </div>
                <div class="footer-column second nav">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/view-payment-method" class="text-hover"><i
                                class="fa-solid fa-money-bill"></i>
                            Phương thức thanh
                            toán</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/view-shipping-method" class="text-hover"><i
                                class="fa-solid fa-truck"></i> Phương
                            thức giao hàng</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/view-warranty-policy" class="text-hover"><i
                                class="fa-solid fa-award"></i> Chính
                            sách bảo hành</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/view-privacy-policy" class="text-hover"><i
                                class="fa-solid fa-lock"></i> Chính sách
                            bảo mật</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/view-term-service" class="text-hover"><i
                                class="fa-solid fa-pen-nib"></i> Điều khoản dịch vụ</a>
                        </li>
                    </ul>
                </div>
                <div class="footer-column third social-media">
                    <ul>
                        <li><a href="${contactShop.getUrlFb()}" class="text-hover"><i class="fa-brands fa-facebook"></i>
                            Facebook</a></li>
                        <li><a href="${contactShop.getUrlIns()}" class="text-hover"><i
                                class="fa-brands fa-instagram"></i> Instagram</a></li>
                    </ul>
                </div>
                <div class="footer-column 4th logo">
                    <img src="${contactShop.getAvatar()}" alt="">
                </div>
            </div>
            <div class="site-below">
                <p>Copyright © 2025 Phụ Kiện Công Nghệ</p>
            </div>
        </div>
    </div>
</div>