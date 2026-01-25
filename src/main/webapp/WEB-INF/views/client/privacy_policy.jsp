<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Chính sách bảo mật</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/privacy_policy.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">

  </head>
  <body>
  <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main>
      <div class="container">
          <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>

        <div class="content">
          <h2>CHÍNH SÁCH BẢO MẬT</h2>
          <p>
            Tất cả các khách hàng khi giao dịch trên website đều được bảo mật
            thông tin theo đúng Chính Sách Bảo Mật Thông tin.
          </p>
          <h3>1. Mục đích và phạm vi thu thập thông tin khách hàng:</h3>
          <ul>
            <li>
              – Để truy cập và sử dụng dịch vụ trên ${contactShop.shopName} quý
              khách có thể được yêu cầu cung cấp cho chúng tôi một số thông tin
              cá nhân như: Họ tên, Mail, số điện thoại liên lạc, địa chỉ liên
              hệ.
            </li>
          </ul>
          <h3>2. Phạm vi sử dụng thông tin:</h3>
          <ul>
            <li>
                ${contactShop.shopName} sử dụng dữ liệu mà khách hàng cung cấp để:
            </li>
            <li>
              – Hỗ trợ khách hàng khi mua sản phẩm – Giải đáp mọi thắc mắc khách
              hàng.
            </li>
            <li>
              – Cung cấp cho khách hàng thông tin về các sản phẩm &amp; các
              chương trình khuyến mãi của Shop.
            </li>
            <li>– Thực hiện các bản khảo sát khách hàng.</li>
            <li>
              – Thực hiện các hoạt động quảng bá liên quan đến các sản phẩm và
              dịch vụ của Shop.
            </li>
          </ul>
          <h3>3. Thời gian lưu trữ thông tin:</h3>
          <ul>
            <li>
              – Dữ liệu thông tin cá nhân của khách hàng sẽ được lưu trữ cho đến
              khi khách hàng có yêu cầu hủy bỏ hoặc tự khách hàng đăng nhập và
              thực hiện hủy bỏ. Còn lại trong mọi trường hợp thông tin cá nhân
              khách hàng sẽ được bảo mật trên hệ thống máy chủ của ${contactShop.shopName}.
            </li>
            <li>
              – Trong trường hợp có yêu cầu của pháp luật: Shop có trách nhiệm
              hợp tác cung cấp thông tin cá nhân khách hàng khi có yêu cầu từ cơ
              quan tư pháp bao gồm: Viện kiểm sát, tòa án, cơ quan công an điều
              tra liên quan đến hành vi vi phạm pháp luật nào đó của khách hàng.
              Ngoài ra, không ai có quyền xâm phạm vào thông tin cá nhân của
              khách hàng.
            </li>
          </ul>
          <h3>4. Địa chỉ của đơn vị thu thập và quản lý thông tin cá nhân:</h3>
          <ul>
            <li>– ${contactShop.shopName}</li>
            <li>– Địa chỉ:Địa chỉ: ${contactShop.shopAddress}</li>
            <li>
              Đối với các thắc mắc về hoạt động thu thập, xử lý thông tin liên
              quan đến cá nhân người tiêu dùng, khách hàng có thể liên hệ:
                ${contactShop.hotline}
            </li>
          </ul>
          <h3>
            5. Phương tiện và công cụ để người dùng tiếp cận và chỉnh sửa dữ
            liệu cá nhân của mình:
          </h3>
          <ul>
            <li>
              – Khách hàng có quyền yêu cầu ${contactShop.shopName} cập nhật, điều
              chỉnh hoặc hủy thông tin cá nhân của mình bằng cách liên hệ trực
              tiếp đến Ban quản trị hoặc thông qua địa chỉ ${contactShop.email}
            </li>
          </ul>
          <h3>6. Cam kết bảo mật thông tin cá nhân khách hàng:</h3>
          <ul>
            <li>
              – Khi khách hàng gửi thông tin cá nhân của khách hàng cho chúng
              tôi, khách hàng đã đồng ý với các điều khoản mà chúng tôi đã nêu ở
              trên.
            </li>
            <li>
              – Thông tin cá nhân của khách hàng trên website được cam kết bảo
              mật tuyệt đối theo chính sách bảo mật thông tin cá nhân của pháp
              luật. Việc thu thập, sử lý và sử dụng thông tin của mỗi khách hàng
              chỉ được thực hiện khi có sự đồng ý của khách hàng đó, trừ trường
              hợp pháp luật có những quy định khác.
            </li>
            <li>
              – Không sử dụng, chuyển giao, cung cấp hay tiết lộ thông tin khách
              hàng cho bên thứ 3 khi không có sự cho phép đồng ý từ khách hàng.
            </li>
            <li>
              – Trong trường hợp vì lý do bất kỳ mà thông tin khách hàng bị mất
              hoặc rò rỉ ra ngoài <strong>${contactShop.shopName}</strong> có trách
              nhiệm thông báo vụ việc cho cơ quan chức năng điều tra xử lý kịp
              thời và thông báo cho khách hàng được biết.
            </li>
            <li>
              – Quý khách hàng chịu trách nhiệm hoàn toàn về tính pháp lý của
              những thông tin khi cung cấp cho ${contactShop.shopName} và nên đọc
              kỹ nội dung trang Chính Sách Bảo Mật này trước khi truy cập các
              nội dung khác trên website.
            </li>
            <h3>Cảm ơn Quý Khách đã tin dùng sản phẩm ủng hộ Shop!</h3>
          </ul>
        </div>
      </div>
    </main>
  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
