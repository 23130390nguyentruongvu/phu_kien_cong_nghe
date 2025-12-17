<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Phương thức giao hàng</title>
    <link rel="stylesheet" href="../../../css/shipping_method.css" />
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
                    <li><a href="news.jsp">Tin Tức</a></li>
                    <li><a href="personal_info.jsp">Tài Khoản Của Tôi</a></li>
                    <li><a href="shipping_method.html">Phương Thức Giao Hàng</a></li>
                    <li><a href="payment_method.jsp">Phương Thức Thanh Toán</a></li>
                    <li><a href="warranty_policy.jsp">Chính Sách Bảo Hành</a></li>
                    <li><a href="privacy_policy.jsp">Chính Sách Bảo Mật</a></li>
                    <li><a href="term_of_service.jsp">Điều Khoản Dịch Vụ</a></li>
                </ul>
            </nav>
          </aside>
        </div>

        <div class="content">
          <h2>PHƯƠNG THỨC GIAO HÀNG</h2>

          <p>
            Phương Thức Giao Hàng của Phụ Kiện Công Nghệ là hỗ trợ giao hàng tận
            nơi trên toàn quốc, áp dụng cho mọi khách hàng đặt mua sản phẩm tại
            website
            <a href="#">phukiencongnghe.com</a>. Đơn hàng được vận chuyển bởi
            các công ty giao nhận hoặc trực tiếp bởi đội ngũ của Phụ Kiện Công
            Nghệ.
          </p>

          <h3>1. Quy cách đóng gói sản phẩm:</h3>
          <ul>
            <li>
              Các sản phẩm luôn được đóng gói cẩn thận, đạt tiêu chuẩn phù hợp
              cho các hình thức vận chuyển nhằm đảm bảo thẩm mỹ và chất lượng
              sản phẩm.
            </li>
          </ul>

          <h3>2. Chính sách Phương thức giao hàng:</h3>
          <ul>
            <li>
              Đơn hàng sẽ được giao tối đa 3 lần (trong trường hợp khách không
              thể nhận trong thời gian đã đặt hẹn). Với những trường hợp khách
              hàng cung cấp sai thông tin, đơn hàng sẽ hết hiệu lực và được hoàn
              Shop.
            </li>
            <li>
              Đơn hàng sẽ linh động thay đổi thời gian giao hàng trong các
              trường hợp thiên tai hoặc các sự kiện đặc biệt khác.
            </li>
            <li>
              Để kiểm tra thông tin hoặc tình trạng đơn hàng, quý khách có thể
              liên hệ hotline hoặc chat trên website.
            </li>
            <li>
              Khi sản phẩm được giao đến, quý khách vui lòng kiểm tra số lượng,
              tên, loại sản phẩm… đúng với đơn đặt hàng trước khi ký xác nhận.
            </li>
            <li>
              Xin quý khách giữ lại biên lai vận chuyển và hóa đơn mua hàng để
              đối chiếu kiểm tra. Khi có bất cứ khiếu nại nào xảy ra, chúng tôi
              sẽ căn cứ vào biên lai vận chuyển và hoá đơn mua hàng để giải
              quyết khiếu nại cho quý khách hàng.
            </li>
          </ul>
          <h3>3. Thời gian giao hàng:</h3>
          <table border="1" cellpadding="10" cellspacing="0">
            <thead>
              <tr>
                <th>Khu vực giao hàng</th>
                <th>Thời gian vận chuyển</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Nội thành Hồ Chí Minh</td>
                <td>Giao ngay trong 30 – 60 phút</td>
              </tr>
              <tr>
                <td>Toàn TP. Hồ Chí Minh</td>
                <td>1 đến 1,5 ngày</td>
              </tr>
              <tr>
                <td>Các thành phố khác (Hải Phòng, Đà Nẵng, Hà Nội, …)</td>
                <td>2 đến 3 ngày</td>
              </tr>
              <tr>
                <td>Tỉnh, thành khác</td>
                <td>3 đến 5 ngày</td>
              </tr>
            </tbody>
          </table>
          <h4>Ghi chú về thời gian giao hàng</h4>
          <p>
            <strong>Lưu ý:</strong> Thời gian giao hàng không tính ngày Chủ
            Nhật, Lễ, Tết… (tùy thuộc chính sách Nhà vận chuyển).
          </p>
          <p>
            Thời gian xử lý đơn hàng được tính từ khi khách hàng đặt thành công
            trên website.
          </p>

          <h3>4. Chi phí vận chuyển</h3>
          <ul>
            <li>Với đơn hàng dưới 500.000đ:</li>
          </ul>
          <table border="1" cellpadding="10" cellspacing="0">
            <thead>
              <tr>
                <th>Khu vực giao hàng</th>
                <th>Phí vận chuyển</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Nội thành Hồ Chí Minh (giao ngay trong 30 – 60p)</td>
                <td>30.000đ/đơn hàng</td>
              </tr>
              <tr>
                <td>Toàn TP. Hồ Chí Minh (giao hôm sau)</td>
                <td>20.000đ/đơn hàng</td>
              </tr>
              <tr>
                <td>Các thành phố khác (Hải Phòng, Đà Nẵng, Hà Nội, …)</td>
                <td>35.000đ/đơn hàng</td>
              </tr>
              <tr>
                <td>Tỉnh, thành khác</td>
                <td>40.000đ/đơn hàng</td>
              </tr>
            </tbody>
          </table>
          <h3>Chi phí vận chuyển cho đơn hàng từ 500.000đ trở lên</h3>

          <ul>
            <li>
              Sẽ được áp dụng chương trình miễn phí hoặc hỗ trợ một phần cước
              vận chuyển tùy điều kiện thực tế (như vị trí khách hàng, trọng
              lượng đơn hàng, phương thức thanh toán).
            </li>
          </ul>

          <p>
            Biểu phí Phương Thức Giao Hàng được áp dụng kể từ ngày
            <strong>01/07/2025</strong>.
          </p>
        </div>
      </div>
    </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>  <script src="../../../js/header.js"></script>
</html>
