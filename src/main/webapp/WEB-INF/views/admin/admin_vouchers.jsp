<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý voucher</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_content.css">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <main class="main-content-admin content-admin">
        <header class="page-heading">
            <div><span class="eyebrow">ƯU ĐÃI</span><h1>Quản lý voucher</h1></div>
            <p>Voucher áp dụng cho danh mục hoặc sản phẩm theo quy tắc OR. Không chọn đối tượng nào thì áp dụng cho toàn bộ sản phẩm.</p>
        </header>

        <c:if test="${param.status == 'created' || param.status == 'updated' || param.status == 'deactivated'}">
            <div class="notice success" role="status">
                <c:choose>
                    <c:when test="${param.status == 'created'}">Đã tạo voucher.</c:when>
                    <c:when test="${param.status == 'updated'}">Đã cập nhật voucher.</c:when>
                    <c:otherwise>Voucher đã được ngừng kích hoạt.</c:otherwise>
                </c:choose>
            </div>
        </c:if>
        <c:if test="${param.error == 'invalid'}"><div class="notice warning">Dữ liệu voucher không hợp lệ. Vui lòng kiểm tra các trường bắt buộc, giới hạn sử dụng và mức giảm.</div></c:if>
        <c:if test="${param.error == 'date'}"><div class="notice warning">Thời gian kết thúc phải sau thời gian bắt đầu.</div></c:if>
        <c:if test="${param.error == 'duplicate'}"><div class="notice warning">Mã voucher này đã tồn tại. Hãy chọn mã khác.</div></c:if>

        <section class="admin-card">
            <h2>Tạo voucher</h2>
            <form class="editor-form voucher-editor" action="${pageContext.request.contextPath}/admin/vouchers" method="post">
                <input type="hidden" name="action" value="create">
                <div class="grid-form">
                    <label>Mã voucher
                        <input name="code" maxlength="50" pattern="[A-Za-z0-9_-]+" placeholder="VD: PKCN20" required>
                    </label>
                    <label>Tên chương trình <input name="name" maxlength="100" required></label>
                    <label>Mô tả <input name="description" maxlength="191"></label>
                    <label>Loại giảm giá
                        <select name="discount_type" required>
                            <option value="PERCENT">Phần trăm</option>
                            <option value="FIXED">Số tiền cố định</option>
                            <option value="FREESHIP">Miễn phí vận chuyển</option>
                        </select>
                    </label>
                    <label>Mức giảm (% hoặc số tiền)
                        <input name="discount_value" type="number" min="0" step="0.01" value="0" required>
                    </label>
                    <label>Giảm tối đa (không bắt buộc)
                        <input name="max_discount_amount" type="number" min="0" step="1">
                    </label>
                    <label>Giá trị đơn tối thiểu
                        <input name="min_order_value" type="number" min="0" step="1" value="0" required>
                        <small class="field-help">Tính trên tổng tiền sản phẩm trong giỏ, trước phí vận chuyển.</small>
                    </label>
                    <label>Tổng lượt dùng (để trống = không giới hạn)
                        <input name="usage_limit" type="number" min="1" step="1">
                    </label>
                    <label>Giới hạn mỗi khách hàng
                        <input name="usage_limit_per_user" type="number" min="1" step="1" value="1" required>
                    </label>
                    <label>Bắt đầu <input name="start_date" type="datetime-local" required></label>
                    <label>Kết thúc <input name="end_date" type="datetime-local" required></label>
                </div>
                <fieldset class="target-picker">
                    <legend>Danh mục áp dụng</legend>
                    <p>Để trống cả danh mục và sản phẩm nếu muốn áp dụng cho toàn bộ sản phẩm.</p>
                    <div class="target-options">
                        <c:forEach var="category" items="${categories}">
                            <label class="target-option"><input type="checkbox" name="categoryIds" value="${category.id}">
                                <c:out value="${category.category_name}"/>
                            </label>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset class="target-picker">
                    <legend>Sản phẩm áp dụng</legend>
                    <p>Nếu chọn cả danh mục và sản phẩm, voucher áp dụng khi sản phẩm thuộc một trong hai nhóm.</p>
                    <div class="target-options">
                        <c:forEach var="product" items="${products}">
                            <label class="target-option"><input type="checkbox" name="productIds" value="${product.id}">
                                <c:out value="${product.name}"/> <c:if test="${not product.status}">(ngừng bán)</c:if>
                            </label>
                        </c:forEach>
                    </div>
                </fieldset>
                <label class="check-label"><input type="checkbox" name="status" value="1" checked> Kích hoạt voucher</label>
                <button class="button primary" type="submit">Tạo voucher</button>
            </form>
        </section>

        <section class="admin-card">
            <div class="section-heading"><h2>Danh sách voucher</h2><span>${fn:length(vouchers)} voucher</span></div>
            <c:choose>
                <c:when test="${empty vouchers}"><p class="empty-state">Chưa có voucher nào.</p></c:when>
                <c:otherwise>
                    <div class="record-list">
                        <c:forEach var="voucher" items="${vouchers}">
                            <article class="record">
                                <div class="record-summary">
                                    <div>
                                        <h3><c:out value="${voucher.name}"/>
                                            <c:choose>
                                                <c:when test="${voucher.status}"><span class="badge">Đang bật</span></c:when>
                                                <c:otherwise><span class="badge muted">Đã tắt</span></c:otherwise>
                                            </c:choose>
                                        </h3>
                                        <p>Mã <strong><c:out value="${voucher.code}"/></strong>
                                            · <c:out value="${voucher.discount_type}"/>
                                            · Đã dùng ${voucher.used_count}
                                            <c:if test="${not empty voucher.usage_limit}"> / ${voucher.usage_limit}</c:if>
                                        </p>
                                        <small><fmt:formatDate value="${voucher.start_date}" pattern="dd/MM/yyyy HH:mm"/>
                                            – <fmt:formatDate value="${voucher.end_date}" pattern="dd/MM/yyyy HH:mm"/>
                                        </small>
                                    </div>
                                    <details class="record-edit">
                                        <summary class="button secondary">Chỉnh sửa</summary>
                                        <form class="editor-form voucher-editor compact" action="${pageContext.request.contextPath}/admin/vouchers" method="post">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="id" value="${voucher.id}">
                                            <div class="grid-form">
                                                <label>Mã voucher <input name="code" maxlength="50" pattern="[A-Za-z0-9_-]+" value="${fn:escapeXml(voucher.code)}" required></label>
                                                <label>Tên chương trình <input name="name" maxlength="100" value="${fn:escapeXml(voucher.name)}" required></label>
                                                <label>Mô tả <input name="description" maxlength="191" value="${fn:escapeXml(voucher.description)}"></label>
                                                <label>Loại giảm giá
                                                    <select name="discount_type" required>
                                                        <option value="PERCENT" ${voucher.discount_type == 'PERCENT' ? 'selected' : ''}>Phần trăm</option>
                                                        <option value="FIXED" ${voucher.discount_type == 'FIXED' ? 'selected' : ''}>Số tiền cố định</option>
                                                        <option value="FREESHIP" ${voucher.discount_type == 'FREESHIP' ? 'selected' : ''}>Miễn phí vận chuyển</option>
                                                    </select>
                                                </label>
                                                <label>Mức giảm <input name="discount_value" type="number" min="0" step="0.01" value="${voucher.discount_value}" required></label>
                                                <label>Giảm tối đa <input name="max_discount_amount" type="number" min="0" step="1" value="${voucher.max_discount_amount}"></label>
                                                <label>Giá trị đơn tối thiểu <input name="min_order_value" type="number" min="0" step="1" value="${voucher.min_order_value}" required></label>
                                                <label>Tổng lượt dùng <input name="usage_limit" type="number" min="1" step="1" value="${voucher.usage_limit}"></label>
                                                <label>Giới hạn mỗi khách hàng <input name="usage_limit_per_user" type="number" min="1" step="1" value="${voucher.usage_limit_per_user}" required></label>
                                                <label>Bắt đầu <input name="start_date" type="datetime-local" value="${voucher.start_date_input}" required></label>
                                                <label>Kết thúc <input name="end_date" type="datetime-local" value="${voucher.end_date_input}" required></label>
                                            </div>
                                            <fieldset class="target-picker">
                                                <legend>Danh mục áp dụng (OR với sản phẩm)</legend>
                                                <div class="target-options">
                                                    <c:forEach var="category" items="${categories}">
                                                        <label class="target-option">
                                                            <input type="checkbox" name="categoryIds" value="${category.id}"
                                                                   ${voucher.category_ids.contains(category.id) ? 'checked' : ''}>
                                                            <c:out value="${category.category_name}"/>
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </fieldset>
                                            <fieldset class="target-picker">
                                                <legend>Sản phẩm áp dụng (OR với danh mục)</legend>
                                                <div class="target-options">
                                                    <c:forEach var="product" items="${products}">
                                                        <label class="target-option">
                                                            <input type="checkbox" name="productIds" value="${product.id}"
                                                                   ${voucher.product_ids.contains(product.id) ? 'checked' : ''}>
                                                            <c:out value="${product.name}"/> <c:if test="${not product.status}">(ngừng bán)</c:if>
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </fieldset>
                                            <label class="check-label"><input type="checkbox" name="status" value="1" ${voucher.status ? 'checked' : ''}> Kích hoạt voucher</label>
                                            <button class="button primary" type="submit">Lưu thay đổi</button>
                                        </form>
                                    </details>
                                    <c:if test="${voucher.status}">
                                        <form action="${pageContext.request.contextPath}/admin/vouchers" method="post" data-confirm="Ngừng kích hoạt voucher này?">
                                            <input type="hidden" name="action" value="deactivate">
                                            <input type="hidden" name="id" value="${voucher.id}">
                                            <button class="button danger" type="submit">Ngừng dùng</button>
                                        </form>
                                    </c:if>
                                </div>
                            </article>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin_content.js" defer></script>
</body>
</html>
