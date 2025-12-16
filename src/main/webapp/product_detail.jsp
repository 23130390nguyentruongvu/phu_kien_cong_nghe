<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/product_detail.css" />
    <link rel="stylesheet" href="shared/main.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="shared/item_product.css" />

    <title>Cáp nối dài USB 3.0 dài 1,5M AM to AF</title>
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
                <li>
                  Kết nối với chúng tôi:
                  <a href="" class="text-hover"
                    ><i class="fa-brands fa-facebook"></i
                  ></a>
                  <a href="" class="text-hover"
                    ><i class="fa-brands fa-instagram"></i
                  ></a>
                </li>
              </ul>
            </div>
            <div class="empty"></div>
            <div id="stateUser">
              <div class="wrap-state-user">
                <a class="state-user-hover">
                  <i class="fa-solid fa-circle-user"></i>
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
            <a href="../index.html"
              ><img
                src="../assets/image/logo.webp"
                style="width: 60px; height: 60px"
            /></a>
          </div>
          <div class="center form-search">
            <form action="" method="get">
              <input
                type="search"
                name="search-product"
                placeholder="Tìm kiếm sản phẩm..."
                class="search-input"
              />
              <button class="search-button" type="submit">
                <i class="fa-solid fa-magnifying-glass"></i>
              </button>
            </form>
          </div>
          <div class="center shopping-cart">
            <a
              ><i class="fa-solid fa-cart-shopping"
                ><span id="badgeNumItemCart" class="badge">0</span></i
              ></a
            >
          </div>
        </div>
        <!--        close header-center-->
        <!--        open header-below-->
        <div class="header-below">
          <div class="empty-left"></div>
          <div class="main-menu below-center">
            <div class="grid-column-below">
              <ul>
                <li id="menuHome" class="menu-item">
                  <a href="home.jsp">TRANG CHỦ</a>
                </li>
                <li id="menuIntroduce" class="menu-item">
                  <a href="introduction.jsp">GIỚI THIỆU</a>
                </li>
                <li id="menuProducts" class="menu-item">
                  <a href="product_category.jsp"
                    >DANH MỤC SẢN PHẨM<i class="fa-solid fa-chevron-down"></i
                  ></a>
                  <div class="show-when-hover products below-center">
                    <div class="grid-column sub-menu-products">
                      <ul>
                        <li id="menuItem1" class="sub-menu-item">
                          <a href="">Phụ Kiện Máy Tính</a>
                        </li>
                        <li id="menuItem2" class="sub-menu-item">
                          <a href="">Phụ Kiện Điện Thoại</a>
                        </li>
                        <li id="menuItem3" class="sub-menu-item">
                          <a href="">Dây Cáp Tín Hiệu</a>
                        </li>
                        <li id="menuItem4" class="sub-menu-item">
                          <a href="">Bộ Chia Tín Hiệu</a>
                        </li>
                        <li id="menuItem5" class="sub-menu-item">
                          <a href="">phụ Kiện Xe</a>
                        </li>
                        <li id="menuItem6" class="sub-menu-item">
                          <a href="">Thiết Bị Mạng</a>
                        </li>
                        <li id="menuItem7" class="sub-menu-item">
                          <a href="">Thiết Bị Ngoại Vi</a>
                        </li>
                        <li id="menuItem8" class="sub-menu-item">
                          <a href="">Bộ Chuyển Đổi Tín Hiệu</a>
                        </li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li id="menuNews" class="menu-item">
                  <a href="news.jsp">TIN TỨC</a>
                </li>
                <li id="menuContact" class="menu-item">
                  <a href="contact.jsp">LIÊN HỆ VỚI CHÚNG TÔI</a>
                </li>
              </ul>
            </div>
          </div>
          <div class="empty-right"></div>
        </div>
        <!--        close header-below-->
      </div>
    </div>

    <main>
      <div class="container">
        <div class="sidebar-product">
          <aside class="widget widget_search">
            <h3>DANH MỤC SẢN PHẨM</h3>
            <div class="search-box">
              <input type="search" placeholder="Tìm sản phẩm…" />
              <button><i class="fa-solid fa-magnifying-glass"></i></button>
            </div>
          </aside>

          <aside class="widget widget_product_categories">
            <ul class="product-categories">
              <li class="category">
                <a href="#" class="category-title">Bộ Chia Tín Hiệu</a>
                <ul class="sub-category">
                  <li><a href="#">Bộ Chia AV</a></li>
                  <li><a href="#">Bộ Chia HDMI</a></li>
                  <li><a href="#">Bộ Chia VGA</a></li>
                  <li><a href="#">Bộ Chia USB</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Bộ Chuyển Đổi Tín Hiệu</a>
                <ul class="sub-category">
                  <li><a href="#">Chuyển Đổi Audio Quang</a></li>
                  <li><a href="#">Chuyển Đổi HDMI</a></li>
                  <li><a href="#">Chuyển Đổi VGA</a></li>
                  <li><a href="#">Chuyển Đổi USB</a></li>
                  <li><a href="#">Cáp DisplayPort</a></li>
                  <li><a href="#">Đầu Nối HDMI, VGA</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Dây Cáp Tín Hiệu</a>
                <ul class="sub-category">
                  <li><a href="#">Cáp Âm Thanh</a></li>
                  <li><a href="#">Cáp HDMI</a></li>
                  <li><a href="#">Cáp VGA</a></li>
                  <li><a href="#">Cáp USB</a></li>
                  <li><a href="#">Cáp Lập Trình</a></li>
                  <li><a href="#">Cáp DVI</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Phụ Kiện Điện Thoại</a>
                <ul class="sub-category">
                  <li><a href="#">Cáp HDMI Cho Điện Thoại</a></li>
                  <li><a href="#">Cáp OTG</a></li>
                  <li><a href="#">Kính 3D VR Shinecon</a></li>
                  <li><a href="#">Thiết Bị Bluetooth</a></li>
                  <li><a href="#">Đồ Chơi SmartPhone</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Phụ Kiện Máy Tính</a>
                <ul class="sub-category">
                  <li><a href="#">Card Màn Hình</a></li>
                  <li><a href="#">Card Sound USB</a></li>
                  <li><a href="#">Card PCI Express</a></li>
                  <li><a href="#">Chuột Bay, Micro, Webcam</a></li>
                  <li><a href="#">Đầu Đọc Thẻ</a></li>
                  <li><a href="#">Túi Chống Sốc</a></li>
                  <li><a href="#">Đồ Chơi Laptop PC</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Phụ Kiện Xe</a>
                <ul class="sub-category">
                  <li><a href="#">Phụ Kiện Xe Đạp</a></li>
                  <li><a href="#">Phụ Kiện Ô Tô</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Thiết Bị Mạng</a>
                <ul class="sub-category">
                  <li><a href="#">Bút Soi Quang</a></li>
                  <li><a href="#">Dao Cắt Quang</a></li>
                  <li><a href="#">Kìm Bấm Mạng</a></li>
                  <li><a href="#">Máy Test Mạng</a></li>
                  <li><a href="#">Dây Cáp Mạng</a></li>
                </ul>
              </li>

              <li class="category">
                <a href="#" class="category-title">Thiết Bị Ngoại Vi</a>
                <ul class="sub-category">
                  <li><a href="#">Máy Trợ Giảng, Bút Trình Chiếu</a></li>
                  <li><a href="#">Phụ Kiện Đàn Guitar</a></li>
                  <li><a href="#">Nguồn Sạc</a></li>
                  <li><a href="#">Thiết Bị Khác</a></li>
                </ul>
              </li>
            </ul>
          </aside>
        </div>

        <div class="content">
          <div class="container-product two-column">
            <div class="product-image">
              <img
                id="mainProductImage"
                src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                alt="Cáp nối dài USB 3.0 1,5M AM to AF"
              />

              <div class="thumbnail-list">
                <img
                  class="thumb active"
                  src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                />
                <img
                  class="thumb"
                  src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                />
                <img
                  class="thumb"
                  src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                />
                <img
                  class="thumb"
                  src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                />
                <img
                  class="thumb"
                  src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                />
              </div>
            </div>

            <div class="product-details">
              <div class="title-product">
                Cáp nối dài USB 3.0 dài 1,5M AM to AF
              </div>
              <div class="price-product">
                <span class="original-price">100.000₫</span>
                <span class="sale-price">80.000₫</span>
              </div>

              <ul class="product-info">
                <li><strong>Bảo hành:</strong> 6 tháng</li>

                <li>
                  <ul class="sub-info">
                    <li>- Cáp nối dài USB 3.0 dài 1,5M AM to AF</li>
                    <li>
                      - Dùng cho ổ cứng di động, webcam, usb, bàn phím chuột,
                      tay game.
                    </li>
                    <li>- Cáp USB 2 đầu chuẩn 3.0 dài 1.5m AM-AF</li>
                  </ul>
                </li>
              </ul>
              
              <form id="addToCartForm" method="POST" action="/add-to-cart">
                <input type="hidden" name="product_id" value="[PRODUCT_ID]" />

                <div class="variant-section">
                  <div class="variant-title">Chọn loại:</div>

                  <div class="variant-list">
                    <label class="variant-item active">
                      <input
                        type="radio"
                        name="variant_id"
                        value="variant_1_5m_id"
                        checked
                        hidden
                      />
                      <img
                        src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                      />
                      <span>1.5 Mét</span>
                    </label>

                    <label class="variant-item">
                      <input
                        type="radio"
                        name="variant_id"
                        value="variant_3m_id"
                        hidden
                      />
                      <img
                        src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                      />
                      <span>3 Mét</span>
                    </label>

                    <label class="variant-item">
                      <input
                        type="radio"
                        name="variant_id"
                        value="variant_5m_id"
                        hidden
                      />
                      <img
                        src="https://phukiencongnghe.com.vn/wp-content/uploads/2025/07/Noi-dai-usb-3.0_2.jpg.webp"
                      />
                      <span>5 Mét</span>
                    </label>
                  </div>
                </div>

                <div class="action-row">
                  <div class="quantity-row">
                    <button
                      type="button"
                      class="quantity-btn"
                      data-action="minus"
                    >
                      -
                    </button>

                    <input
                      type="number"
                      name="quantity"
                      value="1"
                      min="1"
                      class="quantity-input"
                    />

                    <button
                      type="button"
                      class="quantity-btn"
                      data-action="plus"
                    >
                      +
                    </button>
                  </div>

                  <!-- Add to cart -->
                  <button
                    type="submit"
                    id="addItemToCart"
                    class="add-to-cart-btn"
                  >
                    THÊM VÀO GIỎ HÀNG
                  </button>
                </div>
              </form>
              <ul class="product-info">
                <li>SKU: ND_USB3.0</li>
                <li>Danh mục: Cáp USB</li>
                <li>
                  <strong
                    >Free ship Hồ Chí Minh cho đơn hàng >500k &amp; &lt;
                    5km</strong
                  >
                </li>
                <li>>Giao hàng nhanh Toàn Quốc từ 2–4 ngày</li>
                <li>>Thanh toán linh hoạt: Chuyển khoản/COD</li>
                <li>>Hoàn tiền nếu sản phẩm khác mô tả</li>
                <li>>Bảo hành đổi mới chuyên nghiệp</li>
                <li>>Tư vấn hỗ trợ nhanh 24/7</li>
              </ul>
            </div>
          </div>

          <div class="product-description-container">
            <h3>Mô tả sản phẩm</h3>
            <h4>Cáp nối dài USB 3.0 dài 1,5M AM to AF</h4>
            <p>Cáp USB 2 đầu chuẩn 3.0 dài 1.5m tốc độ cao</p>
            <p>Blue USB 3.0 Extension Cable A-Male to A-Female</p>

            <ul>
              <li>- Chuẩn USB 3.0 AM – AF</li>
              <li>- Dây màu xanh dài 1.5m</li>
              <li>- Tốc độ truyền dữ liệu 4.8Gbps</li>
              <li>
                - Cáp USB hai đầu chuẩn 3.0 dài 1.5m (Xanh) nối dài mở rộng cổng
                USB 3.0
              </li>
              <li>
                - Chức năng: Dùng nối dài cáp USB 3.0 cho ổ cứng di động,
                webcam, usb, bàn phím chuột, tay game… Kết nối dữ liệu với HDD
                Box, flash drive, HDD, card reader, USB 3.0 hub, printers,
                cameras với PC/Laptop
              </li>
            </ul>
            <img
              src="https://sieuthimedia.vn/resources/uploaded/SieuThiMedia/Products/2016/01/1038/cap-noi-dai-usb-3-0-am-af-30cm-1-635891713383906250.jpg"
              alt="Cáp nối dài USB 3.0 dài 1,5M AM to AF"
            />
            <img
              src="data:image/jpeg;base64,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"
              alt="Cáp nối dài USB 3.0 dài 1,5M AM to AF"
            />
          </div>

          <div class="review-container">
            <h3>Đánh giá sản phẩm</h3>
            <h4>Chưa có đánh giá nào</h4>
            <br />
            <p>
              Email của bạn sẽ không được hiển thị công khai. Các trường bắt
              buộc được đánh dấu <span class="required">*</span>
            </p>
            <br />
            <form class="review-form" method="POST" action="/submit-review">
              <!-- RATING -->
              <label for="rating"
                >Đánh giá của bạn <span class="required">*</span></label
              >

              <div class="rating-stars">
                <input
                  type="radio"
                  name="rating"
                  id="star1"
                  value="1"
                  required
                />
                <label for="star1" title="1 sao">&#9733;</label>

                <input type="radio" name="rating" id="star2" value="2" />
                <label for="star2" title="2 sao">&#9733;</label>

                <input type="radio" name="rating" id="star3" value="3" />
                <label for="star3" title="3 sao">&#9733;</label>

                <input type="radio" name="rating" id="star4" value="4" />
                <label for="star4" title="4 sao">&#9733;</label>

                <input type="radio" name="rating" id="star5" value="5" />
                <label for="star5" title="5 sao">&#9733;</label>
              </div>

              <label for="comment"
                >Nhận xét <span class="required">*</span></label
              >
              <textarea
                id="comment"
                name="comment"
                class="review-textarea"
                rows="5"
                required
              ></textarea>

              <div class="form-row-container">
                <div class="form-row">
                  <label for="name">Tên <span class="required">*</span></label>
                  <input
                    type="text"
                    id="name"
                    name="name"
                    class="review-input"
                    required
                  />
                </div>

                <div class="form-row">
                  <label for="email"
                    >Email <span class="required">*</span></label
                  >
                  <input
                    type="email"
                    id="email"
                    name="email"
                    class="review-input"
                    required
                  />
                </div>
              </div>

              <button type="submit" class="submit-review-btn">
                GỬI ĐÁNH GIÁ
              </button>
            </form>
          </div>

          <div class="related-container">
            <h3>Sản phẩm liên quan</h3>

            <div class="wrap-center-content">
              <div class="products-grid-5">
                <div class="container-product-item">
                  <div class="item-wrap">
                    <div class="container-item">
                      <div class="image-product-item">
                        <img
                          src="../assets/image/fake_products/item_pkdt_1.webp"
                          loading="lazy"
                          decoding="async"
                        />
                      </div>
                      <div class="title-product-item">
                        Giá đỡ điện thoại kim loại
                      </div>
                      <div class="price-product-item">
                        45.000<span class="underline_dong">đ</span>
                      </div>
                    </div>
                  </div>
                  <div class="wrap-btn-search-similar">
                    <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                  </div>
                </div>
                <div class="container-product-item">
                  <div class="item-wrap">
                    <div class="container-item">
                      <div class="image-product-item">
                        <img
                          src="../assets/image/fake_products/item_bcth_1.webp"
                          loading="lazy"
                          decoding="async"
                        />
                      </div>
                      <div class="title-product-item">
                        Switch để bàn TP-Link LS1005G 5 cổng 10/100/1000Mbps
                      </div>
                      <div class="price-product-item">
                        100.000<span class="underline_dong">đ</span>
                      </div>
                    </div>
                  </div>
                  <div class="wrap-btn-search-similar">
                    <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                  </div>
                </div>
                <div class="container-product-item">
                  <div class="item-wrap">
                    <div class="container-item">
                      <div class="image-product-item">
                        <img
                          src="../assets/image/fake_products/item_bcth_2.webp"
                          loading="lazy"
                          decoding="async"
                        />
                      </div>
                      <div class="title-product-item">
                        Bộ chia tín hiệu AV (Video và Audio) 1 ra 8
                      </div>
                      <div class="price-product-item">
                        100.000<span class="underline_dong">đ</span>
                      </div>
                    </div>
                  </div>
                  <div class="wrap-btn-search-similar">
                    <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                  </div>
                </div>
                <div class="container-product-item">
                  <div class="item-wrap">
                    <div class="container-item">
                      <div class="image-product-item">
                        <img
                          src="../assets/image/fake_products/item_bcth_3.webp"
                          loading="lazy"
                          decoding="async"
                        />
                      </div>
                      <div class="title-product-item">
                        Bộ chia tín hiệu âm thanh RCA 2 trong 4 R/L
                      </div>
                      <div class="price-product-item">
                        100.000<span class="underline_dong">đ</span>
                      </div>
                    </div>
                  </div>
                  <div class="wrap-btn-search-similar">
                    <button class="search-similar">SẢN PHẨM TƯƠNG TỰ</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
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
                <li>
                  <i class="fa-solid fa-phone-volume"></i> Hotline: 0793189032
                </li>
                <li>
                  <i class="fa-solid fa-location-dot"></i> Địa chỉ: Kios 8
                  giang, phường Linh Trung, Thủ Đức
                </li>
                <li>
                  <i class="fa-solid fa-envelope"></i> Email: pkcn@sp.love
                </li>
              </ul>
            </div>
            <div class="footer-column second nav">
              <ul>
                <li>
                  <a href="payment_method.jsp" class="text-hover"
                    ><i class="fa-solid fa-money-bill"></i> Phương thức thanh
                    toán</a
                  >
                </li>
                <li>
                  <a href="shipping_method.jsp" class="text-hover"
                    ><i class="fa-solid fa-truck"></i> Phương thức giao hàng</a
                  >
                </li>
                <li>
                  <a href="warranty_policy.jsp" class="text-hover"
                    ><i class="fa-solid fa-award"></i> Chính sách bảo hành</a
                  >
                </li>
                <li>
                  <a href="privacy_policy.jsp" class="text-hover"
                    ><i class="fa-solid fa-lock"></i> Chính sách bảo mật</a
                  >
                </li>
                <li>
                  <a href="term_of_service.jsp" class="text-hover"
                    ><i class="fa-solid fa-pen-nib"></i> Điều khoản dịch vụ</a
                  >
                </li>
                <li>
                  <a href="voucher.jsp" class="text-hover"
                    ><i class="fa-solid fa-ticket"></i> Voucher</a
                  >
                </li>
              </ul>
            </div>
            <div class="footer-column third social-media">
              <ul>
                <li>
                  <a href="" class="text-hover"
                    ><i class="fa-brands fa-facebook"></i> Facebook</a
                  >
                </li>
                <li>
                  <a href="" class="text-hover"
                    ><i class="fa-brands fa-instagram"></i> Instagram</a
                  >
                </li>
              </ul>
            </div>
            <div class="footer-column 4th logo">
              <img src="../assets/image/logo.webp" alt="" />
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
