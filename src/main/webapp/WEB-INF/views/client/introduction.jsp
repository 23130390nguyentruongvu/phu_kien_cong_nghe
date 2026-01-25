<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Giới Thiệu</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/introdution.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<main>
    <div class="container">
        <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>

        <div class="content">
            <h2>Giới Thiệu</h2>
            <p>
                – ${contactShop.shopName} chuyên cung cấp Phụ kiện Máy tính, Phụ kiện
                Điện thoại, Thiết bị Mạng, Phụ kiện Xe đạp, Pk Ô tô. Phục vụ nhu cầu
                của khách hàng trên cả nước.
            </p>

            <p>
                – ${contactShop.shopName} là một cửa hàng trực tuyến, được thành lập
                từ năm 2015 bởi một đội ngũ đam mê công nghệ, cửa hàng tọa lạc tại ${contactShop.shopAddress}
            </p>

            <p>
                – Người tiêu dùng chọn ${contactShop.shopName} nhờ vào chất lượng sản
                phẩm tốt, giá cả hợp lý và dịch vụ khách hàng tận tâm. Với nhiều năm
                kinh nghiệm trong lĩnh vực, cửa hàng cam kết mang đến những sản phẩm
                công nghệ mới nhất đến tay người tiêu dùng, chúng tôi chỉ cung cấp
                và giới thiệu những sản phẩm tốt nhất.
            </p>

            <p>
                – Nhiều người cũng biết đến ${contactShop.shopName} bởi sự giới thiệu của
                bạn bè, người thân, họ là những khách hàng quen thuộc của chúng tôi.
            </p>

            <p>– Shop bán hàng với phương châm:</p>
            <p class="slogan">
                <strong>Sự hài lòng của bạn là thành công của chúng tôi</strong>
            </p>

            <p>
                Chuyên cung cấp: Các phụ kiện tối tân, giá hợp lí
            </p>

            <p>
                – Chúng ta có thể hiểu Phụ kiện công nghệ là những sản phẩm hỗ trợ,
                tăng thêm tính năng và tiện lợi cho các thiết bị công nghệ khác giúp
                chúng hoạt động tốt hơn hoặc nâng cao tính năng của chúng như Điện
                thoại, Máy tính PC, Laptop, Máy ảnh, TV…
            </p>

            <h3>Cửa hàng ${contactShop.shopName}:</h3>
            <p>– Địa chỉ: ${contactShop.shopAddress}</p>
            <p>– Điện thoại: ${contactShop.phoneNumber}</p>
            <p>– Hotline & bán hàng: ${contactShop.hotline}</p>
            <p>– Email: ${contactShop.email}</p>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<script src="../../../js/header.js"></script>
</html>
