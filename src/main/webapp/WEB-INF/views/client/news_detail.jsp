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
          href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/news_detail.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/shared/sidebar.css">
</head>

<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<main>
    <div class="news-detail-container">

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

            <!-- Bình luận -->
            <div class="comment-section">
                <h3>Để lại một bình luận</h3>

                <p class="comment-note">
                    Email của bạn sẽ không được hiển thị công khai.
                    Các trường bắt buộc được đánh dấu
                    <span class="required">*</span>
                </p>

                <form class="comment-form"
                      action="${pageContext.request.contextPath}/comment"
                      method="post">

                    <input type="hidden"
                           name="articleId"
                           value="${article.id}"/>

                    <div class="form-group">
                        <label for="comment">Nhập vào đây...</label>
                        <textarea id="comment"
                                  name="content"
                                  rows="6"
                                  placeholder="Viết bình luận của bạn..."
                                  required></textarea>
                    </div>

                    <div class="form-row">

                        <div class="form-group">
                            <label for="name">
                                Tên <span class="required">*</span>
                            </label>
                            <input type="text"
                                   id="name"
                                   name="name"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="email">
                                Email <span class="required">*</span>
                            </label>
                            <input type="email"
                                   id="email"
                                   name="email"
                                   required>
                        </div>

                    </div>

                    <button type="submit" class="btn-submit">
                        Gửi bình luận
                    </button>

                </form>
            </div>

        </c:if>

    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
