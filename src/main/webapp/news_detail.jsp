<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>
      Nguyên lý hoạt động Kính VR và Top 10 Kính Thực tế ảo đáng mua nhất
    </title>
    <link rel="stylesheet" href="css/news_detail.css" />
    <link rel="stylesheet" href="shared/main.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
  </head>
  <body>
  <div class="header">
      <div class="header-container">
          <!--     open header-above-->
          <div class="header-above">
              <div class="grid-column-above">
                  <div class="information">
                      <ul>
                          <li><strong>Phụ Kiện Công Nghệ</strong></li>
                          <li>Kết nối với chúng tôi:
                              <a href="" class="text-hover"><i class="fa-brands fa-facebook"></i></a>
                              <a href="" class="text-hover"><i class="fa-brands fa-instagram"></i></a>
                          </li>
                      </ul>
                  </div>
                  <div class="empty"></div>
                  <div id="stateUser">
                      <div class="wrap-state-user">
                          <a  class="state-user-hover"><i class="fa-solid fa-circle-user"></i></i>
                              <p id="stateUserLogin">KhaBa</p>
                          </a>
                      </div>
                  </div>
              </div>
          </div>
          <!--        close header-above-->
          <!--        open header-center-->
          <div class="header-center">
              <div class="center logo">
                  <a href="../index.html"><img src="../assets/image/logo.webp" style="width: 60px;height: 60px;"/></a>
              </div>
              <div class="center form-search">
                  <form action="" method="get">
                      <input type="search" name="search-product" placeholder="Tìm kiếm sản phẩm..."
                             class="search-input">
                      <button class="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i>
                      </button>
                  </form>
              </div>
              <div class="center shopping-cart">
                  <a ><i class="fa-solid fa-cart-shopping"><span id="badgeNumItemCart"
                                                                        class="badge">0</span></i></a>
              </div>
          </div>
          <!--        close header-center-->
          <!--        open header-below-->
          <div class="header-below">
              <div class="empty-left"></div>
              <div class="main-menu below-center">
                  <div class="grid-column-below">
                      <ul>
                          <li id="menuHome" class="menu-item"><a href="home.jsp">TRANG CHỦ</a></li>
                          <li id="menuIntroduce" class="menu-item"><a href="introduction.jsp">GIỚI THIỆU</a></li>
                          <li id="menuProducts" class="menu-item"><a href="product_category.jsp">DANH MỤC SẢN PHẨM<i
                                  class="fa-solid fa-chevron-down"></i></a>
                              <div class="show-when-hover products below-center">
                                  <div class="grid-column sub-menu-products">
                                      <ul>
                                          <li id="menuItem1" class="sub-menu-item"
                                          ><a href="">Phụ Kiện
                                              Máy Tính</a></li>
                                          <li id="menuItem2" class="sub-menu-item"
                                          ><a href="">Phụ Kiện
                                              Điện Thoại</a></li>
                                          <li id="menuItem3" class="sub-menu-item"
                                          ><a href="">Dây Cáp
                                              Tín Hiệu</a></li>
                                          <li id="menuItem4" class="sub-menu-item"
                                          ><a href="">Bộ Chia
                                              Tín Hiệu</a></li>
                                          <li id="menuItem5" class="sub-menu-item"><a href="">phụ Kiện Xe</a>
                                          </li>
                                          <li id="menuItem6" class="sub-menu-item"><a href="">Thiết Bị Mạng</a>
                                          </li>
                                          <li id="menuItem7" class="sub-menu-item"><a href="">Thiết Bị Ngoại Vi</a>
                                          </li>
                                          <li id="menuItem8" class="sub-menu-item"><a href="">Bộ Chuyển Đổi Tín
                                              Hiệu</a>
                                          </li>
                                      </ul>
                                  </div>
                              </div>
                          </li>
                          <li id="menuNews" class="menu-item active"><a href="news.jsp">TIN TỨC</a></li>
                          <li id="menuContact" class="menu-item"><a href="contact.jsp">LIÊN HỆ VỚI CHÚNG TÔI</a></li>
                      </ul>
                  </div>
              </div>
              <div class="empty-right"></div>
          </div>
          <!--        close header-below-->
      </div>
  </div>

    <main>
      <div class="news-detail-container">
        <!-- Thông tin tác giả và ngày đăng -->
        <div class="news-meta">
          <p>
            <i class="fa-solid fa-user"></i> Bởi <strong>admin</strong> /
            <i class="fa-solid fa-calendar-days"></i> 04.07.2025
          </p>
        </div>

        <div class="news-container">
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2024_3_26_638470931197972279_kinh-vr-17.jpg"
              alt="Mở đầu về kính thực tế ảo VR"
            />
          </div>

          <div class="news-content">
            <h1 class="news-title">
              Nguyên lý hoạt động của Kính VR và Top 10 Kính Thực tế ảo đáng mua
              nhất
            </h1>
            <p class="news-description">
              Khi sử dụng kính VR, người dùng có thể cảm nhận một cách rõ rệt
              nhất không gian xung quanh mình thông qua ống kính hiển thị những
              hình ảnh hoặc video 3D chân thật. Ngày nay, kính VR đã được ứng
              dụng rộng rãi trong các lĩnh vực như y tế, giải trí, giáo dục,...
              với những mục đích sử dụng khác nhau.
            </p>
          </div>
        </div>

        <!-- Nội dung tin tức -->
        <div class="news-content">
          <p>
            Kính VR là một trong những công nghệ giúp con người có thể tương tác
            với thế giới xung quanh thông qua không gian ảo. Từ việc trải nghiệm
            các kỳ quan trên thế giới ngay tại nhà cho đến việc tận hưởng những
            trò chơi giải trí, kính VR giúp con người có những trải nghiệm tuyệt
            vời chưa từng có. Hãy cùng FPT Shop khám phá về thiết bị công nghệ
            thần kỳ này nhé!
          </p>
          <p>
            Bài viết này sẽ giới thiệu nguyên lý hoạt động của kính VR cùng danh
            sách 10 sản phẩm đáng mua nhất hiện nay trên thị trường.
          </p>

          <h3>Kính VR là gì?</h3>
          <p>
            <strong>Kính VR</strong> hay
            <strong>kính thực tế ảo (Virtual Reality Glasses)</strong> là thiết
            bị có khả năng mô phỏng lại không gian ảo một cách chân thật nhất.
            Nhờ vào công nghệ VR mà góc nhìn của con người thông qua ống kính
            trở nên sống động hơn.
          </p>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-1.jpg"
              alt="Kính thực tế ảo VR 1"
            />
          </div>
          <h3>Nguyên lý hoạt động của kính VR</h3>
          <p>
            Kính VR sẽ hoạt động dựa trên nguyên tắc 3D. Lúc này màn hình sẽ
            được chia ra làm 2 khung hình và hội tụ qua thấu kính để giúp hình
            ảnh có độ nổi. Khi dùng ống kính VR thì hình ảnh sẽ nhìn trông xa và
            lớn hơn giúp đem đến cho bạn những trải nghiệm tuyệt vời. Màn hình
            sẽ hiển thị ở trên smartphone hay tivi,... và có G-sensor sử dụng
            được kính thực tế ảo.
          </p>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-2.jpg"
              alt="Kính thực tế ảo VR 2"
            />
          </div>
          <h3>Phân loại kính VR</h3>
          <h4>Phân loại theo thiết bị sử dụng:</h4>
          <p>
            Dựa vào thiết bị sử dụng, kính thực tế ảo (VR) có thể được chia
            thành hai loại chính:
          </p>

          <ul>
            <li>
              <strong>Kính VR dùng điện thoại:</strong>
              Loại kính này hoạt động bằng cách gắn trực tiếp điện thoại vào
              phần giá đỡ của kính. Người dùng có thể trải nghiệm xem phim 3D
              hoặc chơi game thực tế ảo với chi phí thấp hơn.
            </li>

            <li>
              <strong>Kính VR dùng thiết bị ngoại vi:</strong>
              Đây là dòng kính cao cấp, thường kết nối với máy tính hoặc thiết
              bị chơi game qua cáp hoặc không dây. Loại này phù hợp cho công
              việc thiết kế 3D, chơi game chuyên nghiệp, nhưng giá thành cao
              hơn.
            </li>
          </ul>

          <h4>Phân loại theo công nghệ:</h4>
          <p>
            Dựa theo công nghệ mà kính thực tế ảo sẽ được phân loại như sau:
          </p>

          <ul>
            <li>
              <strong>Kính VR:</strong>
              Đây là loại công nghệ đời đầu được kết hợp cùng điện thoại và máy
              tính cá nhân. Tuy nhiên người dùng kính sẽ bị hạn chế khi di
              chuyển.
            </li>

            <li>
              <strong>Kính AR:</strong>
              Đây là công nghệ kết hợp giữa thông tin ảo và thế giới thật cùng
              hình ảnh, âm thanh có tốc độ cao. Người dùng có thể trải nghiệm
              tốt hơn qua loại kính này và nhận biết được những hoạt động thực
              song song.
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-3.jpg"
              alt="Kính thực tế ảo VR 3"
            />
          </div>

          <h3>Kinh nghiệm chọn kính VR</h3>
          <ul>
            <li>
              <strong>Về mục đích sử dụng:</strong>
              Trước khi mua kính, bạn cần phải biết bản thân sẽ sử dụng vào mục
              đích gì, ví dụ như xem phim hay chơi game,... Từ đó thì bạn mới
              lựa chọn được mẫu mã và chi phí phù hợp với mình.
            </li>

            <li>
              <strong>Về chất liệu:</strong>
              Vì chất liệu kính sẽ tiếp xúc trực tiếp với mắt nên phần vỏ sẽ là
              nhựa cứng cùng đệm mút bằng da/vải. Chính vì vậy, chất liệu kính
              cần nên an toàn để mắt có thể nhìn được hình ảnh đẹp nhất và giảm
              thiểu tình trạng bị mỏi mắt.
            </li>

            <li>
              <strong>Về kiểu dáng và trọng lượng:</strong>
              Kính phải có thiết kế nhẹ nhàng, kín và thoải mái để mang lại trải
              nghiệm tốt hơn.
            </li>

            <li>
              <strong>Về tiêu cự:</strong>
              Để bảo vệ mắt tốt hơn thì kính phải điều chỉnh được tiêu cự thì
              hình ảnh hiển thị mới đồng nhất và tốt hơn cho mắt.
            </li>

            <li>
              <strong>Về khả năng tương tích với PC và điện thoại:</strong>
              Bạn nên chọn loại kính có khả năng tương thích với nhiều loại điện
              thoại, hệ điều hành và máy tính để dùng thuận tiện hơn.
            </li>

            <li>
              <strong>Về tính năng và tiện ích:</strong> Ngày nay, có một số
              loại kính VR có thêm chức năng điều hướng được bằng tay kèm theo
              tai nghe nhằm phục vụ mục đích nghe nhạc mọi lúc, mọi nơi. Nếu như
              bạn yêu thích tính năng này có thể tìm hiểu về những sản phẩm có
              tích hợp tiện ích này nhé.
            </li>

            <li>
              <strong>Về giá bán:</strong>
              Giá bán của kính VR sẽ dao động từ sản phẩm rẻ cho đến cao cấp và
              còn tùy thuộc vào tính năng, mẫu mã từng loại.
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-4.jpg"
              alt="Kính thực tế ảo VR 4"
            />
          </div>

          <h3>Cách sử dụng kính VR</h3>
          <p>
            Để sử dụng được kính VR thì bạn phải chuẩn bị thêm tablet trình
            chiếu hoặc điện thoại cùng những ứng dụng như module G-sensor. Tiếp
            theo bạn mở video hoặc phim muốn xem rồi gắn kính lên mắt và điều
            chỉnh nút xoay để hình ảnh được sắc nét nhất có thể.
          </p>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-5.jpg"
              alt="Kính thực tế ảo VR 5"
            />
          </div>

          <h3>Ứng dụng của kính VR</h3>

          <p>
            Kính VR thường được sử dụng trong các lĩnh vực như điện tử, du lịch,
            y học,... Cụ thể như sau:
          </p>

          <ul>
            <li>
              <strong>Trong trò chơi điện tử:</strong>
              Kính VR mang đến trải nghiệm chân thật và sống động hơn, giúp
              người chơi cảm nhận như đang ở trong thế giới game.
            </li>

            <li>
              <strong>Trong lĩnh vực y học:</strong>
              Kính VR được sử dụng để mô phỏng hình ảnh 3D, hỗ trợ quá trình
              giảng dạy, phẫu thuật và nghiên cứu với chi phí thấp hơn.
            </li>

            <li>
              <strong>Trong mảng du lịch:</strong>
              Kính VR cho phép người dùng trải nghiệm tham quan các kỳ quan nổi
              tiếng trên thế giới ngay tại nhà, mở ra xu hướng du lịch ảo hiện
              đại.
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-6.jpg"
              alt="Kính thực tế ảo VR 6"
            />
          </div>

          <h3>Những điểm cần lưu ý khi sử dụng kính VR</h3>

          <p>
            Khi sử dụng kính thực tế ảo (VR), người dùng cần chú ý một số điều
            quan trọng để đảm bảo an toàn cho sức khỏe và đạt được trải nghiệm
            tốt nhất:
          </p>

          <ul>
            <li>
              <strong>Chọn mua sản phẩm chất lượng:</strong>
              Nên ưu tiên những loại kính VR có thương hiệu uy tín, nguồn gốc rõ
              ràng, tránh hàng kém chất lượng gây ảnh hưởng đến mắt và cảm giác
              khi sử dụng.
            </li>

            <li>
              <strong>Không sử dụng quá lâu:</strong>
              Chỉ nên dùng kính VR trong khoảng thời gian ngắn, tối đa
              <b>30 phút/lần</b>, để tránh mỏi mắt, đau đầu hoặc rối loạn thị
              giác tạm thời.
            </li>

            <li>
              <strong>Thư giãn sau khi điều chỉnh tiêu cự:</strong>
              Sau khi điều chỉnh kính hoặc sử dụng lâu, nên nhắm mắt nghỉ ngơi
              vài phút để mắt thích nghi và giảm căng thẳng.
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-7.jpg"
              alt="Kính thực tế ảo VR 7"
            />
          </div>

          <h3>Những câu hỏi thường gặp về kính VR</h3>

          <ul>
            <li>
              <strong>Kính VR có gây hại cho mắt không?</strong>
              <p>
                Kính VR sẽ không gây hại cho mắt nếu người dùng tuân thủ đúng
                các nguyên tắc sử dụng như:
              </p>
              <ul>
                <li>Không dùng quá <b>30 phút/lần</b>.</li>
                <li>Nên nhắm mắt nghỉ ngơi một lúc sau khi sử dụng.</li>
                <li>
                  Không sử dụng các sản phẩm kém chất lượng, không rõ nguồn gốc.
                </li>
              </ul>
            </li>

            <li>
              <strong
                >Kính nhựa có ưu điểm gì so với loại kính giấy
                Cardboard?</strong
              >
              <p>
                Kính nhựa có thể điều chỉnh tiêu cự giúp mở rộng góc nhìn và
                mang lại hình ảnh sắc nét hơn. Ngoài ra, kính nhựa còn có độ bền
                cao và được trang bị dây đeo đầu giúp cố định khi sử dụng, mang
                lại trải nghiệm thoải mái hơn so với kính giấy Cardboard.
              </p>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-8.jpg"
              alt="Kính thực tế ảo VR 8"
            />
          </div>
          <h3>TOP các sản phẩm kính VR chất lượng hiện nay</h3>

          <ul>
            <li>
              <strong>1. Kính VR Oculus Quest 2</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Màu sắc: Trắng</li>
                <li>- Thương hiệu: Oculus</li>
                <li>- Tính năng: Bluetooth</li>
                <li>- Loại: 64GB, 128GB, 256GB</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Đồ họa ấn tượng và trải nghiệm khi chơi game mượt mà</li>
                <li>
                  - Sử dụng được 2 chế độ: dùng với PC và độc lập hoàn toàn
                </li>
                <li>- Tương thích với nhiều tựa game</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-9.jpg"
              alt="Kính thực tế ảo VR 9"
            />
          </div>
          <ul>
            <li>
              <strong>2. Kính VR 3D Shinecon G07E</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Kích thước: 10.5cm x 5cm x 3.5cm</li>
                <li>- Trọng lượng: 0.043kg</li>
                <li>- Tính năng: Bluetooth 3.0</li>
                <li>- Màu sắc: Đen xám</li>
                <li>- Hoạt động: 2 pin AAA</li>
                <li>- Thương hiệu: VR Shinecon</li>
                <li>- Khoảng cách nhận được tối đa: 10m</li>
                <li>- Tích hợp được loa</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Thiết kế không dây tiện lợi</li>
                <li>- Sở hữu nhiều công nghệ hiện đại</li>
                <li>- Odyssey G7 mang tới trải nghiệm VR ấn tượng</li>
                <li>
                  - Màn hình AMOLED cao cấp, sắc nét, cho phép hình ảnh hiển thị
                  chân thực và sống động
                </li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-10.jpg"
              alt="Kính thực tế ảo VR 10"
            />
          </div>
          <ul>
            <li>
              <strong>3.Kính VR Box 2</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Trọng lượng: 330g</li>
                <li>- Chất liệu: Nhựa PU-ABS</li>
                <li>- Màu sắc: Trắng đen</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>
                  - Thiết kế đẹp mắt và sang trọng, có thể cầm nắm được thoải
                  mái
                </li>
                <li>- Điều chỉnh và tháo lắp thấu kính một cách dễ dàng</li>
                <li>- Sử dụng để chơi game hoặc xem phim</li>
                <li>- Hình ảnh rõ ràng và bảo vệ được mắt</li>
                <li>
                  - Dùng phù hợp với những loại màn hình điện thoại từ 3.5 cho
                  đến 5.5 inch
                </li>
                <li>- Sử dụng được trong mọi tư thế</li>
                <li>
                  - Thấu kính có 8 lớp giúp giảm thiểu độ nhiễu làm cho hình ảnh
                  có màu sắc đẹp và sống động hơn
                </li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-11.jpeg"
              alt="Kính thực tế ảo VR 11"
            />
          </div>
          <ul>
            <li>
              <strong>4. Kính VR Google Cardboard có bìa Carton</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Kích thước: 13.6cm x 8.6cm x 8cm</li>
                <li>- Độ phân giải: Từ 720 đến 1080MP</li>
                <li>- Chất liệu: Bìa carton cứng</li>
                <li>- Kích thước điện thoại: Từ 3.5 đến 6.5 inch</li>
                <li>- Có 2 nam châm, 2 dây co dãn, 2 lén, 1 bìa carton</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Dùng để xem các video thực tế ảo trên YouTube</li>
                <li>- Phù hợp với hầu hết smartphone hiện nay</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-12.jpg"
              alt="Kính thực tế ảo VR 12"
            />
          </div>
          <ul>
            <li>
              <strong>5. Kính VR Oculus Rift S</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Độ dài cáp: 5m</li>
                <li>- RAM: 8GB</li>
                <li>- Kết nối: USB 3.0 x Display 1.2.1</li>
                <li>- Hệ điều hành: Windows 10</li>
                <li>- Thương hiệu: Oculus</li>
                <li>- Tốc độ làm tươi: 80Hz</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Đồ họa đẹp</li>
                <li>- Thấu kính có hình ảnh mịn và sắc nét</li>
                <li>- Tay cầm thoải mái</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-13.jpg"
              alt="Kính thực tế ảo VR 13"
            />
          </div>
          <ul>
            <li>
              <strong>6. Kính VR Shinecon G06, G10, G06E</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Kích thước: 17.9cm x 12.5cm x 9.6cm</li>
                <li>- Chất liệu: Nhựa ABS cao cấp</li>
                <li>- Khối lượng: 250g</li>
                <li>- Tốc độ làm tươi: 80Hz</li>
                <li>- Thương hiệu: Shinecon</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>
                  - Tương thích với mọi thiết bị điện thoại từ 4.7 đến 6 inch
                </li>
                <li>- Dây đeo đầu và đệm da đều có thể điều chỉnh được</li>
                <li>- Tùy chỉnh được khoảng cách giữa các mắt</li>
                <li>- 108 độ FOV mang lại trải nghiệm tốt nhất</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-14.jpg"
              alt="Kính thực tế ảo VR 14"
            />
          </div>
          <ul>
            <li>
              <strong>7. Kính VR BOBO Z4</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Kích thước: 20cm x 15cm x 14.2cm</li>
                <li>- Chất liệu: Nhựa ABS</li>
                <li>- Trọng lượng: 610g</li>
                <li>- Hệ điều hành: Android 4.0, IOS</li>
                <li>- Màu sắc: Đen trắng</li>
                <li>- Góc nhìn: 120 độ</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Tùy chỉnh được âm lượng thông qua nút chức năng</li>
                <li>- Có kèm theo đèn LED và cáp OTG</li>
                <li>- Điều khiển tạm dừng hoặc tiếp tục thuận tiện</li>
                <li>- Hỗ trợ được người cận thị dưới 5 độ</li>
                <li>- Thích hợp với các tựa game 3D có dùng gamepad</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-14.jpeg"
              alt="Kính thực tế ảo VR 14"
            />
          </div>
          <ul>
            <li>
              <strong>8. Kính VR PlayStation</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Màn hình: OLED</li>
                <li>- Tần số quét: 120Hz</li>
                <li>- Độ phân giải: 1080x960</li>
                <li>- Màu sắc: Đen trắng</li>
                <li>- Thương hiệu: Sony Station</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>- Dùng được với PlayStation 4</li>
                <li>- Xem hình ảnh hoặc video 360 độ</li>
                <li>- Màn hình OLED 1920 có 5.7 inch</li>
                <li>- Hỗ trợ được tín hiệu HDR</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-15.jpg"
              alt="Kính thực tế ảo VR 15"
            />
          </div>
          <ul>
            <li>
              <strong>9. Kính VR Samsung Gear VR R325</strong>
              <p><b>Thông tin sản phẩm:</b></p>
              <ul>
                <li>- Kích thước: 20.78cm x 9.86cm x 12.25cm</li>
                <li>- Chất liệu: Nhựa ABS</li>
                <li>- Trọng lượng: 345g</li>
                <li>- Cổng sạc: Micro USB và USB Type C</li>
                <li>- Góc nhìn: 101 độ</li>
                <li>- Màu sắc: Đen</li>
              </ul>
              <p><b>Đặc điểm:</b></p>
              <ul>
                <li>
                  - Dùng tương thích với các dòng điện thoại như Samsung Galaxy
                  S8, Samsung Galaxy S8 Plus,...
                </li>
                <li>
                  - Có chế độ thông minh giúp vừa có thể dùng kính VR, vừa trả
                  lời được cuộc gọi hoặc tin nhắn ngay trên điện thoại
                </li>
                <li>- Khung màn hình lớn có độ phân giải cao</li>
                <li>- Tay cầm có thể dùng được 1 tay dễ dàng</li>
                <li>- Cổng cắm phù hợp với nhiều loại thiết bị</li>
              </ul>
            </li>
          </ul>
          <div class="news-image">
            <img
              src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/180540/Originals/kinh-vr-16.jpg"
              alt="Kính thực tế ảo VR 16"
            />
          </div>

          <h3>Tạm kết</h3>
          <p>
            Với sự tiến bộ không ngừng của công nghệ thực tế ảo hiện nay đã mở
            ra cơ hội cho con người khám phá thêm những điều mới lạ hơn trong
            cuộc sống. Việc hiểu rõ về nguyên lý hoạt động hay cách sử dụng,...
            sẽ giúp bạn tận hưởng những giây phút tuyệt vời mà kính VR mang lại.
            Hy vọng rằng qua bài viết này, bạn hiểu rõ hơn về chiếc kính thực tế
            ảo này và lựa chọn được sản phẩm ưng ý cho bản thân.
          </p>
        </div>

        <!-- Phần bình luận -->
        <div class="comment-section">
          <h3>Để lại một bình luận</h3>
          <p class="comment-note">
            Email của bạn sẽ không được hiển thị công khai. Các trường bắt buộc
            được đánh dấu <span class="required">*</span>
          </p>

          <form class="comment-form" action="#" method="post">
            <div class="form-group">
              <label for="comment">Nhập vào đây...</label>
              <textarea
                id="comment"
                name="comment"
                rows="6"
                placeholder="Viết bình luận của bạn..."
              ></textarea>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label for="name">Tên <span class="required">*</span></label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  placeholder="Tên của bạn"
                  required
                />
              </div>
              <div class="form-group">
                <label for="email">Email <span class="required">*</span></label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  placeholder="Email của bạn"
                  required
                />
              </div>
            </div>
            <button type="submit" class="btn-submit">Gửi bình luận</button>
          </form>
        </div>
      </div>
    </main>

  <div id="footer">
      <div class="footer">
          <div class="footer-container">
              <div class="footer-grid center">
                  <div class="footer-column first info">
                      <ul>
                          <li><i class="fa-solid fa-phone"></i> Gọi: 0793189000</li>
                          <li><i class="fa-solid fa-phone-volume"></i> Hotline: 0793189032</li>
                          <li><i class="fa-solid fa-location-dot"></i> Địa chỉ: Kios 8 giang, phường Linh Trung, Thủ
                              Đức
                          </li>
                          <li><i class="fa-solid fa-envelope"></i> Email: pkcn@sp.love</li>
                      </ul>
                  </div>
                  <div class="footer-column second nav">
                      <ul>
                          <li><a href="payment_method.jsp" class="text-hover"><i class="fa-solid fa-money-bill"></i> Phương thức thanh
                              toán</a>
                          </li>
                          <li><a href="shipping_method.jsp" class="text-hover"><i class="fa-solid fa-truck"></i> Phương thức giao hàng</a>
                          </li>
                          <li><a href="warranty_policy.jsp" class="text-hover"><i class="fa-solid fa-award"></i> Chính sách bảo hành</a>
                          </li>
                          <li><a href="privacy_policy.jsp" class="text-hover"><i class="fa-solid fa-lock"></i> Chính sách bảo mật</a>
                          </li>
                          <li><a href="term_of_service.jsp" class="text-hover"><i class="fa-solid fa-pen-nib"></i> Điều khoản dịch vụ</a>
                          </li>
                          <li><a href="voucher.jsp" class="text-hover"><i class="fa-solid fa-ticket"></i> Voucher</a>
                          </li>
                      </ul>
                  </div>
                  <div class="footer-column third social-media">
                      <ul>
                          <li><a href="" class="text-hover"><i class="fa-brands fa-facebook"></i> Facebook</a></li>
                          <li><a href="" class="text-hover"><i class="fa-brands fa-instagram"></i> Instagram</a></li>
                      </ul>
                  </div>
                  <div class="footer-column 4th logo">
                      <img src="../assets/image/logo.webp" alt="">
                  </div>
              </div>
              <div class="site-below">
                  <p>Copyright © 2025 Phụ Kiện Công Nghệ</p>
              </div>
          </div>
      </div>
  </div>
  </body>
  <script src="js/header.js"></script>
</html>
