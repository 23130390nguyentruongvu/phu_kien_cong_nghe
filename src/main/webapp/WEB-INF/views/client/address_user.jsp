<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Địa chỉ của tôi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/address_user.css" />

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main id="main" class="main">
        <!--    open account nav-->
    <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
    <!--close account nav-->
        <!--        open main content-->
        <div class="address-user-info">
            <div class="title">
                <h3>Địa chỉ đã lưu</h3>
                <button class="add-address"> &nbsp;+ Thêm địa chỉ mới</button>
                <script>
                    btnAddAddress = document.getElementsByClassName("add-address")[0];
                    btnAddAddress.addEventListener("click", () => {
                        window.location.href = "${pageContext.request.contextPath}/add-address";
                    })
                </script>
            </div>
            <hr>
            <div class="address-user">
                <p class="header-address">Địa chỉ</p>
                <table class="address-content">
                    <tbody>
                    <c:forEach var = "address" items="${listAddress}">
                        <tr>
                        <td class="content">
                            <div class="name-phone">
                                <p class="name">${address.receiverName}</p> &nbsp; | &nbsp; (+84) <p class="phone">${address.phoneNumber}</p>
                            </div>
                            <div class="address-detail">
                               ${address.addressDetail}
                            </div>
                            <div class="district-province">
                                <p class="district">${address.district}</p>
                                ,&nbsp;
                                <p class="province">${address.provinceCity}</p>
                            </div>
                            <c:if test="${address.isSelected == 1}">
                            <div class="status">
                                Mặc định
                            </div>
                            </c:if>
                        </td>
                        <td class="btn">
                            <div class="update-delete">
                                <button class="update">Cập nhật</button>
                                &nbsp;
                                <button class="delete">Xóa</button>
                            </div>
                            <c:if test="${address.isSelected == 0}">
                            <button class="set-status">Thiết lập mặc định</button>
                            </c:if>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>  <script src="../../../js/header.js"></script>
</html>