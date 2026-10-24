<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.variant}">
    <div class="popup-content">
        <h3>Không tìm thấy biến thể với mã: ${requestScope.variant.id}</h3>
        <div class="wrap-button-cancel-submit">
            <button type="button" id="closeEditProdVar" class="close">Đóng</button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.variant}">
    <!-- TODO: Popup edit product variant-->
    <div class="popup-content">
        <h2>Biến thể sản phẩm</h2>
        <form id="editVariantForm" method="post" novalidate>
            <label for="edit-variant-sku">Mã SKU:</label>
            <input id="edit-variant-sku" type="text" class="form-input" name="variantSku"
                   value="${requestScope.variant.sku}" readonly aria-readonly="true">
            <span class="field-error" data-testid="edit-variant-sku-error"
                  data-error-for="variantSku" aria-live="polite"></span>
            <label>Tên biến thể: </label>
            <input type="text" class="form-input" value="${requestScope.variant.name}"
                   name="variantNames" minlength="3" maxlength="191"
                   placeholder="Tên biến thể" required>
            <span class="field-error" data-testid="edit-variant-name-error"
                  data-error-for="variantNames" aria-live="polite"></span>
            <br>
            <label>Giá: </label>
            <input type="number" class="form-input" value="${requestScope.variant.priceByPlainStr}"
                   name="variantPrices" min="0.01" max="9999999999999.99"
                   step="0.01" placeholder="Giá" required>
            <span class="field-error" data-testid="edit-variant-price-error"
                  data-error-for="variantPrices" aria-live="polite"></span>
            <br>
            <label>Số lượng trong kho: </label>
            <input type="number" class="form-input" value="${requestScope.variant.stock}"
                   name="variantStocks" min="0" max="2147483647" step="1"
                   placeholder="Số lượng" required>
            <span class="field-error" data-testid="edit-variant-stock-error"
                  data-error-for="variantStocks" aria-live="polite"></span>
            <br>
            <label>Trọng lượng (gram): </label>
            <input type="number" class="form-input" value="${requestScope.variant.gram}" name="gram"
                   min="1" max="2147483647" step="1" placeholder="Trọng lượng (gram)" required>
            <span class="field-error" data-testid="edit-variant-gram-error"
                  data-error-for="gram" aria-live="polite"></span>
            <br>
            <label>Màu sắc: </label>
            <input type="text" class="form-input" value="${requestScope.variant.color}" name="color"
                   minlength="2" maxlength="50" placeholder="Màu sắc" required>
            <span class="field-error" data-testid="edit-variant-color-error"
                  data-error-for="color" aria-live="polite"></span>
            <br>
            <label>Kích thước: </label>
            <input type="text" class="form-input" value="${requestScope.variant.size}" name="size"
                   maxlength="50" placeholder="Kích thước" required>
            <span class="field-error" data-testid="edit-variant-size-error"
                  data-error-for="size" aria-live="polite"></span>
            <br>
            <span>Ảnh ban đầu: </span>
            <img class="old-variant-img" data-url="${requestScope.variant.urlImage}"
                 src="${requestScope.variant.urlImage}" loading="lazy"
                 style="width: 80px; height: 80px">
            <br>
            <label>Thay đổi ảnh: </label>
            <input type="file" class="form-input" name="editVariantImage" accept="image/*">
            <span class="field-error" data-testid="edit-variant-image-error"
                  data-error-for="editVariantImage" aria-live="polite"></span>
            <div id="editVariantPreview"></div>
            <br>
            <div class="wrap-button-cancel-submit">
                <button type="submit" id="submitEditProdVar" class="submit">Chấp nhận</button>
                <button type="button" id="closeEditProdVar" class="close">Đóng</button>
            </div>
        </form>
    </div>
</c:if>