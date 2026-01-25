<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Phương thức thanh toán</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment_method.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
</head>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main id="main" class="ast-container">
    <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>
    <div class="content-area">
        <main id="main-area" class="site-main">
            <div class="entry-content">
                <h3>PHƯƠNG THỨC THANH TOÁN</h3>
                <p>
                    Cảm ơn quý khách đã lựa chọn mua hàng tại shop <a href="${pageContext.request.contextPath}/">phukiencongnghe</a>
                    của chúng tôi, trên website đang có ${requestScope.methods.size()} phương thức thanh toán giúp quý
                    khách dễ dàng lựa chọn khi mua hàng.
                </p>
                <c:if test="${empty requestScope.methods}">
                    <h3>Chúng tôi chưa có phương thức thanh toán</h3>
                </c:if>
                <c:if test="${not empty requestScope.methods}">
                    <c:forEach var="method" items="${requestScope.methods}">
                        <ol>
                            <li>
                                <h4>${method.nameMethod}</h4>
                            </li>
                        </ol>
                        <p>${method.subtitle}</p>
                        <c:if test="${not empty requestScope.bankInfos}">
                            <table>
                                <tbody>
                                <c:forEach var="info" items="${requestScope.bankInfos}">
                                    <c:if test="${info.paymentMethodId == method.id}">
                                        <tr>
                                            <td>${info.bankName}
                                                <br>
                                                Chủ tài khoản: &nbsp; ${info.bankOwnerName}
                                                <br>
                                                Số tài khoản: ${info.bankAccountNumber}
                                            </td>
                                        </tr>
                                    </c:if>

                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </c:forEach>
                </c:if>
                <p>
                    <strong><em>Cảm ơn quý khách đã tin dùng sản phẩm ủng hộ Shop!</em></strong>
                </p>
            </div>
        </main>
    </div>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>