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
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main>
      <div class="container">
        <div class="sidebar-product">
          <aside class="widget widget_search">
            <h3>DANH MỤC SẢN PHẨM</h3>
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


      </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
</html>
