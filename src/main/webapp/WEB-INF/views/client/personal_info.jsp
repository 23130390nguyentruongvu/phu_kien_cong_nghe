<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<script src="../../../js/header.js"></script>
<html>
    <head>
      <meta charset="UTF-8" />
      <title>Thông tin cá nhân</title>
      <link rel="stylesheet" href="../../../css/personal_info.css" />
      <link rel="stylesheet" href="../../../shared/main.css" />
      <link rel="stylesheet" href="../../../shared/nav_account.css" />
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

      <main>
        <div class="wrap-content-all">
          <div class="nav-account">
            <div class="wrap-base-info-user">
              <span class="img-user-account"
                ><img src="../../../assets/image/logo.webp" loading="lazy"
              /></span>
              <span class="user-name-account"><strong>MyUserName</strong></span>
            </div>
            <div class="wrap-nav-link">
              <ul>
                <li class="nav-link-item link-selected">
                  <i class="fa-solid fa-user"></i>
                    <a href="personal_info.html">Tài khoản</a>
                </li>
                <li class="nav-link-item">
                  <i class="fa-solid fa-clock-rotate-left"></i>
                    <a href="history_order.jsp">Lịch sử đơn hàng</a>
                </li>
                <li class="nav-link-item">
                  <i class="fa-solid fa-location-dot"></i>
                    <a href="address_user.jsp">Địa chỉ</a>
                </li>
                <li class="nav-link-item">
                  <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
                </li>
              </ul>
            </div>
          </div>

          <div class="main-content">
            <h2 class="title-account">Thông tin tài khoản</h2>

            <form class="form-account-info">
              <div class="row-2">
                <div class="form-group">
                  <label for="firstName"
                    >Tên</label
                  >
                  <input type="text" id="firstName" placeholder="Tên của bạn" />
                </div>

                <div class="form-group">
                  <label for="lastName"
                    >Họ</label
                  >
                  <input type="text" id="lastName" placeholder="Họ của bạn" />
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

<!--              <div class="form-group full-width">-->
<!--                <label for="email"-->
<!--                  >Địa chỉ email <span class="required">*</span></label-->
<!--                >-->
<!--                <input-->
<!--                  type="email"-->
<!--                  id="email"-->
<!--                  value="khachhang113@gmail.com"-->
<!--                />-->
<!--              </div>-->

              <h3 class="change-pass-title">Thay đổi mật khẩu</h3>

              <div class="form-group full-width">
                <label for="currentPass"
                  >Mật khẩu hiện tại</label
                >
                <input
                  type="password"
                  id="currentPass"
                  placeholder="••••••••••••••"
                />
              </div>

              <div class="row-2">
                <div class="form-group">
                  <label for="newPass"
                    >Mật khẩu mới</label
                  >
                  <input type="password" id="newPass" />
                </div>

                <div class="form-group">
                  <label for="confirmPass">Xác nhận mật khẩu mới <span class="required">*</span></label>
                  <input type="password" id="confirmPass" />
                </div>
              </div>

              <button class="btn-save-changes">Lưu thay đổi</button>
            </form>
          </div>
        </div>
      </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </body>
    <script src="../../../js/header.js"></script>
  </html>
</html>
