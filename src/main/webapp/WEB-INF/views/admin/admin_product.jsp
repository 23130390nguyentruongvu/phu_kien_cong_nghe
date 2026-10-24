<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lí sản phẩm</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_product.css?v=7">

</head>
<body>
<div class="wrap-all-content">
    <!--    open nav admin-->
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <!--    close nav admin-->
    <!--    open main content admin-->
    <div class="main-content-admin">
        <h1 class="title-for-page">Quản lí sản phẩm</h1>
        <div class="wrap-find-info-product">
            <form>
                <input type="text" name="keySearch" class="input-product-id"
                       value="${empty requestScope.keySearch?'':requestScope.keySearch}"
                       placeholder="Tìm kiếm theo mã sản phẩm hoặc tên sản phẩm">
                <button type="submit" class="submit-data">Tìm kiếm</button>
            </form>
        </div>
        <div class="edit-product head-edit">
            <button type="button" class="edit-product-add-prod" aria-label="Thêm sản phẩm">
                <i class="fa-solid fa-circle-plus" aria-hidden="true"></i>
            </button>
        </div>
        <div class="board-res-product">
            <c:if test="${empty requestScope.products}">
                <div>
                    <h3>Không có sản phẩm nào</h3>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.products}">
                <form>
                    <table>
                        <thead>
                        <tr>
                            <th></th>
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phâm</th>
                            <th>Số lượng sản phẩm biến thể</th>
                            <th>Tổng số lượng</th>
                            <th>Phạm vi giá</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${requestScope.products}">
                            <tr>
                                <td><img class="prod-img" src="${product.mainImage}"></td>
                                <td>${product.prodId}</td>
                                <td>${product.name}</td>
                                <td>${product.productVariantQuant}</td>
                                <td>${product.totalStock}</td>
                                <td>${product.rangePriceFormat}</td>
                                <td>
                            <span class="edit-product">
                                <span class="edit-product-remove" data-name="${product.name}"
                                      data-id="${product.prodId}"><i
                                        class="fa-solid fa-circle-minus"></i></span>
                                <span class="edit-product-add-var" data-id="${product.prodId}"><i
                                        class="fa-solid fa-circle-plus"></i></span>
                                <span class="edit-product-update" data-id="${product.prodId}"><i
                                        class="fa-solid fa-pen-to-square"></i></span>
                                <span class="edit-product-show-var" data-id="${product.prodId}"><i
                                        class="fa-solid fa-eye"></i></span>
                            </span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>

            </c:if>
        </div>
    </div>
    <!--    close main content admin-->

    <!--    popup-->
    <!-- TODO:Popup ADD product -->
    <div id="popup-add-product" class="popup">
        <div class="popup-content">
            <h2>Thêm sản phẩm mới</h2>
            <form id="form-add-product" enctype="multipart/form-data" method="post" novalidate>
                <label for="add-prod-name">Tên sản phẩm</label>
                <input id="add-prod-name" type="text" name="name" class="form-input"
                       maxlength="191" required placeholder="Nhập tên sản phẩm">
                <span class="field-error" data-testid="add-product-name-error"
                      data-error-for="add-prod-name" aria-live="polite"></span>

                <label for="add-prod-warranty">Thời gian bảo hành (tháng)</label>
                <input id="add-prod-warranty" type="number" name="warranty-period" class="form-input"
                       min="1" max="127" step="1" value="12" required>
                <span class="field-error" data-testid="add-product-warranty-error"
                      data-error-for="add-prod-warranty" aria-live="polite"></span>

                <label for="add-prod-categoryId">Danh mục / Thể loại con</label>
                <select id="add-prod-categoryId" class="form-input" name="categoryId" required>
                    <option value="" selected>Chọn thể loại con</option>
                    <c:if test="${empty requestScope.categories}">
                        <option value="" disabled>Chưa có thể loại con nào</option>
                    </c:if>
                    <c:if test="${not empty requestScope.categories}">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <option value="${category.id}">${category.nameCategory}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <span class="field-error" data-testid="add-product-category-error"
                      data-error-for="add-prod-categoryId" aria-live="polite"></span>

                <label for="add-prod-subtitle">Mô tả ngắn</label>
                <textarea id="add-prod-subtitle" name="subtitle" class="form-input" maxlength="191"
                          required placeholder="Nhập mô tả ngắn"></textarea>
                <span class="field-error" data-testid="add-product-subtitle-error"
                      data-error-for="add-prod-subtitle" aria-live="polite"></span>

                <label for="add-prod-description">Mô tả chi tiết</label>
                <textarea id="add-prod-description" name="description" class="form-input"
                          required placeholder="Nhập mô tả chi tiết"></textarea>
                <span class="field-error" data-testid="add-product-description-error"
                      data-error-for="add-prod-description" aria-live="polite"></span>

                <div class="wrap-status-product">
                    <label for="statusProduct">Hiện lên website</label>
                    <input type="checkbox" name="status" checked id="statusProduct">
                </div>

                <!-- Upload ảnh sản phẩm -->
                <label for="add-prod-productImages">Ảnh sản phẩm (chọn ít nhất 1 ảnh):</label>
                <input type="file" id="add-prod-productImages" name="productImages[]" multiple
                       accept="image/*" required>
                <div id="productPreview"></div>
                <span class="field-error" data-testid="add-product-images-error"
                      data-error-for="add-prod-productImages" aria-live="polite"></span>
                <span class="field-error" data-testid="add-product-main-image-error"
                      data-error-for="add-prod-mainImage" aria-live="polite"></span>

                <!-- Biến thể sản phẩm -->
                <label>Danh sách các biến thể:</label>
                <div id="variantList">
                    <div class="add-prod-variant-item variant-item">
                        <label>Tên biến thể
                            <input type="text" class="form-input" name="variantNames" maxlength="191"
                                   minlength="3" required placeholder="Tên biến thể">
                        </label>
                        <span class="field-error" data-testid="add-variant-name-error"
                              data-error-for="variantNames" aria-live="polite"></span>
                        <label>Mã SKU
                            <input type="text" class="form-input" name="sku" maxlength="50"
                                   minlength="2" required placeholder="Mã SKU">
                        </label>
                        <span class="field-error" data-testid="add-variant-sku-error"
                              data-error-for="sku" aria-live="polite"></span>
                        <label>Giá biến thể
                            <input type="number" class="form-input" name="variantPrices" min="0.01"
                                   max="9999999999999.99" step="0.01" required placeholder="Giá">
                        </label>
                        <span class="field-error" data-testid="add-variant-price-error"
                              data-error-for="variantPrices" aria-live="polite"></span>
                        <label>Số lượng tồn kho
                            <input type="number" class="form-input" name="variantStocks" min="0"
                                   max="2147483647" step="1" required placeholder="Số lượng">
                        </label>
                        <span class="field-error" data-testid="add-variant-stock-error"
                              data-error-for="variantStocks" aria-live="polite"></span>
                        <label>Trọng lượng (gram)
                            <input type="number" class="form-input" name="gram" min="1"
                                   max="2147483647" step="1" required placeholder="Trọng lượng">
                        </label>
                        <span class="field-error" data-testid="add-variant-gram-error"
                              data-error-for="gram" aria-live="polite"></span>
                        <label>Màu sắc
                            <input type="text" class="form-input" name="color" maxlength="50"
                                   minlength="2" required placeholder="Màu sắc">
                        </label>
                        <span class="field-error" data-testid="add-variant-color-error"
                              data-error-for="color" aria-live="polite"></span>
                        <label>Kích thước
                            <input type="text" class="form-input" name="size" maxlength="50"
                                   required placeholder="Kích thước">
                        </label>
                        <span class="field-error" data-testid="add-variant-size-error"
                              data-error-for="size" aria-live="polite"></span>
                        <!-- Chỉ cho phép chọn 1 ảnh biến thể -->
                        <label>Ảnh biến thể
                            <input type="file" name="add-prod-variantImages" accept="image/*" required>
                        </label>
                        <div class="variant-image-preview" style="margin-top: 5px;"></div>
                        <span class="field-error" data-testid="add-variant-image-error"
                              data-error-for="add-prod-variantImages" aria-live="polite"></span>
                        <button type="button" class="removeVariant variant-remove-button">Xóa biến thể</button>
                    </div>
                </div>
                <span class="field-error" data-testid="add-variant-list-error"
                      data-error-for="variantList" aria-live="polite"></span>
                <section class="product-derived-preview" aria-live="polite">
                    <strong>Giá cha:</strong> <span id="derived-min-price">—</span> –
                    <span id="derived-max-price">—</span>
                    <strong>Tổng tồn kho:</strong> <span id="derived-total-stock">0</span>
                </section>
                <button type="button" id="addVariantBtn" class="variant-add-button">Thêm biến thể</button>
                <div class="wrap-button-cancel-submit">
                    <button id="submitAddProd" type="submit" class="submit">Thêm</button>
                    <button id="closeAddProd" type="button" class="close">Đóng</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        //Sự kiện review ảnh
        function handleVariantImagePreview(inputElement) {
            const file = inputElement.files[0];
            const container = inputElement.closest('.add-prod-variant-item');
            const previewDiv = container.querySelector('.variant-image-preview');
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    previewDiv.innerHTML = '\<' +
                        'img src="' + e.target.result + '" alt="Ảnh sản phẩm" style="width:80px;height:80px;object-fit:cover;margin:5px;">'
                };
                reader.readAsDataURL(file);
            } else {
                previewDiv.innerHTML = '';
            }
        }

        document.getElementById('variantList').addEventListener('change', event => {
            if (event.target.matches('input[name="add-prod-variantImages"]')) {
                handleVariantImagePreview(event.target);
            }
        });

        // Hiển thị preview ảnh sản phẩm và chọn ảnh chính
        document.getElementById('add-prod-productImages').addEventListener('change', function (event) {
            const preview = document.getElementById('productPreview');
            preview.replaceChildren();
            Array.from(event.target.files).forEach((file, index) => {
                const div = document.createElement('div');
                div.classList.add('preview-item');
                const image = document.createElement('img');
                image.alt = 'Ảnh sản phẩm';
                image.style.cssText = 'width:80px;height:80px;object-fit:cover;margin:5px;';
                const radioLabel = document.createElement('label');
                const radio = document.createElement('input');
                radio.type = 'radio';
                radio.name = 'add-prod-mainImage';
                radio.value = String(index);
                radio.checked = index === 0;
                radioLabel.append(radio, ' Ảnh chính');
                div.append(image, radioLabel);
                preview.appendChild(div);
                const reader = new FileReader();
                reader.onload = function (e) {
                    image.src = e.target.result;
                };
                reader.readAsDataURL(file);
            });
            preview.dispatchEvent(new Event('change', {bubbles: true}));
        });
        document.getElementById('addVariantBtn').addEventListener('click', function () {
            const variantList = document.getElementById('variantList');
            const newVariant = document.createElement('div');
            newVariant.classList.add('add-prod-variant-item', 'variant-item');
            newVariant.innerHTML = `
                <label>Tên biến thể
                    <input type="text" class="form-input" name="variantNames" maxlength="191" minlength="3" required placeholder="Tên biến thể">
                </label>
                <span class="field-error" data-testid="add-variant-name-error"
                      data-error-for="variantNames" aria-live="polite"></span>
                <label>Mã SKU
                    <input type="text" class="form-input" name="sku" maxlength="50" minlength="2" required placeholder="Mã SKU">
                </label>
                <span class="field-error" data-testid="add-variant-sku-error"
                      data-error-for="sku" aria-live="polite"></span>
                <label>Giá biến thể
                    <input type="number" class="form-input" name="variantPrices" min="0.01" max="9999999999999.99" step="0.01" required placeholder="Giá">
                </label>
                <span class="field-error" data-testid="add-variant-price-error"
                      data-error-for="variantPrices" aria-live="polite"></span>
                <label>Số lượng tồn kho
                    <input type="number" class="form-input" name="variantStocks" min="0" max="2147483647" step="1" required placeholder="Số lượng">
                </label>
                <span class="field-error" data-testid="add-variant-stock-error"
                      data-error-for="variantStocks" aria-live="polite"></span>
                <label>Trọng lượng (gram)
                    <input type="number" class="form-input" name="gram" min="1" max="2147483647" step="1" required placeholder="Trọng lượng">
                </label>
                <span class="field-error" data-testid="add-variant-gram-error"
                      data-error-for="gram" aria-live="polite"></span>
                <label>Màu sắc
                    <input type="text" class="form-input" name="color" maxlength="50" minlength="2" required placeholder="Màu sắc">
                </label>
                <span class="field-error" data-testid="add-variant-color-error"
                      data-error-for="color" aria-live="polite"></span>
                <label>Kích thước
                    <input type="text" class="form-input" name="size" maxlength="50" required placeholder="Kích thước">
                </label>
                <span class="field-error" data-testid="add-variant-size-error"
                      data-error-for="size" aria-live="polite"></span>
                <label>Ảnh biến thể
                    <input type="file" name="add-prod-variantImages" accept="image/*" required>
                </label>
                <div class="variant-image-preview" style="margin-top: 5px;"></div>
                <span class="field-error" data-testid="add-variant-image-error"
                      data-error-for="add-prod-variantImages" aria-live="polite"></span>
                <button type="button" class="removeVariant variant-remove-button">Xóa biến thể</button>
            `;
            variantList.appendChild(newVariant);
            variantList.dispatchEvent(new Event('change', {bubbles: true}));
        });
        document.getElementById('variantList').addEventListener('click', event => {
            const removeButton = event.target.closest('.removeVariant');
            if (!removeButton) return;
            removeButton.closest('.add-prod-variant-item').remove();
            document.getElementById('variantList')
                .dispatchEvent(new Event('change', {bubbles: true}));
        });
    </script>

    <!-- TODO: Popup  views product variant-->
    <div id="popup-variants" class="popup" style="display:none;">
        <div class="popup-content">
            <h2>Danh sách biến thể sản phẩm<span id="display-id"></span></h2>
            <div class="board-res-product">
                <table class="table-prod-variants">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Mã</th>
                        <th>SKU</th>
                        <th>Loại</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Thông số</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="variant-data-container">
                    </tbody>
                </table>
            </div>
            <div id="variant-action-message" class="field-error" data-testid="variant-action-message"
                 role="status" aria-live="polite"></div>
            <button type="button" id="closeVariants">Đóng</button>
        </div>
    </div>
    <!--   TODO: Popup add product variant-->
    <div id="popup-add-variant" class="popup">
        <div class="popup-content">
            <h2>Biến thể sản phẩm</h2>
            <form id="variantForm" method="post">
                <input type="text" class="form-input" name="variantNames" placeholder="Tên biến thể" required>
                <br>
                <input type="text" class="form-input" name="sku" placeholder="Mã SKU" required>
                <br>
                <input type="number" class="form-input" name="variantPrices" placeholder="Giá" required>
                <br>
                <input type="number" class="form-input" name="variantStocks" placeholder="Số lượng" required>
                <br>
                <input type="number" class="form-input" name="gram" placeholder="Trọng lượng (gram)">
                <br>
                <input type="text" class="form-input" name="color" placeholder="Màu sắc">
                <br>
                <input type="text" class="form-input" name="size" placeholder="Kích thước">
                <br>
                <input type="file" class="form-input" name="variantImage" accept="image/*">
                <div id="variantPreview"></div>
                <br>

                <div class="wrap-button-cancel-submit">
                    <button type="submit" id="submitAddProdVar" class="submit">Chấp nhận</button>
                    <button type="button" id="closeAddProdVar" class="close">Đóng</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        document.querySelector('input[name="variantImage"]').addEventListener('change', (event) => {
            const preview = document.getElementById('variantPreview');
            preview.innerHTML = ''; // clear cũ

            Array.from(event.target.files).forEach((file) => {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const div = document.createElement('div');
                    div.classList.add('preview-item');
                    div.innerHTML = '\<' +
                        'img src="' + e.target.result + '" alt="Ảnh sản phẩm" style="width:80px;height:80px;object-fit:cover;margin:5px;">\
                    ';
                    preview.appendChild(div);
                };
                reader.readAsDataURL(file);
            });
        });
    </script>

    <!-- TODO: edit variant-->
    <div id="popup-edit-variant" class="popup">

    </div>
    <!--  TODO:  edit product-->
    <div id="popup-edit" class="popup">
        <div class="popup-content edit-product-content">
            <h2>Chỉnh sửa sản phẩm</h2>
            <form id="editProductForm" novalidate>
                <div class="content-edit-product">

                </div>
                <input type="file" id="addImageInput" accept="image/*" multiple>
                <div id="editProductPreview" class="edit-product-images"></div>
                <!-- Nút hành động -->
                <div class="popup-actions">
                    <button type="submit" id="submit-update-product">Cập nhật</button>
                    <button type="button" id="closeEdit">Đóng</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/js/admin_product_popup.js?v=1" defer></script>
<script type="module" src="${pageContext.request.contextPath}/js/firebase_template.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_add.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_remove.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_edit.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product.js"></script>
</html>