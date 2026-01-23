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
        <form id="editVariantForm" method="post">
            <input type="hidden" name="variantSku" value="${requestScope.variant.sku}">
            <label>Tên biến thể: </label>
            <input type="text" class="form-input" value="${requestScope.variant.name}" name="variantNames"
                   placeholder="Tên biến thể" required>
            <br>
            <label>Giá: </label>
            <input type="number" class="form-input" value="${requestScope.variant.priceByPlainStr}" name="variantPrices"
                   placeholder="Giá" required>
            <br>
            <label>Số lượng trong kho: </label>
            <input type="number" class="form-input" value="${requestScope.variant.stock}" name="variantStocks"
                   placeholder="Số lượng" required>
            <br>
            <label>Trọng lượng (gram): </label>
            <input type="number" class="form-input" value="${requestScope.variant.gram}" name="gram"
                   placeholder="Trọng lượng (gram)">
            <br>
            <label>Màu sắc: </label>
            <input type="text" class="form-input" value="${requestScope.variant.color}" name="color"
                   placeholder="Màu sắc">
            <br>
            <label>Kích thước: </label>
            <input type="text" class="form-input" value="${requestScope.variant.size}" name="size"
                   placeholder="Kích thước">
            <br>
            <span>Ảnh ban đầu: </span>
            <img class="old-variant-img" data-url="${requestScope.variant.urlImage}"
                 src="${requestScope.variant.urlImage}" loading="lazy"
                 style="width: 80px; height: 80px">
            <br>
            <label>Thay đổi ảnh: </label>
            <input type="file" class="form-input" name="editVariantImage" accept="image/*">
            <div id="editVariantPreview"></div>
            <br>
            <div class="wrap-button-cancel-submit">
                <button type="submit" id="submitEditProdVar" class="submit">Chấp nhận</button>
                <button type="button" id="closeEditProdVar" class="close">Đóng</button>
            </div>
        </form>
    </div>
</c:if>