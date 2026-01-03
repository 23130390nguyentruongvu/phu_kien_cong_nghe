<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="contactShop" scope="request" value="${applicationScope.ContactShop}"/>
<div id="header">
    <div class="header">
        <div class="header-container">
            <!--     open header-above-->
            <div class="header-above">
                <div class="grid-column-above">
                    <div class="information">
                        <ul>
                            <li><strong>${contactShop.getShopName()}</strong></li>
                            <li>Kết nối với chúng tôi:
                                <a href="${contactShop.getUrlFb()}" class="text-hover"><i
                                        class="fa-brands fa-facebook"></i></a>
                                <a href="${contactShop.getUrlIns()}" class="text-hover"><i
                                        class="fa-brands fa-instagram"></i></a>
                            </li>
                        </ul>
                    </div>
                    <div class="empty"></div>
                    <div id="stateUser">
                        <div class="wrap-state-user">
                            <a class="state-user-hover">
                                <i class="fa-solid fa-circle-user"></i>
                                <p id="stateUserLogin">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user}">
                                            ${sessionScope.user.fullName}
                                        </c:when>
                                        <c:otherwise>
                                            Đăng nhập
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <!--        close header-above-->
            <!--        open header-center-->
            <div class="header-center">
                <div class="center logo">
                    <a href="../index.html"><img src="${contactShop.getAvatar()}"
                                                 style="width: 60px;height: 60px;"/></a>
                </div>
                <div class="center form-search">
                    <form action="" method="get">
                        <input type="search" name="search-product" placeholder="Tìm kiếm sản phẩm..."
                               class="search-input">
                        <button class="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i>
                        </button>
                    </form>
                </div>
                <div class="center shopping-cart">
                    <a><i class="fa-solid fa-cart-shopping"><span id="badgeNumItemCart"
                                                                  class="badge">0</span></i></a>
                </div>
            </div>
            <!--        close header-center-->
            <!--        open header-below-->
            <div class="header-below">
                <div class="empty-left"></div>
                <div class="main-menu below-center">
                    <div class="grid-column-below">
                        <ul>
                            <li id="menuHome"
                                class="menu-item ${requestScope.activeHeader == 1 ? 'active' : ''}"><a
                                    href="home.html">TRANG CHỦ</a></li>
                            <li id="menuIntroduce" class="menu-item ${requestScope.activeHeader == 2 ? 'active' : ''}">
                                <a href="introduction.jsp">GIỚI
                                    THIỆU</a></li>
                            <li id="menuProducts" class="menu-item ${requestScope.activeHeader == 3 ? 'active' : ''}">

                                <a href="${pageContext.request.contextPath}/product-category?id=">DANH MỤC SẢN
                                    PHẨM
                                    <i class="fa-solid fa-chevron-down"></i>
                                </a>
                                <div class="show-when-hover products below-center">
                                    <div class="grid-column sub-menu-products">
                                        <ul>
                                            <c:if test="${not empty applicationScope.ParentCategories}">
                                                <c:forEach var="parent" items="${applicationScope.ParentCategories}">
                                                    <li class="sub-menu-item">
                                                        <c:url var="categoryLink" value="/product-category">
                                                            <c:param name="id" value="${parent.id}"/>
                                                        </c:url>
                                                        <a href="${categoryLink}">${parent.nameCategory}</a>
                                                    </li>
                                                </c:forEach>
                                            </c:if>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                            <li id="menuNews" class="menu-item ${requestScope.activeHeader == 4 ? 'active' : ''}" ><a
                                    href="news.jsp">TIN TỨC</a></li>
                            <li id="menuContact" class="menu-item ${requestScope.activeHeader == 5 ? 'active' : ''}" ><a
                                    href="contact.jsp">LIÊN HỆ VỚI CHÚNG TÔI</a></li>
                        </ul>
                    </div>
                </div>
                <div class="empty-right"></div>
            </div>
            <!--        close header-below-->
        </div>
    </div>
</div>