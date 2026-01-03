<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>${product.name}</title>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"/>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product_detail.css" />
      <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">
  </head>
  <body>
  <div class="container-header-main-footer">
  <jsp:include page="/WEB-INF/views/common/header.jsp" />
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
                      <c:if test="${not empty product.variants}">
                          <img
                                  id="mainProductImage"
                                  src="${product.variants[0].urlImage}"
                                  alt="${product.name}"
                          />

                          <div class="thumbnail-list">
                              <c:forEach items="${product.variants}" var="v" varStatus="st">
                                  <img
                                          class="thumb ${st.first ? 'active' : ''}"
                                          src="${v.urlImage}"
                                  />
                              </c:forEach>
                          </div>
                      </c:if>
                  </div>

                  <div class="product-details">

                      <div class="title-product">
                          ${product.name}
                      </div>

                      <div class="price-product">
                <span class="current-price">
                    <c:out value="${product.minPriceByFormat}" />
                </span>
                      </div>

                      <ul class="product-info">
                          <li>
                              <strong>Bảo hành:</strong> ${product.warranty}
                          </li>

                          <c:if test="${not empty product.subDescription}">
                              <li>
                                  <ul class="sub-info">
                                          ${product.subDescription}
                                  </ul>
                              </li>
                          </c:if>
                      </ul>

                      <form method="POST" action="${pageContext.request.contextPath}/add-to-cart">
                          <input type="hidden" name="product_id" value="${product.id}" />

                          <c:if test="${not empty product.variants}">
                              <div class="variant-section">
                                  <div class="variant-title">Chọn loại:</div>

                                  <div class="variant-list">
                                      <c:forEach items="${product.variants}" var="v" varStatus="st">
                                          <label class="variant-item ${st.first ? 'active' : ''}">
                                              <input
                                                      type="radio"
                                                      name="variant_id"
                                                      value="${v.id}"
                                                  ${st.first ? 'checked' : ''}
                                                      hidden
                                              />
                                              <img src="${v.urlImage}" />
                                              <span>${v.name}</span>
                                          </label>
                                      </c:forEach>
                                  </div>
                              </div>
                          </c:if>

                          <div class="action-row">
                              <div class="quantity-row">
                                  <button type="button" class="quantity-btn" data-action="minus">-</button>

                                  <input
                                          type="number"
                                          name="quantity"
                                          value="1"
                                          min="1"
                                          class="quantity-input"
                                  />

                                  <button type="button" class="quantity-btn" data-action="plus">+</button>
                              </div>

                              <button type="submit" class="add-to-cart-btn">
                                  THÊM VÀO GIỎ HÀNG
                              </button>
                          </div>
                      </form>

                      <ul class="product-info">
                          <c:if test="${not empty product.variants}">
                              <li>SKU: ${product.variants[0].sku}</li>
                          </c:if>

                          <li>Danh mục: ${product.categoryName}</li>
                          <li><strong>Free ship HCM cho đơn &gt;500k &amp; &lt;5km</strong></li>
                          <li>&gt; Giao hàng nhanh toàn quốc 2–4 ngày</li>
                          <li>&gt; Thanh toán COD / Chuyển khoản</li>
                          <li>&gt; Hoàn tiền nếu sản phẩm sai mô tả</li>
                          <li>&gt; Bảo hành đổi mới</li>
                          <li>&gt; Hỗ trợ 24/7</li>
                      </ul>

                  </div>
              </div>

              <div class="product-description-container">
                  <h3>Mô tả sản phẩm</h3>
                  ${product.description}
              </div>
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
    </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </div>
  </body>
</html>
