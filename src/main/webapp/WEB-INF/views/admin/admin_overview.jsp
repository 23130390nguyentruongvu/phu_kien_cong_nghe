<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chào Mừng Admin</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_overview.css">
</head>
<body>
<div class="wrap-all-content">
    <!--    open nav admin-->
    <jsp:include page="/WEB-INF/views/common/sidebar_admin.jsp"/>
    <!--    close nav admin-->
    <!--    open main content admin-->
    <div class="main-content-admin">
        <h1 class="title-for-page">Tổng quan</h1>
        <div class="wrap-row-item-dashboard">
            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Tổng người dùng</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">1,200</strong></span>
                <span class="wrap-icon-item-dashboard total-user"><i
                        class="fa-solid fa-users"></i></span>
                </span>
            </span>

            <span class="item-dashboard total-products">
                <p class="title-item-dashboard">Tổng sản phẩm bày bán</p>
                <span class="wrap-small-info">
                     <span class="wrap-quantity"><strong class="quantity">100</strong></span>
                <span class="wrap-icon-item-dashboard total-product"><i
                        class="fa-solid fa-boxes-stacked"></i></span></span>
            </span>

            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Đơn giao thành công</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">200</strong></span>
                <span class="wrap-icon-item-dashboard total-order-completed"><i class="fa-solid fa-check"></i></span>
                </span>
            </span>

            <span class="item-dashboard total-users">
                <p class="title-item-dashboard">Tổng doanh thu</p>
                <span class="wrap-small-info">
                    <span class="wrap-quantity"><strong class="quantity">đ125M</strong></span>
                <span class="wrap-icon-item-dashboard total-revenue">
                <i class="fa-solid fa-dollar-sign"></i>
                </span></span>
            </span>
        </div>
        <div class="wrap-order-recent-by-week">
            <div class="filter-by-week">
                <form>
                    <select name="filter-by-week" id="filter-by-week-select">
                        <option value="1-week">Lấy các đơn hàng 1 tuần trước</option>
                        <option value="2-week">Lấy các đơn hàng 2 tuần trước</option>
                        <option value="3-week">Lấy các đơn hàng 3 tuần trước</option>
                        <option value="4-week">Lấy các đơn hàng 4 tuần trước</option>
                    </select>
                </form>
            </div>
            <div class="board-res-filter-by-week">
                <table>
                    <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Id khách hàng</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái đơn</th>
                        <th>Ngày tạo đơn</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>100</td>
                        <td>2313</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>20/11/2025</td>
                    </tr>
                    <tr>
                        <td>101</td>
                        <td>2311</td>
                        <td>10.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order processing">Chờ xử lí</span></td>
                        <td>19/11/2025</td>
                    </tr>
                    <tr>
                        <td>200</td>
                        <td>1313</td>
                        <td>22.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order shipping">Đang giao</span></td>
                        <td>20/11/2025</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>1213</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order cancel">Đã hủy</span></td>
                        <td>19/11/2025</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>2121</td>
                        <td>21.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>100</td>
                        <td>23213</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>100</td>
                        <td>211313</td>
                        <td>24.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order cancel">Đã hủy</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>110</td>
                        <td>222</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>01</td>
                        <td>2</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                    <tr>
                        <td>100</td>
                        <td>2121</td>
                        <td>21.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>100</td>
                        <td>23213</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>100</td>
                        <td>211313</td>
                        <td>24.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order cancel">Đã hủy</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>110</td>
                        <td>222</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>                   <tr>
                        <td>01</td>
                        <td>2</td>
                        <td>2.200.000<span class="underline-dong">đ</span></td>
                        <td><span class="status-order completed">Đã giao</span></td>
                        <td>18/11/2025</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!--    close main content admin-->
</div>
</body>
</html>