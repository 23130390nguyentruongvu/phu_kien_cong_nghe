<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Liên Hệ</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
  </head>
  <body>
  <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main>
      <div class="container">
        <div id="sidebar">
            <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>
        </div>

        <div class="content">
          <h2>Liên Hệ</h2>

          <section class="contact-info">
            <h3>Thông tin liên hệ</h3>
            <p><b>${contactShop.shopName}</b></p>
            <p>Địa chỉ: ${contactShop.shopAddress}</p>
            <p>
              Email:
              <a href="#"
                >${contactShop.email}</a
              >
            </p>
            <p>
              Điện thoại - hotline: <a href="#">${contactShop.phoneNumber}</a> –
              <a href="#">${contactShop.hotline}</a>
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

<%--          <section class="contact-form">--%>
<%--            <h3>Liên hệ với chúng tôi</h3>--%>
<%--            <p>--%>
<%--              Luôn sẵn sàng hỗ trợ và tư vấn cho bạn để có sản phẩm tốt nhất--%>
<%--            </p>--%>

<%--            <form>--%>
<%--              <label>Họ và tên</label>--%>
<%--              <input type="text" placeholder="Họ và tên của bạn" required />--%>

<%--              <label>Điện thoại</label>--%>
<%--              <input type="text" placeholder="Số điện thoại" required />--%>

<%--              <label>Email</label>--%>
<%--              <input type="email" placeholder="Email" required />--%>

<%--              <label>Nội dung</label>--%>
<%--              <textarea--%>
<%--                rows="4"--%>
<%--                placeholder="Nhập nội dung liên hệ..."--%>
<%--                required--%>
<%--              ></textarea>--%>

<%--              <button type="submit">Gửi yêu cầu liên hệ</button>--%>
<%--            </form>--%>
<%--          </section>--%>
        </div>
      </div>
    </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>  <script src="../../../js/header.js"></script>
</html>
