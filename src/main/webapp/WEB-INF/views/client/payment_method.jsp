<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Phương thức thanh toán</title>
    <link rel="stylesheet" href="../../../shared/sidebar.css">
    <link rel="stylesheet" href="../../../shared/main.css">
    <link rel="stylesheet" href="../../../css/payment_method.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main id="main" class="ast-container">
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
                        <li><a href="shipping_method.jsp">Phương Thức Giao Hàng</a></li>
                        <li><a href="payment_method.html">Phương Thức Thanh Toán</a></li>
                        <li><a href="warranty_policy.jsp">Chính Sách Bảo Hành</a></li>
                        <li><a href="privacy_policy.jsp">Chính Sách Bảo Mật</a></li>
                        <li><a href="term_of_service.jsp">Điều Khoản Dịch Vụ</a></li>
                    </ul>
                </nav>
            </aside>
        </div>
    <div class="content-area">
        <main id="main-area" class="site-main">
            <div class="entry-content">
                <h3>PHƯƠNG THỨC THANH TOÁN</h3>
                <p>
                    Cảm ơn quý khách đã lựa chọn mua hàng tại shop <a href="#">phukiencongnghe</a>
                    của chúng tôi, trên website đang có 2 phương thức thanh toán giúp quý khách dễ dàng lựa chọn khi mua hàng.
                </p>
                <ol>
                    <li>
                        <h4>Thanh toán khi nhận hàng (COD)</h4>
                    </li>
                </ol>
                <p>- Quý khách nhận hàng và thanh toán cho nhân viên giao hàng.</p>
                <p>- Giá trị đơn sẽ đúng với giá hiển thị trên website.</p>
                <ol start="2">
                    <li>
                        <h4>Chuyển khoản ngân hàng&nbsp;trực tiếp</h4>
                    </li>
                </ol>
                <p>- Khi đặt hàng, chọn "Chuyển khoản ngân hàng trực tiếp" sẽ có hướng dẫn tiếp theo.</p>
                <p>- Thông tin chuyển khoản: </p>
                <table>
                    <tbody>
                    <tr>
                        <td><img src="#" alt="bidv"></td>
                        <td>BIDV - NHTMCP Ngoại Thương Việt Nam - Chi nhánh Châu Thành An Giang
                        <br>
                        Chủ tài khoản: &nbsp; Nguyễn Trọng Tín
                            <br>
                        Số tài khoản: 7011078823</td>
                    </tr>
                    <tr>
                        <td><img src="#" alt="MB"></td>
                           <td>
                        MBbank - NHTMCP Quân Đội - Chi nhánh Đồng Nai
                        <br>
                        Chủ tài khoản: &nbsp;Nguyễn Trường Vũ
                        <br>
                        Số tài khoản: 0793189034</td>
                    </tr>
                    </tbody>
                </table>
                <p>- &nbsp;Khi chuyển khoản thành công, hệ thống sẽ gửi tin nhắn
                thông báo đến khách hàng, hoặc shop sẽ thông báo qua cuộc gọi (kênh chat).</p>
                <p>- Để kiểm tra tình trạng chuyển khoản của đơn hàng quý khách có thể chủ động
                liên hệ Hotline: 0986 828 331.</p>
                <p>
                    <strong><em>Cảm ơn quý khách đã tin dùng sản phẩm ủng hộ Shop!</em></strong>
                </p>
            </div>
        </main>
    </div>
    </main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
<script src="../../../js/header.js"></script>
</html>