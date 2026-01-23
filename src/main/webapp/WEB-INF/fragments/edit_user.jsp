<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty requestScope.user}">
    <div class="popup-content">
        <h2>Không thể tải được sản phẩm</h2>
    </div>
</c:if>

<c:if test="${not empty requestScope.user}">
    <div class="popup-content">
        <h2>Chỉnh sửa thông tin người dùng</h2>
        <form id="editUserForm" action="" method="post">
            <input type="hidden" id="edit-user-id" name="userId" value="${requestScope.user.id}">

            <label for="name-user">Tên người dùng:</label>
            <input type="text" id="name-user" name="name" class="form-input" value="${requestScope.user.fullName}">
            <br>
            <label for="user-name">User Name:</label>
            <input type="text" id="user-name" name="userName" class="form-input" readonly style="background: #eee;"
                   value="${requestScope.user.userName}">
            <br>
            <label for="email">Email:</label>
            <input type="text" id="email-edit" name="email" class="form-input" readonly style="background: #eee;"
                   value="${requestScope.user.email}">
            <br>
            <label>Ảnh cũ</label>
            <img src="${requestScope.user.avatar}">
            <br>
            <br>
            <label for="file-avatar">Đổi Avatar:</label>
            <input type="file" id="fileEditUser" name="fileAvatar" class="form-input" accept="image/*">
            <div id="editUserPreview"></div>

            <label for="role-edit">Vai trò:</label>
            <c:if test="${requestScope.user.role == 2}">
                <select name="role" id="role-edit" class="form-input">
                    <option value="1" ${requestScope.user.role == 1?'selected':''}>Admin</option>
                    <option value="2" ${requestScope.user.role == 2?'selected':''}>Customer</option>
                </select>
            </c:if>
            <c:if test="${requestScope.user.role == 1 && sessionScope.user.id == requestScope.user.id}">
                <select name="role" id="role-edit" disabled class="form-input">
                    <option value="1" selected>Admin</option>
                </select>
            </c:if>
            <br>

            <div class="popup-actions">
                <button type="submit" id="submitEditUser" class="submit">Cập nhật</button>
                <button type="button" id="closeEditUser" class="close">Đóng</button>
            </div>
        </form>
    </div>
</c:if>