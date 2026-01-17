<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="sidebar-product">

    <aside class="widget widget_search">
        <h3>DANH MỤC SẢN PHẨM</h3>
        <div class="search-box">
            <input type="search" placeholder="Tìm sản phẩm…" />
            <button><i class="fa-solid fa-magnifying-glass"></i></button>
        </div>
    </aside>

    <aside class="widget widget_product_categories">
        <ul class="product-categories">

            <c:forEach var="parent" items="${applicationScope.ParentCategories}">
                <li class="category">

                    <a
                            href="${pageContext.request.contextPath}/product-category?id=${parent.id}"
                            class="category-title"
                    >
                            ${parent.nameCategory}
                    </a>

                    <c:if test="${not empty applicationScope.SubCategoryMap[parent.id]}">
                        <ul class="sub-category">
                            <c:forEach var="child"
                                       items="${applicationScope.SubCategoryMap[parent.id]}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/product-category?id=${child.id}">
                                            ${child.nameCategory}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>

                </li>
            </c:forEach>

        </ul>
    </aside>

</div>
