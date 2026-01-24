<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm địa chỉ mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/add_edit_address.css" />
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main id="main" class="main">
        <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
        <!--    close account nav-->
        <!--        open main content-->
        <div class="main-container">
            <div class="add-address-content">
                <div class="container">
                    <div class="title"><h3>Địa chỉ mới</h3></div>
                    <form class="form-address" id="form-address" method="post" action="${pageContext.request.contextPath}/add-address">
                        <div class="form-main">
                            <div class="name">
                                <input type="text" name="receiverName" class="input-name" placeholder="&nbsp;Họ và tên">
                            </div>
                            <div class="phone">
                                <input type="tel" name="phone" class="input-phone" placeholder="&nbsp;Số điện thoại">
                            </div>
                            <div class="province-district" >
                                <div class="select-province">
                                <select class="input-province" name="province">
                                    <option></option>
                                    <option>An Giang</option>
                                    <option>TP.Hồ Chí Minh</option>
                                    <option>Hà Nội</option>
                                </select>
                                </div>
                                <div class="select-district">
                                <select class="input-district" name="district">
                                    <option></option>
                                    <option>Chợ Mới</option>
                                </select>
                                </div>
                            </div>
                            <div class="address-detail">
                                <input class="input-address-details" name="addressDetail"   type="text" placeholder="&nbsp;Địa chỉ chi tiết">
                            </div>
<!--                            <div class="checkbox-status-d">-->
<!--                                <input class="checkbox-status" type="checkbox" id="check">-->
<!--                                <label for="check">Đặt làm mặc định</label>-->
<!--                            </div>-->
                            <div class="btn-done-cancel">
                                <button type="button" class="btn-cancel" onclick="window.location.href= 'address-user'">Trở lại</button>
                                <button type="submit" class="btn-done">Hoàn thành</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
<script src="../../../js/header.js"></script>
</html>