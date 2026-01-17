<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="nav-account">
    <div class="wrap-base-info-user">
        <span class="img-user-account"><img src="${empty sessionScope.user?'':sessionScope.user.avatar}" loading="lazy"></span>
        <span class="user-name-account"><strong>${empty sessionScope.user?'':sessionScope.user.fullName}</strong></span>
    </div>
    <div class="wrap-nav-link">
        <ul>
            <li class="nav-link-item ${requestScope.linkNav == 1?'link-selected':''}">
                <i class="fa-solid fa-user"></i>
                <a href="${pageContext.request.contextPath}/personal_info">Tài khoản</a>
            </li>
            <li class="nav-link-item ${requestScope.linkNav == 2?'link-selected':''}">
                <i class="fa-solid fa-clock-rotate-left"></i>
                <a href="${pageContext.request.contextPath}/order-history">Lịch sử đơn hàng</a>
            </li>
            <li class="nav-link-item ${requestScope.linkNav == 3?'link-selected':''}">
                <i class="fa-solid fa-location-dot"></i>
                <a href="${pageContext.request.contextPath}/address-user">Địa chỉ</a>
            </li>
            <li class="nav-link-item">
                <i class="fa-solid fa-right-from-bracket"></i>
                <a href="${pageContext.request.contextPath}/logout" onclick="return confirm("Bạn có thực sự muốn đăng xuất")">Đăng xuất</a>
            </li>
        </ul>
    </div>
</div>