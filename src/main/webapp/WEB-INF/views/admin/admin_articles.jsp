<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý bài viết</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_content.css">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <main class="main-content-admin content-admin">
        <header class="page-heading">
            <div><span class="eyebrow">NỘI DUNG</span><h1>Quản lý bài viết</h1></div>
            <p>Tạo mới, chỉnh sửa và gỡ bài viết hiển thị trên trang tin tức.</p>
        </header>
        <c:if test="${not empty articleFlashMessage}">
            <div class="notice article-notice ${articleFlashType == 'error' ? 'error' : 'success'}" role="status"
                 data-testid="article-toast">${fn:escapeXml(articleFlashMessage)}</div>
        </c:if>

        <section class="admin-card">
            <h2>Thêm bài viết</h2>
            <p><a class="button secondary" href="${pageContext.request.contextPath}/admin/article-comment">Quản lý bình luận bài viết</a></p>
            <form class="editor-form article-editor-form" action="${pageContext.request.contextPath}/admin/articles"
                  method="post" novalidate>
                <input type="hidden" name="action" value="create">
                <label>Tiêu đề <input name="title" required>
                    <span class="article-field-error" data-error-for="title"
                          data-testid="article-create-title-error" aria-live="polite"></span></label>
                <label>Mô tả ngắn <textarea name="subdescription" rows="3" required></textarea>
                    <span class="article-field-error" data-error-for="subdescription"
                          data-testid="article-create-description-error" aria-live="polite"></span></label>
                <label>Nội dung <textarea name="content" rows="7" required></textarea>
                    <span class="article-field-error" data-error-for="content"
                          data-testid="article-create-content-error" aria-live="polite"></span></label>
                <button class="button primary" type="submit">Đăng bài viết</button>
            </form>
        </section>

        <section class="admin-card">
            <div class="section-heading">
                <h2>Danh sách bài viết</h2>
                <span data-testid="search-results-count">${fn:length(articles)} bài viết
                    <c:if test="${not empty searchQuery}"> · kết quả cho “${fn:escapeXml(searchQuery)}”</c:if>
                </span>
            </div>
            <form class="record-search" action="${pageContext.request.contextPath}/admin/articles" method="get">
                <label for="article-search">Tìm bài viết</label>
                <div class="record-search-controls">
                    <input id="article-search" type="search" name="q" value="${fn:escapeXml(searchQuery)}"
                           placeholder="Tìm theo mã, tiêu đề, nội dung hoặc tác giả">
                    <button class="button primary" type="submit">Tìm kiếm</button>
                    <c:if test="${not empty searchQuery}">
                        <a class="button secondary" href="${pageContext.request.contextPath}/admin/articles">Xóa tìm kiếm</a>
                    </c:if>
                </div>
            </form>
            <c:choose>
                <c:when test="${empty articles}">
                    <p class="empty-state" data-testid="search-empty-state">
                        <c:choose>
                            <c:when test="${not empty searchQuery}">Không tìm thấy bài viết phù hợp với “${fn:escapeXml(searchQuery)}”.</c:when>
                            <c:otherwise>Chưa có bài viết nào.</c:otherwise>
                        </c:choose>
                    </p>
                </c:when>
                <c:otherwise>
                    <div class="record-list">
                        <c:forEach var="article" items="${articles}">
                            <article class="record">
                                <div class="record-summary">
                                    <div><h3>${fn:escapeXml(article.title)}</h3><p>${fn:escapeXml(article.subdescription)}</p>
                                        <small>Mã #${article.id} · Tác giả ${fn:escapeXml(article.author_name)} · ${fn:escapeXml(article.post_date)}</small>
                                    </div>
                                    <details class="record-edit">
                                        <summary class="button secondary">Chỉnh sửa</summary>
                                        <form class="editor-form compact article-editor-form"
                                              action="${pageContext.request.contextPath}/admin/articles"
                                              method="post" novalidate>
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="id" value="${article.id}">
                                            <label>Tiêu đề <input name="title" value="${fn:escapeXml(article.title)}" required>
                                                <span class="article-field-error" data-error-for="title"
                                                      data-testid="article-update-title-error-${article.id}" aria-live="polite"></span></label>
                                            <label>Mô tả ngắn <textarea name="subdescription" rows="3" required>${fn:escapeXml(article.subdescription)}</textarea>
                                                <span class="article-field-error" data-error-for="subdescription"
                                                      data-testid="article-update-description-error-${article.id}" aria-live="polite"></span></label>
                                            <label>Nội dung <textarea name="content" rows="7" required>${fn:escapeXml(article.content)}</textarea>
                                                <span class="article-field-error" data-error-for="content"
                                                      data-testid="article-update-content-error-${article.id}" aria-live="polite"></span></label>
                                            <button class="button primary" type="submit">Lưu thay đổi</button>
                                        </form>
                                    </details>
                                    <a class="button secondary article-comments-link"
                                       href="${pageContext.request.contextPath}/admin/article-comment?articleId=${article.id}"
                                       data-testid="manage-article-comments-${article.id}">
                                        Quản lý bình luận (${article.comment_count})
                                    </a>
                                    <form action="${pageContext.request.contextPath}/admin/articles" method="post" data-confirm="Xóa bài viết này? Các bình luận liên quan cũng sẽ bị xóa.">
                                        <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${article.id}">
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
<script src="${pageContext.request.contextPath}/js/admin_article_validation.js" defer></script>
</body>
</html>
