<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.variants}">
    <tr>
        <td colspan="6">Không thấy biến thể</td>
    </tr>
</c:if>
<c:if test="${not empty requestScope.variants}">
    <c:forEach var="v" items="${requestScope.variants}">
        <tr class="product-variant-item">
            <td><img class="img-product-var" src="${v.urlImage}" width="50"></td>
            <td>${v.id}</td>
            <td>${v.sku}</td>
            <td>${v.name}</td>
            <td>${v.priceByFormat}</td>
            <td>${v.stock}</td>
            <td>${v.specific}</td>
            <td>
         <span class="edit-product">
                <span class="edit-product-var-remove" data-id="${v.id}"><i class="fa-solid fa-circle-minus"></i></span>
                <span class="edit-product-var-update" data-product-id="${v.productId}" data-id="${v.id}"><i class="fa-solid fa-pen-to-square"></i></span>
            </span>
            </td>
        </tr>
    </c:forEach>
</c:if>