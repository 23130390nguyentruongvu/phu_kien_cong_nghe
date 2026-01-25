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
<%--            <div class="comment-section">--%>
<%--                <h3>Để lại một bình luận</h3>--%>

<%--&lt;%&ndash;                <p class="comment-note">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    Email của bạn sẽ không được hiển thị công khai.&ndash;%&gt;--%>
<%--&lt;%&ndash;                    Các trường bắt buộc được đánh dấu&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <span class="required">*</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </p>&ndash;%&gt;--%>

<%--&lt;%&ndash;                <form class="comment-form"&ndash;%&gt;--%>
<%--&lt;%&ndash;                      action="${pageContext.request.contextPath}/comment"&ndash;%&gt;--%>
<%--&lt;%&ndash;                      method="post">&ndash;%&gt;--%>

<%--&lt;%&ndash;                    <input type="hidden"&ndash;%&gt;--%>
<%--&lt;%&ndash;                           name="articleId"&ndash;%&gt;--%>
<%--&lt;%&ndash;                           value="${article.id}"/>&ndash;%&gt;--%>

<%--&lt;%&ndash;                    <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <label for="comment">Nhập vào đây...</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <textarea id="comment"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                  name="content"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                  rows="6"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                  placeholder="Viết bình luận của bạn..."&ndash;%&gt;--%>
<%--&lt;%&ndash;                                  required></textarea>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;                    <div class="form-row">&ndash;%&gt;--%>

<%--&lt;%&ndash;                        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <label for="name">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                Tên <span class="required">*</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            </label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <input type="text"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   id="name"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   name="name"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   required>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;                        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <label for="email">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                Email <span class="required">*</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            </label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <input type="email"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   id="email"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   name="email"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                   required>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;                    </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;                    <button type="submit" class="btn-submit">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        Gửi bình luận&ndash;%&gt;--%>
<%--&lt;%&ndash;                    </button>&ndash;%&gt;--%>

<%--&lt;%&ndash;                </form>&ndash;%&gt;--%>
<%--            </div>--%>

        </c:if>

    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
