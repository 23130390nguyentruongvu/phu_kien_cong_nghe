<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý bình luận bài viết</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_content.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_article_comments.css">
</head>
<body>
<div class="wrap-all-content">
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <main class="main-content-admin content-admin">
        <header class="page-heading">
            <div><span class="eyebrow">NỘI DUNG</span><h1>Quản lý bình luận bài viết</h1></div>
            <p>Duyệt, ẩn hoặc xóa bình luận trên các bài viết.</p>
        </header>
        <p><a class="button secondary" href="${pageContext.request.contextPath}/admin/articles">Quay lại quản lý bài viết</a></p>
        <c:if test="${not empty articleFlashMessage}">
            <div class="notice ${articleFlashType == 'error' ? 'error' : 'success'}" role="status"
                 data-testid="article-comment-toast"><c:out value="${articleFlashMessage}"/></div>
        </c:if>
        <section class="admin-card">
            <form class="record-search" action="${pageContext.request.contextPath}/admin/article-comment" method="get">
                <label for="article-filter">Lọc theo bài viết</label>
                <div class="record-search-controls">
                    <select id="article-filter" name="articleId">
                        <option value="">Tất cả bài viết</option>
                        <c:forEach var="article" items="${articles}">
                            <option value="${article.id}" ${selectedArticleId == article.id ? 'selected' : ''}>
                                #${article.id} - <c:out value="${article.title}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <button class="button primary" type="submit">Lọc</button>
                </div>
            </form>
            <c:choose>
                <c:when test="${empty comments}">
                    <p class="empty-state">Không có bình luận nào cho bộ lọc này.</p>
                </c:when>
                <c:otherwise>
                    <div class="record-list" id="admin-article-comments">
                        <c:forEach var="comment" items="${comments}">
                            <article class="record">
                                <div class="record-summary">
                                    <div>
                                        <h3><c:out value="${comment.name_display}"/> ·
                                            <c:choose>
                                                <c:when test="${comment.status == 'approved'}">Đã duyệt</c:when>
                                                <c:when test="${comment.status == 'hidden'}">Đã ẩn</c:when>
                                                <c:otherwise>Chờ duyệt</c:otherwise>
                                            </c:choose>
                                        </h3>
                                        <p><c:out value="${comment.content}"/></p>
                                        <small>
                                            Bài viết #${comment.article_id}:
                                            <c:out value="${comment.article_title}"/> ·
                                            <c:out value="${comment.email}"/> ·
                                            <c:out value="${comment.post_date}"/>
                                        </small>
                                    </div>
                                    <div class="article-comment-actions">
                                        <c:if test="${comment.status != 'approved'}">
                                            <form action="${pageContext.request.contextPath}/admin/article-comment" method="post">
                                                <input type="hidden" name="action" value="approve">
                                                <input type="hidden" name="commentId" value="${comment.id}">
                                                <input type="hidden" name="articleId" value="${selectedArticleId}">
                                                <button class="button primary" type="submit">Duyệt</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${comment.status != 'hidden'}">
                                            <form action="${pageContext.request.contextPath}/admin/article-comment" method="post">
                                                <input type="hidden" name="action" value="hide">
                                                <input type="hidden" name="commentId" value="${comment.id}">
                                                <input type="hidden" name="articleId" value="${selectedArticleId}">
                                                <button class="button secondary" type="submit">Ẩn</button>
                                            </form>
                                        </c:if>
                                        <form action="${pageContext.request.contextPath}/admin/article-comment" method="post"
                                              data-confirm="Bạn có chắc muốn xóa bình luận này?">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="commentId" value="${comment.id}">
                                            <input type="hidden" name="articleId" value="${selectedArticleId}">
                                            <button class="button danger" type="submit">Xóa</button>
                                        </form>
                                    </div>
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
