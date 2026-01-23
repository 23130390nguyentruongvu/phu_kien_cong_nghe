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
    <link rel="stylesheet" href="../../../css/admin_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_product.css">

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
            <span class="edit-product-add-prod"><i class="fa-solid fa-circle-plus"></i></span>
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
            <form id="form-add-product" enctype="multipart/form-data" method="post">
                <input id="add-prod-name" type="text" name="name" class="form-input" placeholder="Tên sản phẩm">
                <br>
                <input id="add-prod-warranty" type="number" name="warranty-period" class="form-input"
                       placeholder="Thời gian bảo hành (tháng)">
                <br>
                <select id="add-prod-categoryId" class="form-input" name="categoryId">
                    <c:if test="${empty requestScope.categories}">
                        <option value="">Chưa có thể loại nào</option>
                    </c:if>
                    <c:if test="${not empty requestScope.categories}">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <option value="${category.id}">${category.nameCategory}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <br>
                <textarea id="add-prod-subtitle" name="subtitle" class="form-input" placeholder="Mô tả ngắn"></textarea>
                <br>
                <textarea id="add-prod-description" name="description" class="form-input"
                          placeholder="Mô tả dài"></textarea>
                <br>
                <div class="wrap-status-product">
                    <label for="statusProduct">Hiện lên website</label>
                    <input type="checkbox" name="status" checked id="statusProduct">
                </div>
                <br>

                <!-- Upload ảnh sản phẩm -->
                <label>Ảnh sản phẩm:</label>
                <input type="file" id="add-prod-productImages" name="productImages[]" multiple accept="image/*">
                <div id="productPreview"></div>

                <!-- Biến thể sản phẩm -->
                <label>Danh sách các biến thể:</label>
                <div id="variantList">
                    <div class="add-prod-variant-item variant-item">
                        <input type="text" class="form-input" name="variantNames" placeholder="Tên biến thể">
                        <br>
                        <input type="text" class="form-input" name="sku" placeholder="Mã SKU">
                        <br>
                        <input type="number" class="form-input" name="variantPrices" placeholder="Giá">
                        <br>
                        <input type="number" class="form-input" name="variantStocks" placeholder="Số lượng">
                        <br>
                        <input type="number" class="form-input" name="gram" placeholder="Trọng lượng">
                        <br>
                        <input type="text" class="form-input" name="color" placeholder="Màu sắc">
                        <br>
                        <input type="text" class="form-input" name="size" placeholder="Kích thước">
                        <br>
                        <!-- Chỉ cho phép chọn 1 ảnh biến thể -->
                        <label>Ảnh biến thể:</label>
                        <input type="file" name="add-prod-variantImages" accept="image/*">
                        <div class="variant-image-preview" style="margin-top: 5px;"></div>
                    </div>
                </div>
                <br>
                <button type="button" id="addVariantBtn">Thêm biến thể</button>
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

        //Set hiện ảnh cho biến thể đầu tiên
        document.querySelectorAll('input[name="add-prod-variantImages"]').forEach(input => {
            input.addEventListener('change', function () {
                handleVariantImagePreview(this);
            });
        });

        // Hiển thị preview ảnh sản phẩm và chọn ảnh chính
        document.getElementById('add-prod-productImages').addEventListener('change', function (event) {
            const preview = document.getElementById('productPreview');
            preview.innerHTML = ''; // clear cũ
            Array.from(event.target.files).forEach((file, index) => {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const div = document.createElement('div');
                    div.classList.add('preview-item');
                    div.innerHTML = '\<' +
                        'img src="' + e.target.result + '" alt="Ảnh sản phẩm" style="width:80px;height:80px;object-fit:cover;margin:5px;">\
                         <br>\
                        <input type="radio" name="add-prod-mainImage" value="' + index + '" ' + (index === 0 ? 'checked' : '') + '> Ảnh chính\
';

                    preview.appendChild(div);
                };
                reader.readAsDataURL(file);
            });
        });
        document.getElementById('addVariantBtn').addEventListener('click', function () {
            const variantList = document.getElementById('variantList');

            // Tạo một div mới cho biến thể
            const newVariant = document.createElement('div');
            newVariant.classList.add('add-prod-variant-item', 'variant-item');
            newVariant.innerHTML = `\
            <input type="text" class="form-input" name="variantNames" placeholder="Tên biến thể">\
            <br>\
            <input type="text" class="form-input" name="sku" placeholder="Mã SKU">\
            <br>\
            <input type="number" class="form-input" name="variantPrices" placeholder="Giá">\
            <br>\
            <input type="number" class="form-input" name="variantStocks" placeholder="Số lượng">\
            <br>\
            <input type="number" class="form-input" name="gram" placeholder="Trọng lượng">\
            <br>\
            <input type="text" class="form-input" name="color" placeholder="Màu sắc">\
            <br>\
            <input type="text" class="form-input" name="size" placeholder="Kích thước">\
            <br>\
            <label>Ảnh biến thể:</label>\
            <input type="file" name="add-prod-variantImages" accept="image/*">\
            <div class="variant-image-preview" style="margin-top: 5px;"></div>\
            <button type="button" class="removeVariant">Xóa biến thể</button>\
        `;

            // Thêm vào danh sách
            variantList.appendChild(newVariant);

            const fileInput = newVariant.querySelector('input[type="file"]');
            fileInput.addEventListener('change', function () {
                handleVariantImagePreview(this);
            });

            // Gắn sự kiện xóa
            newVariant.querySelector('.removeVariant').addEventListener('click', function () {
                variantList.removeChild(newVariant);
            });
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
            <form id="editProductForm">
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

<script type="module" src="${pageContext.request.contextPath}/js/firebase.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_add.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_remove.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product_edit.js"></script>

<script type="module" src="${pageContext.request.contextPath}/js/admin_product.js"></script>
</html>