<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tin tức</title>
    <link rel="stylesheet" href="../../../css/news.css" />
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
        <div class="sidebar-main">
          <aside id="block-31" class="widget widget_block">
            <h3 class="wp-block-heading">DANH MỤC TRANG</h3>
          </aside>
          <aside id="nav_menu-9" class="widget widget_nav_menu">
            <nav class="menu-danh-muc-trang-container" aria-label="Menu">
                <ul id="menu-danh-muc-trang" class="menu">
                    <li><a href="home.jsp">Trang Chủ</a></li>
                    <li><a href="introduction.jsp">Giới Thiệu</a></li>
                    <li><a href="contact.jsp">Liên Hệ</a></li>
                    <li><a href="news.html">Tin Tức</a></li>
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
        <div class="content">
          <section class="news-section">
            <h1>Tin Tức</h1>
            <div class="news-grid">
              <article class="news-card">
                <h2>
                  Nguyên lý hoạt động Kính VR và Top 10 Kính Thực tế ảo đáng mua
                  nhất
                </h2>
                <p class="meta">Bởi admin | 01/07/2025</p>
                <p>
                  Nguyên lý hoạt động Kính VR và Top 10 Kính Thực tế ảo đáng mua
                  nhất Kính VR là một […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>

              <article class="news-card">
                <h2>
                  Nên sử dụng cổng Quang hay HDMI ARC để kết nối dàn âm thanh
                </h2>
                <p class="meta">Bởi admin | 01/07/2025</p>
                <p>
                  Nên sử dụng cổng Quang hay HDMI ARC để kết nối dàn âm thanh
                  Với các dòng Smart Tivi hiện đại […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>

              <article class="news-card">
                <h2>Cáp OTG hỗ trợ Điện thoại kết nối thiết bị ngoại vi</h2>
                <p class="meta">Bởi admin | 01/07/2025</p>
                <p>
                  Cáp OTG hỗ trợ Điện thoại kết nối thiết bị ngoại vi trong thời
                  kỳ Công nghệ phát triển, cáp […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>

              <article class="news-card">
                <h2>Sự khác nhau giữa PCI EXPRESS X1, X4, X8, X16</h2>
                <p class="meta">Bởi admin | 01/07/2025</p>
                <p>
                  Sự khác nhau giữa PCI EXPRESS X1, X4, X8, X16 – PCI-E để kết
                  nối các thiết bị […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>

              <article class="news-card">
                <h2>Công nghệ thực tế ảo VR là gì?</h2>
                <p class="meta">Bởi admin | 01/07/2025</p>
                <p>
                  Công nghệ thực tế ảo VR là gì? Công nghệ thực tế ảo VR thực tế
                  ảo – Virtual reality, […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>

              <article class="news-card">
                <h2>Thiết bị mạng cáp quang là gì</h2>
                <p class="meta">Bởi admin | 01/06/2025</p>
                <p>
                  Thiết bị mạng cáp quang là gì – Phụ Kiện Công Nghệ sẽ giới
                  thiệu chi tiết thêm […]
                </p>
                <a href="#" class="read-more">Đọc bài viết →</a>
              </article>
            </div>
          </section>
        </div>
      </div>
    </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
  <script src="../../../js/header.js"></script>
<script src="../../../js/news.js"></script>
</html>
