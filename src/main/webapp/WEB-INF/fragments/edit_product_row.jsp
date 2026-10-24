<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.product}">
    <h3>Lỗi không thấy sản phẩm</h3>
</c:if>
<c:if test="${not empty requestScope.product}">

    <!-- Tên sản phẩm -->
    <label for="name-product">Tên sản phẩm:</label>
    <input type="text" id="name-product" name="name-product" value="${requestScope.product.name}"
           minlength="3" maxlength="191" required placeholder="Tên sản phẩm">
    <span class="field-error" data-testid="edit-product-name-error" data-error-for="name-product"
          aria-live="polite"></span>
    <br>
    <!-- Thời hạn bảo hành -->
    <label for="warranty">Thời hạn bảo hành (tháng):</label>
    <input type="number" id="warranty" value="${requestScope.product.warrantyPeriod}" name="warranty"
           min="1" max="127" step="1" required placeholder="VD: 12">
    <span class="field-error" data-testid="edit-product-warranty-error" data-error-for="warranty"
          aria-live="polite"></span>
    <br>

    <!-- Mô tả ngắn -->
    <label for="short-desc">Mô tả ngắn:</label>
    <input type="text" id="short-desc" name="short-desc" value="${requestScope.product.subtitle}"
           minlength="5" maxlength="191" required placeholder="Mô tả ngắn">
    <span class="field-error" data-testid="edit-product-subtitle-error" data-error-for="short-desc"
          aria-live="polite"></span>
    <br>

    <!-- Mô tả dài -->
    <label for="long-desc">Mô tả dài:</label>
    <textarea id="long-desc" name="long-desc" minlength="10" required
              placeholder="Mô tả chi tiết" rows="5" cols="50">
            ${requestScope.product.description}
    </textarea>
    <span class="field-error" data-testid="edit-product-description-error" data-error-for="long-desc"
          aria-live="polite"></span>
    <br>

    <!-- Hiển thị trên web -->
    <label>Hiển thị trên website<input style="height: 20px" type="checkbox"
                                       class="edit-isActive"
                                       name="isVisible" ${requestScope.product.active?'checked':''}>

    </label>
    <br>

    <!-- Ảnh sản phẩm -->
    <label>Ảnh sản phẩm:</label>
    <div class="product-images">
        <div class="image-item">
            <img src="${requestScope.product.mainImage}" alt="Ảnh 1" data-url="${requestScope.product.mainImage}">
            <label style="margin-left: 150px">Ảnh chính</label>
            <input style="height: 20px" type="radio" value="${requestScope.product.mainImage}" class="mainImage" name="mainImage" checked>
            <label for="edit-remove-img">Xóa</label>
            <input type="checkbox" name="edit-remove-img"
                   class="edit-remove-img" value="Xóa"
                   style="height: 20px; width: 20px">
            <span class="field-error image-delete-error" data-testid="edit-main-image-delete-error"
                  data-error-for="edit-main-image-0" aria-live="polite"></span>
        </div>
        <c:if test="${not empty requestScope.product.images}">
            <c:forEach var="image" items="${requestScope.product.images}" varStatus="imageStatus">
                <div class="image-item">
                    <img src="${image}" alt="Ảnh 2" data-url="${image}">
                    <label style="margin-left: 150px">Ảnh chính</label>
                    <input style="height: 20px" type="radio" value="${image}" class="mainImage" name="mainImage">
                    <label for="edit-remove-img">Xóa</label>
                    <input type="checkbox" name="edit-remove-img"
                           class="edit-remove-img" value="Xóa"
                           style="height: 20px; width: 20px">
                    <span class="field-error image-delete-error"
                          data-testid="edit-image-delete-error-${imageStatus.index + 1}"
                          data-error-for="edit-main-image-${imageStatus.index + 1}" aria-live="polite"></span>
                </div>
            </c:forEach>
        </c:if>
    </div>
</c:if>