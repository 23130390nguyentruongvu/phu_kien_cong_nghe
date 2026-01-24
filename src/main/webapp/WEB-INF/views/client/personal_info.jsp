<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<script src="${pageContext.request.contextPath}/js/header.js"></script>
<html>
    <head>
      <meta charset="UTF-8"/>
      <title>Thông tin cá nhân</title>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal_info.css" />
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css" />
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css" />
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
        integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
        crossorigin="anonymous"
        referrerpolicy="no-referrer"
      />
    </head>
    <body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <c:set var="u" value="${sessionScope.user}" />
      <main>
        <div class="wrap-content-all">
            <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>
          <div class="main-content">
            <h2 class="title-account">Thông tin tài khoản</h2>
              <c:if test="${not empty error}">
                  <p style="color: red;">${error}</p>
              </c:if>
              <c:if test="${not empty suc}">
                  <p style="color: green;">${suc}</p>
              </c:if>
            <form class="form-account-info" action="${pageContext.request.contextPath}/change-password" method="post">
              <div class="row-2">
                <div class="form-group">
                  <label for="firstName"
                    >Email</label
                  >
                  <input type="text" id="firstName" value="${sessionScope.user.email}" readonly class="input-readonly"/>
                </div>

                <div class="form-group">
                  <label for="lastName"
                    >User Name</label
                  >
                  <input type="text" id="lastName" value="${sessionScope.user.userName}" readonly class="input-readonly"/>
                </div>
              </div>

<!--              <div class="form-group full-width">-->
<!--                <label for="displayName"-->
<!--                  >Tên hiển thị <span class="required">*</span></label-->
<!--                >-->
<!--                <input-->
<!--                  type="text"-->
<!--                  id="displayName"-->
<!--                  placeholder="MyUserName"-->
<!--                />-->

<!--                <p class="note-display-name">-->
<!--                  Đây sẽ là cách tên của bạn được hiển thị trong phần tài khoản-->
<!--                  và trong các đánh giá.-->
<!--                </p>-->
<!--              </div>-->

<%--<!--              <div class="form-group full-width">-->--%>
<%--<!--                <label for="email"-->--%>
<%--<!--                  >Địa chỉ email <span class="required">*</span></label-->--%>
<%--<!--                >-->--%>
<%--<!--                <input-->--%>
<%--<!--                  type="email"-->--%>
<%--<!--                  id="email"-->--%>
<%--<!--                  value="khachhang113@gmail.com"-->--%>
<%--<!--                />-->--%>
<%--<!--              </div>-->--%>

              <h3 class="change-pass-title">Thay đổi mật khẩu</h3>

              <div class="form-group full-width">
                <label for="currentPass"
                  >Nhập mật khẩu hiện tại</label
                >
                <input
                  type="password"
                  id="currentPass"
                  name = "currentPass" required
                />
              </div>

              <div class="row-2">
                <div class="form-group">
                  <label for="newPass"
                    >Mật khẩu mới</label
                  >
                  <input type="password" id="newPass" name="newPass" required />
                </div>

                <div class="form-group">
                  <label for="confirmPass">Xác nhận mật khẩu mới <span class="required">*</span></label>
                  <input type="password" id="confirmPass" name="confirmPass" required />
                </div>
              </div>

              <button type="submit" class="btn-save-changes">Lưu thay đổi</button>
            </form>
          </div>
        </div>
      </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </body>
    <script src="../../../js/header.js"></script>
  </html>
</html>
