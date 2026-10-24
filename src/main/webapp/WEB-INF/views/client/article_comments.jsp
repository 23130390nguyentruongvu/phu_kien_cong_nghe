<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:choose>
    <c:when test="${empty comments}">
        <p class="empty-comments">Chưa có bình luận nào được duyệt.</p>
    </c:when>
    <c:otherwise>
        <ul class="article-comment-items">
            <c:forEach var="comment" items="${comments}">
                <li class="article-comment-item">
                    <p class="article-comment-author"><c:out value="${comment.name_display}"/></p>
                    <p class="article-comment-content"><c:out value="${comment.content}"/></p>
                    <time><c:out value="${comment.post_date}"/></time>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>
