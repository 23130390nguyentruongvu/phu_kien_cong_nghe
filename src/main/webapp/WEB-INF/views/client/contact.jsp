<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Liên Hệ</title>
    <link rel="stylesheet" href="../../../css/contact.css" />
    <link rel="stylesheet" href="../../../shared/main.css" />
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
      <div class="container">
        <div id="sidebar">
          <div class="sidebar-main">
            <aside id="block-31" class="widget widget_block">
              <h3 class="wp-block-heading">DANH MỤC TRANG</h3>
            </aside>
            <aside id="nav_menu-9" class="widget widget_nav_menu">
              <nav class="menu-danh-muc-trang-container" aria-label="Menu">
                  <ul id="menu-danh-muc-trang" class="menu">
                      <li><a href="home.jsp">Trang Chủ</a></li>
                      <li><a href="introduction.jsp">Giới Thiệu</a></li>
                      <li><a href="contact.html">Liên Hệ</a></li>
                      <li><a href="news.jsp">Tin Tức</a></li>
                      <li><a href="personal_info.jsp">Tài Khoản Của Tôi</a></li>
                      <li><a href="shipping_method.jsp">Phương Thức Giao Hàng</a></li>
                      <li><a href="payment_method.jsp">Phương Thức Thanh Toán</a></li>
                      <li><a href="warranty_policy.jsp">Chính Sách Bảo Hành</a></li>
                      <li><a href="privacy_policy.jsp">Chính Sách Bảo Mật</a></li>
                      <li><a href="term_of_service.jsp">Điều Khoản Dịch Vụ</a></li>
                  </ul>
              </nav>
            </aside>
          </div>
        </div>

        <div class="content">
          <h2>Liên Hệ</h2>

          <section class="contact-info">
            <h3>Thông tin liên hệ</h3>
            <p><b>Phụ Kiện Công Nghệ</b></p>
            <p>Địa chỉ: VQCR+GP6, khu phố 6, Thủ Đức, Thành phố Hồ Chí Minh</p>
            <p>
              Email:
              <a href="mailto:phukiencongnghenonglam@gmail.com"
                >phukiencongnghenonglam@gmail.com</a
              >
            </p>
            <p>
              Điện thoại: <a href="tel:02460278331">0123 456 789</a> –
              <a href="tel:0986828331">0963 369 336</a>
            </p>
          </section>

          <section class="map-section">
            <h3>Bản đồ chỉ đường</h3>
            <div class="map-container">
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.2143797342956!2d106.78919067369718!3d10.871292757439459!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175278521e2b8f5%3A0xcc08026a2d7c6c01!2zVlFDUitHUDYsIGtodSBwaOG7kSA2LCBMaW5oIFRydW5nLCBUaOG7pyDEkOG7qWMsIFRow6BuaCBwaOG7kSBI4buTIENow60gTWluaCwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1761395325706!5m2!1svi!2s"
                width="100%"
                height="450"
                style="border: 0"
                allowfullscreen=""
                loading="lazy"
                referrerpolicy="no-referrer-when-downgrade"
              >
              </iframe>
            </div>
          </section>

          <section class="contact-form">
            <h3>Liên hệ với chúng tôi</h3>
            <p>
              Luôn sẵn sàng hỗ trợ và tư vấn cho bạn để có sản phẩm tốt nhất
            </p>

            <form>
              <label>Họ và tên</label>
              <input type="text" placeholder="Họ và tên của bạn" required />

              <label>Điện thoại</label>
              <input type="text" placeholder="Số điện thoại" required />

              <label>Email</label>
              <input type="email" placeholder="Email" required />

              <label>Nội dung</label>
              <textarea
                rows="4"
                placeholder="Nhập nội dung liên hệ..."
                required
              ></textarea>

              <button type="submit">Gửi yêu cầu liên hệ</button>
            </form>
          </section>
        </div>
      </div>
    </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>  <script src="../../../js/header.js"></script>
</html>
