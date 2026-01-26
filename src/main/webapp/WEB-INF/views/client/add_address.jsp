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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/add_edit_address.css?v=1.1" />
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main id="main" class="main">
    <div class="main-container">
        <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
        <!--    close account nav-->
        <!--        open main content-->

            <div class="add-address-content">
                <div class="container">
                    <div class="title"><h3>Địa chỉ mới</h3></div>
                    <form class="form-address" id="form-address" method="post" action="${pageContext.request.contextPath}/add-address">
                        <input type="hidden" name="from" value="${param.from}">
                        <div class="form-main">
                            <div class="name">
                                <input type="text" name="receiverName" class="input-name" placeholder="&nbsp;Họ và tên">
                            </div>
                            <div class="phone">
                                <input type="tel" name="phone" class="input-phone" placeholder="&nbsp;Số điện thoại">
                            </div>
                            <div class="province-district" >
                                <%-- Khai báo danh sách tỉnh thành (Ví dụ một số tỉnh trong 34 tỉnh) --%>
                                <c:set var="provinces" value="${['An Giang', 'Bắc Ninh', 'Hà Nội', 'TP.Hồ Chí Minh', 'Hải Phòng', 'Đà Nẵng', 'Cần Thơ', 'Huế', 'Tuyên Quang', 'Lào Cai', 'Thái Nguyên', 'Phú Thọ', 'Hưng Yên', 'Ninh Bình', 'Quảng Trị', 'Quảng Ngãi', 'Gia Lai', 'Khánh Hòa', 'Lâm Đồng', 'Đắk Lắk', 'Đồng Nai', 'Tây Ninh', 'Vĩnh Long', 'Đồng Tháp', 'Cà Mau', 'Quảng Ninh', 'Thanh Hóa', 'Nghệ An', 'Hà Tĩnh', 'Sơn La', 'Lai Châu', 'Điện Biên', 'Cao Bằng', 'Lạng Sơn']}" />

                                <div class="select-province">
                                    <select class="input-province" name="province">
                                        <option value="">-- Chọn Tỉnh/Thành phố --</option>
                                        <c:forEach var="p" items="${provinces}">
                                            <option value="${p}">${p}</option>
                                        </c:forEach>
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
                                <button type="button" class="btn-cancel" onclick="history.back()">Trở lại</button>
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
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>

<script type="module" src="${pageContext.request.contextPath}/js/sidebar_account_change_avatar.js"></script>
</html>