<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tin Tức</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/news.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/sidebar.css">

</head>
<body>
<div class="container-header-main-footer">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main>
        <div class="container">
            <jsp:include page="/WEB-INF/views/common/sidebar_category_page.jsp"/>

            <div class="content">
                <section class="news-section">
                    <h1>Tin Tức</h1>

                    <!-- Trường hợp chưa có bài viết -->
                    <c:if test="${empty requestScope.ArticleItems}">
                        <p style="text-align: center">Chưa có bài viết nào.</p>
                    </c:if>

                    <!-- Trường hợp có bài viết -->
                    <c:if test="${not empty requestScope.ArticleItems}">
                        <div class="news-grid">

                            <c:forEach var="article" items="${requestScope.ArticleItems}">
                                <article class="news-card">

                                    <h2>${article.title}</h2>

                                    <p class="meta">
                                        Bởi admin | ${article.postDateFormat}
                                    </p>

                                    <p>
                                            ${article.subDescription}
                                    </p>

                                    <a href="${pageContext.request.contextPath}/news-detail?id=${article.id}"
                                       class="read-more">
                                        Đọc bài viết →
                                    </a>

                                </article>
                            </c:forEach>
                        </div>
                    </c:if>
                </section>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>
