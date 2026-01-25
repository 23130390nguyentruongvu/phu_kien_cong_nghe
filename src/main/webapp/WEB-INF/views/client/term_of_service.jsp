<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Điều khoản và dịch vụ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/term_of_service.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<main id="main" class="main-container">
    <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>
    <div class="entry-content">
        <h3>ĐIỀU KHOẢN DỊCH VỤ</h3>
        <p>Chào mừng quý khách đến với website của ${contactShop.shopName}, vui lòng đọc kỹ Điều Khoản Dịch Vụ.</p>
        <p>– Khi quý khách hàng truy cập vào trang website của chúng tôi có nghĩa là quý khách đồng
            ý với các điều khoản này. Trang web có quyền thay đổi, chỉnh sửa, thêm hoặc lược bỏ bất kỳ
            phần nào trong điều khoản mua bán hàng hóa này, vào bất cứ lúc nào. Các thay đổi có hiệu
            lực ngay khi được đăng trên trang web mà không cần thông báo trước.</p>
        <p>– Và khi quý khách tiếp tục sử dụng trang web, sau khi các thay đổi về Điều Khoản Dịch Vụ này được đăng tải,
            có nghĩa là quý khách chấp nhận với những thay đổi đó. Quý khách vui lòng kiểm tra thường xuyên để cập nhật
            những thay đổi của chúng tôi.</p>
        <p>Xin vui lòng đọc kĩ Điều Khoản Dịch Vụ trước khi mua hàng!</p>
        <ol>
            <li>Hướng dẫn sử dụng website</li>
        </ol>
        <p>
            – Khi vào web của chúng tôi, khách hàng phải đảm bảo đủ 18 tuổi, hoặc truy cập dưới sự giám sát của cha mẹ
            hay người giám hộ hợp pháp. Khách hàng đảm bảo có đầy đủ hành vi dân sự để thực hiện các giao dịch mua bán
            hàng hóa theo quy định hiện hành của pháp luật Việt Nam.
        </p>
        <p>
            – Chúng tôi sẽ cấp một tài khoản (Account) sử dụng để khách hàng có thể mua sắm trên
            website ${contactShop.shopName} trong khuôn khổ Điều khoản và Điều kiện sử dụng đã đề ra.
        </p>
        <p>
            – Quý khách hàng sẽ phải đăng ký tài khoản với thông tin xác thực về bản thân và phải cập nhật nếu có thay
            đổi nào. Mỗi người truy cập phải có trách nhiệm với mật khẩu, tài khoản và hoạt động của mình trên web. Hơn
            nữa, quý khách phải thông báo cho chúng tôi biết khi tài khoản bị truy cập trái phép. Chúng tôi không chịu
            bất kỳ trách nhiệm nào, dù trực tiếp hay gián tiếp, đối với những thiệt hại hoặc mất mát gây ra do quý khách
            không tuân thủ quy định.
        </p>
        <p>
            – Nghiêm cấm sử dụng bất kỳ phần nào của trang web này với mục đích thương mại hoặc nhân danh bất kỳ đối tác
            thứ ba nào nếu không được chúng tôi cho phép bằng văn bản. Nếu vi phạm bất cứ điều nào trong đây, chúng tôi
            sẽ hủy tài khoản của khách mà không cần báo trước.
        </p>
        <p>
            – Trong suốt quá trình đăng ký, quý khách đồng ý nhận email thông báo từ website. Nếu không muốn tiếp tục
            nhận mail, quý khách có thể từ chối bằng cách thực hiện theo hướng dẫn của chúng tôi trong mỗi email.
        </p>
        <ol start="2">
            <li>Ý kiên của khách hàng</li>
        </ol>
        <p>– Điều Khoản Dịch Vụ quy định tất cả nội dung trang web và ý kiến phê bình của quý khách đều là tài sản của
            chúng tôi. Nếu chúng tôi phát hiện bất kỳ thông tin giả mạo nào, chúng tôi sẽ khóa tài khoản của quý khách
            ngay lập tức hoặc áp dụng các biện pháp khác theo quy định của pháp luật Việt Nam.</p>
        <ol start="3">
            <li>Xác nhận và từ chối đơn hàng</li>
        </ol>
        <p>
            – Khi quý khách đặt hàng tại ${contactShop.shopName}, chúng tôi sẽ nhận được yêu cầu đặt hàng và gửi đến quý
            khách mã số đơn hàng. Tuy nhiên, yêu cầu đặt hàng cần thông qua một bước xác nhận đơn hàng, Shop chỉ xác
            nhận đơn hàng nếu yêu cầu đặt hàng của quý khách thỏa mãn các tiêu chí thực hiện đơn hàng tại website.
        </p>
        <p>
            – Để yêu cầu đặt hàng được xác nhận nhanh chóng, quý khách vui lòng cung cấp đúng và đầy đủ các thông tin
            liên quan đến việc giao nhận, hoặc các điều khoản và điều kiện của chương trình khuyến mãi (nếu có) mà quý
            khách tham gia.
        </p>
        <p>
            – Chúng tôi có quyền từ chối hoặc hủy đơn hàng của quý khách vì bất kỳ lý do gì liên quan đến lỗi kỹ thuật,
            hệ thống một cách khách quan vào bất kỳ lúc nào. Chúng tôi sẽ hỏi thêm về số điện thoại và địa chỉ nhận hàng
            trước khi xác nhận đơn hàng.
        </p>
        <ol start="4">
            <li>Hủy bỏ đơn hàng tại ${contactShop.shopName}</li>
        </ol>
        <p>– Trường hợp quý khách muốn hủy bỏ đơn hàng đã thực hiện trên website ${contactShop.shopName} xin vui lòng
            liên hệ số
            Hotline: ${contactShop.hotline} hoặc nếu đơn hàng đang ở trạng thái "Pending" quý khách cũng có thể nhấn nút
            hủy đơn.</p>


        <ol start="5">
            <li>Giải quyết hậu quả do lỗi nhập sai thông tin tại ${contactShop.shopName}</li>
        </ol>
        <p>– Khách hàng có trách nhiệm cung cấp thông tin đầy đủ và chính xác khi tham gia giao dịch
            tại ${contactShop.shopName}. Trong trường hợp khách hàng nhập sai thông tin, Phụ Kiện Công Nghệ có quyền từ
            chối thực hiện giao
            dịch.</p>
        <p>– Trong trường hợp sai thông tin phát sinh từ phía website mà ${contactShop.shopName} có thể chứng minh đó là
            lỗi
            của hệ thống, shop sẽ giải quyết các sự cố phát sinh cho khách hàng hoặc sẽ đền bù một mã giảm giá cho các
            lần mua hàng tiếp theo với mệnh giá tùy từng trường hợp cụ thể và có quyền không thực hiện giao dịch bị
            lỗi.</p>
        <p>– Mọi quyền sở hữu trí tuệ (đã đăng ký hoặc chưa đăng ký), nội dung thông tin và tất cả các thiết kế, văn
            bản, đồ họa, phần mềm, hình ảnh, video, âm nhạc, âm thanh, biên dịch phần mềm, mã nguồn và phần mềm cơ bản
            đều là tài sản của chúng tôi. Toàn bộ nội dung của trang web được bảo vệ bởi luật bản quyền của Việt Nam và
            các công ước quốc tế. Bản quyền đã được bảo lưu.</p>
        <ol start="6">
            <li>Quy định về bảo mật</li>
        </ol>
        <p>– ${contactShop.shopName} coi trọng việc bảo mật thông tin và sử dụng các biện pháp tốt nhất bảo vệ thông tin
            và
            việc thanh toán của quý khách. Thông tin của quý khách trong quá trình thanh toán sẽ được mã hóa để đảm bảo
            an toàn. Sau khi quý khách hoàn thành quá trình đặt hàng, quý khách sẽ thoát khỏi chế độ an toàn.</p>
        <p>– Quý khách không được sử dụng bất kỳ chương trình, công cụ hay hình thức nào khác để can thiệp vào hệ thống
            hay làm thay đổi cấu trúc dữ liệu. Trang web cũng nghiêm cấm việc phát tán, truyền bá hay cổ vũ cho bất kỳ
            hoạt động nào nhằm can thiệp, phá hoại hay xâm nhập vào dữ liệu của hệ thống. Cá nhân hay tổ chức vi phạm sẽ
            bị tước bỏ mọi quyền lợi cũng như sẽ bị truy tố trước pháp luật nếu cần thiết.</p>
        <p>– Mọi thông tin giao dịch sẽ được bảo mật ngoại trừ trong trường hợp cơ quan pháp luật yêu cầu.</p>
        <ol start="7">
            <li>Quyền pháp lý</li>
        </ol>
        <p>– Các điều kiện, điều khoản và nội dung của trang web này được điều chỉnh theo luật pháp Việt Nam, Tòa án có
            thẩm quyền tại Việt Nam sẽ giải quyết bất kỳ tranh chấp nào phát sinh từ việc sử dụng trái phép trang web
            này.</p>
        <ol start="8">
            <li>Giải quyết tranh chấp</li>
        </ol>
        <p>– Bất kỳ tranh cãi, khiếu nại hoặc tranh chấp phát sinh từ hoặc liên quan đến giao dịch
            tại ${contactShop.shopName} hoặc các Quy định và Điều Khoản Dịch Vụ này đều sẽ được giải quyết bằng hình
            thức thương lượng, hòa
            giải, trọng tài và/hoặc Tòa án theo Luật bảo vệ Người tiêu dùng Chương 4 về Giải quyết tranh chấp giữa người
            tiêu dùng và tổ chức, cá nhân kinh doanh hàng hóa, dịch vụ.</p>
        <p>Điều Khoản Dịch Vụ – giao dịch trên website ${contactShop.shopName}</p>
        <strong><em>Cảm ơn Quý Khách đã tin dùng sản phẩm ủng hộ Shop!</em></strong>
    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>

</html>