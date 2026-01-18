<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="nav-admin">
    <div class="wrap-base-info-admin">
        <div class="wrap-img-user-admin">
            <span class="img-of-admin"><img src="{${sessionScope.user.avatar}" alt=""></span>
            <span class="username-role-of-admin">
                    <span class="username">${sessionScope.user.userName}</span>

                    <div class="role">Quản trị viên</div>
                </span>
        </div>
        <div class="title"><h2><i class="fa-solid fa-crown"></i> Trang quản lí</h2></div>
    </div>
    <hr>
    <div class="wrap-nav-link-admin">
        <ul>
            <li class="item-nav-link-admin ${requestScope.navLink == 1?'selected':''}">
                <i class="fa-solid fa-eye"></i> <a href="${pageContext.request.contextPath}/overview">Tổng quan</a>
            </li>
            <li class="item-nav-link-admin ${requestScope.navLink == 2?'selected':''}">
                <i class="fa-solid fa-user-group"></i> <a href="${pageContext.request.contextPath}/view-user">Quản lí người dùng</a>
            </li>
            <li class="item-nav-link-admin ${requestScope.navLink == 3?'selected':''}">
                <i class="fa-solid fa-box"></i> <a href="${pageContext.request.contextPath}/product-manager">Quản lí sản phẩm</a>
            </li>
            <li class="item-nav-link-admin">
                <i class="fa-solid fa-house"></i> <a href="${pageContext.request.contextPath}/">Quay lại trang chủ</a>
            </li>
            <li class="item-nav-link-admin">
                <i class="fa-solid fa-arrow-right-from-bracket"></i> <a href="${pageContext.request.contextPath}/">Đăng xuất</a>
            </li>
        </ul>
    </div>
</div>