<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chính Sách Bảo </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/warranty_policy.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
</head>
<body>
<!--    open header-->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<!--    close header-->
<main>
    <div class="container">
        <div class="sidebar">
            <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>


        </div>
        <div class="main-contain content-warranty-policy">
            <h2>CHÍNH SÁCH BẢO HÀNH</h2>
            <br>
            <p>
                – Tất cả các sản phẩm bán ra đều được bảo hành theo đúng tiêu chuẩn Chính Sách Bảo Hành.
                <br>
                – Chúng tôi cam kết đổi mới sản phẩm trong thời gian bảo hành theo đúng chính sách bảo hành sản
                phẩm.
                <br>
                – Đối với sản phẩm bị lỗi kỹ thuật trong thời gian bảo hành sẽ được đổi mới hoàn toàn miễn phí.
                <br>
                – Trong thời gian bảo hành mà Shop không còn sản phẩm đổi, quý khách sẽ được đổi sang sản phẩm khác
                tương đương.
                <br>
                – Các trường hợp lỗi cháy nổ, nước vào, lỗi do con người hoặc tác nhân bên ngoài gây lên sẽ không
                được bảo hành.
                <br>
                – Các trường hợp lỗi hỏng khác, tùy theo mức độ hư hỏng có thể được Shop trợ giá một phần khi mua
                hoặc đổi sản phẩm mới.
                <br>
                – Khách hàng có nhu cầu đổi hoặc bảo hành sản phẩm vui lòng liên hệ qua hotline hoặc địa chỉ email của
                Shop để được phản hồi sớm nhất.
            </p>
            <br>
            <h3>ĐỔI TRẢ HOÀN TIỀN</h3>
            <br>
            <p>
                Để đảm bảo quyền lợi cho khách hàng, bên cạnh chính sách bảo hành đổi mới sản phẩm,
                shop có thêm cơ chế đổi trả & hoàn tiền cho sản phẩm.
            </p>
            <ol>
                <li>
                    Điều kiện đổi trả
                    <br>
                    <p>
                        Quý khách phải đảm bảo thực hiện các bước sau trong vòng 48h:
                    </p>

                    <p>- Sản phẩm không đúng như trên web mô tả tại thời điểm đặt, lỗi từ nhà sản xuất.</p>
                    <p>- Số lượng sản phẩm được giao không đủ.</p>
                    <p>- Hàng giao bên ngoài bị: rách, móp méo, bong tróc,...</p>
                    <p>- Khi nhận phải quay video mở hàng, nếu thấy có vấn đề thì không được tự ý seal, tem hàng
                        nếu có
                    </p>
                    <p>- Chỉ chấp nhận trả hàng nếu không đúng mẫu mã, bị hư hỏng trong quá trình vận chuyển</p>

                    <p><strong>***Lưu ý:</strong> Chúng tôi chỉ nhận đổi trả hàng trong trường hợp hàng hóa đó được
                        đặt tại cửa hàng <strong><em>PHỤ KIỆN CÔNG NGHỆ</em></strong>. Vì thế quý khách vui lòng giữ
                        lại hóa đơn khi nhận hàng hoặc các giấy tờ liên quan để đối soát.</p>
                </li>
                <li>
                    Quy định về thời gian thông báo đổi trả
                    <br>
                    <p>- Thời gian đổi trả sẽ khác nhau tùy theo khu vực. Đối với khu vực gần TP.HCM sẽ rơi vào 2 đến 3
                        ngày, các tỉnh thành khác sẽ trong vòng 5 đến 6 ngày</p>
                    <p>- Địa chỉ đổi trả:
                        ${contactShop.shopAddress}. Số điện thoại ${contactShop.phoneNumber}, ${contactShop.shopName}
                        Shop
                    </p>
                </li>

                <strong><em>Cảm ơn Quý Khách đã ủng hộ Shop!</em></strong>

            </ol>
        </div>
    </div>
</main>
<!--    open footer-->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<!--    close footer-->
</div>

</body>
</html>