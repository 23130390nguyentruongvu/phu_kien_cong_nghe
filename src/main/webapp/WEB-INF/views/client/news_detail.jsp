<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty article}">
                ${article.title}
            </c:when>
            <c:otherwise>
                Chi tiết tin tức
            </c:otherwise>
        </c:choose>
    </title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"/>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/shared/main.css?v=2">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/news_detail.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/article_comments.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/shared/sidebar.css">
</head>

<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<main>
    <div class="news-detail-container">
        <c:if test="${not empty articleFlashMessage}">
            <div class="article-toast ${articleFlashType == 'error' ? 'error' : 'success'}"
                 role="status" data-testid="article-toast"><c:out value="${articleFlashMessage}"/></div>
        </c:if>

        <!-- ===== TRƯỜNG HỢP KHÔNG CÓ BÀI VIẾT ===== -->
        <c:if test="${empty article}">
            <p style="text-align:center; padding:40px">
                Bài viết không tồn tại hoặc đã bị xóa.
            </p>
        </c:if>

        <!-- ===== TRƯỜNG HỢP CÓ BÀI VIẾT ===== -->
        <c:if test="${not empty article}">

            <!-- Thông tin tác giả & ngày đăng -->
            <div class="news-meta">
                <p>
                    <i class="fa-solid fa-user"></i>
                    Bởi <strong>Admin</strong>
                    &nbsp;|&nbsp;
                    <i class="fa-solid fa-calendar-days"></i>
                        ${article.postDateFormat}
                </p>
            </div>

            <div class="news-container">

                <div class="news-content">
                    <h1 class="news-title">
                            ${article.title}
                    </h1>

                    <p class="news-description">
                            ${article.subDescription}
                    </p>
                </div>

            </div>

            <!-- Nội dung bài viết -->
            <div class="news-content article-body">
                <!-- content cho phép HTML -->
                <c:out value="${article.content}" escapeXml="false"/>
            </div>

            <section class="comment-section" id="comments">
                <h3>Bình luận (${approvedCommentCount})</h3>
                <button type="button" id="toggle-article-comments" class="btn-submit"
                        aria-expanded="false" aria-controls="article-comment-list"
                        data-count="${approvedCommentCount}">
                    Xem bình luận (${approvedCommentCount})
                </button>
                <div id="article-comment-list" class="article-comment-list" hidden
                     data-comments-url="${pageContext.request.contextPath}/article-comments?articleId=${article.id}">
                    <p>Đang tải bình luận...</p>
                </div>

                <h3>Để lại một bình luận</h3>
                <p class="comment-note">Bình luận sẽ hiển thị sau khi được quản trị viên duyệt.</p>
                <form class="comment-form" action="${pageContext.request.contextPath}/article-comments"
                      method="post" novalidate>
                    <input type="hidden" name="articleId" value="${article.id}">
                    <div class="form-group">
                        <label for="article-comment-content">Nội dung <span class="required">*</span></label>
                        <textarea id="article-comment-content" name="content" rows="5"
                                  placeholder="Viết bình luận của bạn..." required></textarea>
                        <span class="article-field-error" data-error-for="content"
                              data-testid="article-comment-content-error" aria-live="polite"></span>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="article-comment-name">Tên hiển thị <span class="required">*</span></label>
                            <input id="article-comment-name" type="text" name="name"
                                   value="<c:out value='${commentName}'/>" required>
                            <span class="article-field-error" data-error-for="name"
                                  data-testid="article-comment-name-error" aria-live="polite"></span>
                        </div>
                        <div class="form-group">
                            <label for="article-comment-email">Email <span class="required">*</span></label>
                            <input id="article-comment-email" type="email" name="email"
                                   value="<c:out value='${commentEmail}'/>" required>
                            <span class="article-field-error" data-error-for="email"
                                  data-testid="article-comment-email-error" aria-live="polite"></span>
                        </div>
                    </div>
                    <button type="submit" class="btn-submit">Đăng bình luận</button>
                </form>
            </section>

        </c:if>

    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/article_comments.js" defer></script>
</body>
</html>
