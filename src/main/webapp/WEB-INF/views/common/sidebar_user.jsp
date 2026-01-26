<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="nav-account">
    <div class="wrap-base-info-user">
        <div class="avatar-upload">
            <label for="avatarInput" class="avatar-label">
        <span class="img-user-account">
            <img id="avatarPreview" src="${sessionScope.user.avatar}" loading="lazy">
        </span>
                <div class="avatar-edit-overlay">
                    <i class="fa-solid fa-camera"></i>
                </div>
            </label>

            <input type="file" id="avatarInput" name="avatar" accept="image/*"
                   style="display: none;"
                   data-id="${sessionScope.user.id}"
                   data-userName="${sessionScope.user.userName}">
        </div>
        <span class="user-name-account"><strong>${empty sessionScope.user ? '' : sessionScope.user.fullName}</strong></span>
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
            <c:if test="${not empty sessionScope.user and sessionScope.user.role == 1}">
                <li class="nav-link-item">
                    <i class="fa-solid fa-user-shield"></i>
                    <a href="${pageContext.request.contextPath}/overview">Trang quản trị</a>
                </li>
            </c:if>
            <li class="nav-link-item">
                <i class="fa-solid fa-right-from-bracket"></i>
                <a href="${pageContext.request.contextPath}/logout" onclick="">Đăng xuất</a>
            </li>
        </ul>
    </div>
</div>