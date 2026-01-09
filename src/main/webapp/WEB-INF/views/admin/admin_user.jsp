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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
</head>
<body>
    <div class="wrap-all-content">
        <!--    open nav admin-->
        <div class="nav-admin">
            <div class="wrap-base-info-admin">
                <div class="wrap-img-user-admin">
                    <span class="img-of-admin"><img src="assets/image/customer/customer_2.webp" alt=""></span>
                    <span class="username-role-of-admin">
                    <span class="username">Admin123</span>

                    <div class="role">Quản trị viên</div>
                </span>
                </div>
                <div class="title"><h2><i class="fa-solid fa-crown"></i> Trang quản lí</h2></div>
            </div>
            <hr>
            <div class="wrap-nav-link-admin">
                <ul>
                    <li class="item-nav-link-admin ">
                        <i class="fa-solid fa-eye"></i> <a href="admin_overview.jsp">Tổng quan</a>
                    </li>
                    <li class="item-nav-link-admin selected">
                        <i class="fa-solid fa-user-group"></i> <a href="admin_user.html">Quản lí người dùng</a>
                    </li>
                    <li class="item-nav-link-admin">
                        <i class="fa-solid fa-box"></i> <a href="admin_product.jsp">Quản lí sản phẩm</a>
                    </li>
                </ul>
            </div>
        </div>
        <!--    close nav admin-->
        <!--    open main content admin-->
        <div class="main-content-admin">
            <h1 class="title-for-page">Quản lý người dùng</h1>
            <div class="wrap-find-info-user">
                <form>
                    <input type="text" name="user-id" class="input-user-id" placeholder="Tìm kiếm theo tên người dùng">
                    <select name="filter-by" class="filter-by">
                        <option value="all" selected>Tất cả</option>
                        <option value="pkmt">Đang hoạt động</option>
                        <option value="pkdt">Không còn hoạt động</option>
                    </select>
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
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>
                                        <img class="avatar-img" src="${user.avatar != null ? user.avatar : 'assets/image/logo.webp'}" alt="Avatar">
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
                                        <c:choose>
                                            <c:when test="${user.status == 'active'}">
                                                <a href="manage-user?action=lock&id=${user.id}" onclick="return confirm('Khóa người dùng?')">
                                                    <span class="edit-user-lock"><i class="fa-solid fa-lock"></i></span>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="manage-user?action=unlock&id=${user.id}">
                                                    <span class="edit-user-unlock"><i class="fa-solid fa-unlock" style="color: orange;"></i></span>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="manage-user?action=delete&id=${user.id}" onclick="return confirm('Xóa người dùng?')">
                                            <span class="edit-user-remove"><i class="fa-solid fa-circle-minus"></i></span>
                                        </a>
                                        <span class="edit-user-update" onclick="openPopupEditUser(${user.id})">
                                             <i class="fa-solid fa-pen-to-square"></i>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="wrap-load-more">
                            <input type="button" class="load-more" name="change-status" value="Tải thêm">
                        </div>
                    </form>
                </div>
            </div>
<!--        <div id="popup-confirm" style="z-index: 1001" class="popup">-->
<!--            <div class="popup-content">-->
<!--                <h2>Xác nhận hành động</h2>-->
<!--                <p id="confirmMessage"></p>-->
<!--                <form id="confirmForm" method="post">-->
<!--                    <input type="hidden" id="confirmId" name="id">-->
<!--                    <button type="submit" id="confirmYes">Đồng ý</button>-->
<!--                    <button type="button" id="confirmNo">Hủy</button>-->
<!--                </form>-->
<!--            </div>-->
<!--        </div>-->

        <div id="popup-add-user" class="popup">
            <div class="popup-content">
                <h2>Thêm User mới</h2>
                <form action="manage-user" method="post">
                    <input type="text" name="name" class="form-input" placeholder="Tên người dùng">
                    <br>
                    <input type="text" name="userName" class="form-input" placeholder="Username">
                    <br>
                    <input type="email" name="email" class="form-input" placeholder="Email">
                    <br>
                    <input type="password" name="password" class="form-input" placeholder="Password">
                    <br>
                    <input type="password" name="re-password" class="form-input" placeholder="Nhập lại Password">
                    <br>
                    <input type="text" name="avatar" class="form-input" placeholder="Url hình ảnh">
                    <br>
                    <select name="role" class="form-input">
                        <option value="#" disabled selected>Chọn vai trò</option>
                        <option value="1" ${user.role == 1 ? 'selected' : ''}>Admin</option>
                        <option value="2" ${user.role == 2 ? 'selected' : ''}>Customer</option>
                    </select>
                    <div class="wrap-button-cancel-submit">
                        <button id="submitAddProd" type="submit" class="submit">Thêm</button>
                        <button id="closeAddProd" type="button" class="close">Đóng</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="popup-view-user" class="popup">
            <div class="popup-content">
                <h2>Chi tiết user</h2>
                <div class="board-res-user">
                    <table class="table-prod-variants">
                        <thead>
                        <tr>
                            <th>Mã người dùng</th>
                            <th>Avatar</th>
                            <th>Tên người dùng</th>
                            <th>User Name</th>
                            <th>Email</th>
                            <th>Mật khẩu</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                               1
                            </td>
                            <td><img src="assets/image/customer/customer_1.webp"></td>
                            <td>Nguyễn Trường Vũ</td>
                            <td>Vucun</td>
                            <td>Vu123@gmail.com</td>
                            <td>123</td>
                            <td>đang hoạt động</td>
                            <td>
                            <span class="edit-user">
                                <span class="edit-user-lock"><i class="fa-solid fa-lock"></i></span>
                                <span class="edit-user-unlock"><i class="fa-solid fa-unlock"></i></span>
                                <span class="edit-user-remove"><i class="fa-solid fa-circle-minus"></i></span>
                                <span class="edit-user-update"><i class="fa-solid fa-pen-to-square"></i></span>
                            </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <button id="closeUserView">Đóng</button>
            </div>
        </div>

        <div id="popup-update-user" class="popup">
            <div class="popup-content">
                <h2>Chỉnh sửa thông tin người dùng</h2>
                <form id="editUserForm" action="manage-user" method="post">
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

</body>
<script src="${pageContext.request.contextPath}/js/admin_user.js"></script>
</html>