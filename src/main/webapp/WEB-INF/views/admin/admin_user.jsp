<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_user.css?v=1.1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
</head>
<body>
<div class="wrap-all-content">
    <!--    open nav admin-->
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <!--    close nav admin-->
    <!--    open main content admin-->
    <div class="main-content-admin">
        <h1 class="title-for-page">Quản lý người dùng</h1>
        <div class="wrap-find-info-user">
            <form action="search-user" method="get">
                <input type="text" name="searchName" class="input-user-id" placeholder="Tìm kiếm theo tên người dùng"
                       value="${param.searchName}">
                <button type="submit" class="submit-data">Submit</button>
            </form>
        </div>
        <div class="edit-user head-edit">
            <span class="edit-user-add-user"><i class="fa-solid fa-circle-plus"></i></span>
        </div>
        <div class="board-res-user">
            <form>
                <table>
                    <thead>
                    <tr>
                        <th>Mã người dùng</th>
                        <th>Avatar</th>
                        <th>Tên người dùng</th>
                        <th>User Name</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}" varStatus="loop">
                        <tr>
                            <td>${user.id}</td>
                            <td>
                                <img class="avatar-img"
                                     src="${user.avatar != null ? user.avatar : ''}" alt="Avatar">
                            </td>
                            <td>${user.fullName}</td>
                            <td>${user.userName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.status == 'active'}">
                                        <span style="color: green;">Đang hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red;">Đang bị khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${user.role == 2}">
                                    <c:choose>
                                        <c:when test="${user.status == 'active'}">
                                            <a href="${pageContext.request.contextPath}/lock-unlock-user?action=lock&id=${user.id}"
                                               onclick="return confirm('Đồng ý khóa người dùng này?')">
                                                <span class="edit-user-lock"><i class="fa-solid fa-lock"></i></span>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/lock-unlock-user?action=unlock&id=${user.id}"
                                               onclick="return confirm('Đồng ý mở khóa người dùng này?')">
                                                <span class="edit-user-unlock"><i class="fa-solid fa-unlock"
                                                                                  style="color: orange;"></i></span>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>

                                <c:if test="${user.role == 2}">
                                    <span class="edit-user-remove" data-id="${user.id}"
                                          data-username="${user.userName}"><i
                                            class="fa-solid fa-circle-minus"></i></span>

                                    <span class="edit-user-update" style="cursor: pointer;" data-id="${user.id}"
                                          data-username="${user.userName}">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                    </span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </div>

    <%--        TODO: Popup add user--%>
    <div id="popup-user" class="popup">
        <div class="popup-content">
            <h2 id="popup-title">Thêm User mới</h2>
            <form id="user-form" action="add-user" method="post" enctype="multipart/form-data">
                <input type="text" id="user-fullname" name="name" class="form-input" placeholder="Tên người dùng"
                       required>
                <br>
                <input type="text" id="user-username" name="userName" class="form-input" placeholder="Username"
                       required>
                <br>
                <input type="email" id="user-email" name="email" class="form-input" placeholder="Email" required>
                <br>
                <div id="password-group">
                    <input type="password" id="user-password" name="password" class="form-input" placeholder="Password">
                    <br>
                    <input type="password" id="user-repassword" name="re-password" class="form-input"
                           placeholder="Nhập lại Password">
                    <br>
                </div>

                <label for="user-file">Chọn Avatar:</label>
                <input type="file" id="addUserImage" name="avatar-file" class="form-input" accept="image/*">
                <br>
                <div id="addUserImagePreview"></div>
                <br>
                <select id="user-role" name="role" class="form-input">
                    <option value="1">Admin</option>
                    <option value="2">Customer</option>
                </select>

                <div class="wrap-button-cancel-submit">
                    <button id="submitAddUser" type="submit" class="submit">Xác nhận</button>
                    <button id="closeAddUser" type="button" class="close" onclick="closePopup()">Đóng</button>
                </div>
            </form>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const inputFile = document.getElementById("addUserImage");
            const previewDiv = document.getElementById("addUserImagePreview");

            inputFile.addEventListener("change", function () {
                previewDiv.innerHTML = ""; // xoá preview cũ

                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement("img");
                        img.src = e.target.result;
                        img.alt = "Avatar Preview";
                        img.style.width = "120px";
                        img.style.height = "120px";
                        img.style.objectFit = "cover";
                        img.style.marginTop = "10px";

                        previewDiv.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                }
            });
        });
    </script>

    <div id="popup-update-user" class="popup">
        <div class="popup-content">
            <h2>Chỉnh sửa thông tin người dùng</h2>
            <form id="editUserForm" action="${pageContext.request.contextPath}/edit-user" method="post">
                <input type="hidden" id="edit-id" name="id">

                <label for="name-user">Tên người dùng:</label>
                <input type="text" id="name-user" name="name" class="form-input">
                <br>
                <label for="user-name">User Name:</label>
                <input type="text" id="user-name" name="userName" class="form-input" readonly style="background: #eee;">
                <br>
                <label for="email">Email:</label>
                <input type="text" id="email-edit" name="email" class="form-input" readonly style="background: #eee;">
                <br>
                <label for="avatar-edit">Avatar URL:</label>
                <input type="text" id="avatar-edit" name="avatar" class="form-input">
                <br>
                <label for="file-avatar">Chọn Avatar:</label>
                <input type="file" id="file-edit" name="file-avatar" class="form-input" accept="image/*">
                <label for="role-edit">Vai trò:</label>
                <select name="role" id="role-edit" class="form-input">
                    <option value="1">Admin</option>
                    <option value="2">Customer</option>
                </select>
                <br>

                <div class="popup-actions">
                    <button type="submit" id="submit-update-user" class="submit">Cập nhật</button>
                    <button type="button" id="closeUpdate" class="close">Đóng</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="popup-confirm" style="z-index: 1001" class="popup">
    <div class="popup-content">
        <h2>Xác nhận hành động</h2>
        <p id="confirmMessage"></p>
        <form id="confirmForm" method="post">
            <input type="hidden" id="confirmId" name="id">
            <button type="submit" id="confirmYes">Đồng ý</button>
            <button type="button" id="confirmNo">Hủy</button>
        </form>
    </div>
</div>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script type="module" src="${pageContext.request.contextPath}/js/admin_user.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/admin_remove_user.js"></script>
</html>