<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý tài khoản ngân hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_content.css">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <main class="main-content-admin content-admin">
        <header class="page-heading">
            <div><span class="eyebrow">THANH TOÁN</span><h1>Quản lý tài khoản ngân hàng</h1></div>
            <p>Quản lý thông tin tài khoản được hiển thị theo phương thức thanh toán.</p>
        </header>
        <c:if test="${param.status == 'created' || param.status == 'updated' || param.status == 'deleted'}">
            <div class="notice success" role="status">
                <c:choose>
                    <c:when test="${param.status == 'created'}">Đã thêm tài khoản ngân hàng.</c:when>
                    <c:when test="${param.status == 'updated'}">Đã cập nhật tài khoản ngân hàng.</c:when>
                    <c:otherwise>Đã xóa tài khoản ngân hàng.</c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <section class="admin-card">
            <h2>Thêm tài khoản ngân hàng</h2>
            <c:if test="${empty paymentMethods}"><p class="notice warning">Cần tạo phương thức thanh toán trước khi thêm tài khoản ngân hàng.</p></c:if>
            <form class="editor-form grid-form" action="${pageContext.request.contextPath}/admin/bank-info" method="post">
                <input type="hidden" name="action" value="create">
                <label>Phương thức thanh toán
                    <select name="payment_method_id" required>
                        <option value="">Chọn phương thức</option>
                        <c:forEach var="method" items="${paymentMethods}">
                            <option value="${method.id}">${fn:escapeXml(method.name_method)}</option>
                        </c:forEach>
                    </select>
                </label>
                <label>Tên ngân hàng <input name="bank_name" maxlength="100" required></label>
                <label>Chủ tài khoản <input name="bank_owner_name" maxlength="100" required></label>
                <label>Số tài khoản <input name="bank_account_number" maxlength="50" required></label>
                <label class="check-label"><input type="checkbox" name="is_show" value="1" checked> Hiển thị cho khách hàng</label>
                <button class="button primary" type="submit" ${empty paymentMethods ? 'disabled' : ''}>Thêm tài khoản</button>
            </form>
        </section>

        <section class="admin-card">
            <div class="section-heading">
                <h2>Danh sách tài khoản</h2>
                <span data-testid="search-results-count">${fn:length(banks)} tài khoản
                    <c:if test="${not empty searchQuery}"> · kết quả cho “${fn:escapeXml(searchQuery)}”</c:if>
                </span>
            </div>
            <form class="record-search" action="${pageContext.request.contextPath}/admin/bank-info" method="get">
                <label for="bank-search">Tìm tài khoản</label>
                <div class="record-search-controls">
                    <input id="bank-search" type="search" name="q" value="${fn:escapeXml(searchQuery)}"
                           placeholder="Tìm theo mã, ngân hàng, chủ tài khoản, số tài khoản">
                    <button class="button primary" type="submit">Tìm kiếm</button>
                    <c:if test="${not empty searchQuery}">
                        <a class="button secondary" href="${pageContext.request.contextPath}/admin/bank-info">Xóa tìm kiếm</a>
                    </c:if>
                </div>
            </form>
            <c:choose>
                <c:when test="${empty banks}">
                    <p class="empty-state" data-testid="search-empty-state">
                        <c:choose>
                            <c:when test="${not empty searchQuery}">Không tìm thấy tài khoản ngân hàng phù hợp với “${fn:escapeXml(searchQuery)}”.</c:when>
                            <c:otherwise>Chưa có tài khoản ngân hàng nào.</c:otherwise>
                        </c:choose>
                    </p>
                </c:when>
                <c:otherwise>
                    <div class="record-list">
                        <c:forEach var="bank" items="${banks}">
                            <article class="record">
                                <div class="record-summary">
                                    <div><h3>${fn:escapeXml(bank.bank_name)}
                                        <c:if test="${bank.is_show}"><span class="badge">Đang hiển thị</span></c:if>
                                        <c:if test="${not bank.is_show}"><span class="badge muted">Đang ẩn</span></c:if>
                                    </h3><p>${fn:escapeXml(bank.bank_owner_name)} · ${fn:escapeXml(bank.bank_account_number)}</p>
                                        <small>Mã #${bank.id} · ${fn:escapeXml(bank.name_method)}</small>
                                    </div>
                                    <details class="record-edit">
                                        <summary class="button secondary">Chỉnh sửa</summary>
                                        <form class="editor-form grid-form compact" action="${pageContext.request.contextPath}/admin/bank-info" method="post">
                                            <input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${bank.id}">
                                            <label>Phương thức thanh toán
                                                <select name="payment_method_id" required>
                                                    <c:forEach var="method" items="${paymentMethods}">
                                                        <option value="${method.id}" ${bank.payment_method_id == method.id ? 'selected' : ''}>${fn:escapeXml(method.name_method)}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                            <label>Tên ngân hàng <input name="bank_name" maxlength="100" value="${fn:escapeXml(bank.bank_name)}" required></label>
                                            <label>Chủ tài khoản <input name="bank_owner_name" maxlength="100" value="${fn:escapeXml(bank.bank_owner_name)}" required></label>
                                            <label>Số tài khoản <input name="bank_account_number" maxlength="50" value="${fn:escapeXml(bank.bank_account_number)}" required></label>
                                            <label class="check-label"><input type="checkbox" name="is_show" value="1" ${bank.is_show ? 'checked' : ''}> Hiển thị cho khách hàng</label>
                                            <button class="button primary" type="submit">Lưu thay đổi</button>
                                        </form>
                                    </details>
                                    <form action="${pageContext.request.contextPath}/admin/bank-info" method="post" data-confirm="Xóa thông tin tài khoản ngân hàng này?">
                                        <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${bank.id}">
                                        <input type="hidden" name="q" value="${fn:escapeXml(searchQuery)}">
                                        <button class="button danger" type="submit">Xóa</button>
                                    </form>
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
