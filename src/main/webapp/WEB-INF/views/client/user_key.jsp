<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Khóa Số</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
            integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/shared/nav_account.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_key.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<main>
    <div class="wrap-content-all">
        <jsp:include page="/WEB-INF/views/common/sidebar_user.jsp"/>

        <div class="main-content">
            <nav class="breadcrumb">
                <a href="${pageContext.request.contextPath}/personal_info">Trang cá nhân</a>
                <span class="breadcrumb-sep">&gt;</span>
                <span class="breadcrumb-current">Quản lý khóa số</span>
            </nav>

            <div class="key-header">
                <div>
                    <h1 class="key-title">Khóa số cá nhân</h1>
                    <p class="key-subtitle">Quản lý khóa công khai dùng để xác thực và ký số đơn hàng</p>
                </div>
                <a href="${pageContext.request.contextPath}/download-tool" class="btn-download-tool">Tải Tool kí điện tử</a>
            </div>

            <div class="key-grid">
                <div class="key-register-card">
                    <h2 class="card-title">Đăng ký khóa mới</h2>

                    <form id="registerKeyForm" onsubmit="return registerKey(event)">
                        <div class="form-group">
                            <label class="form-label" for="keyName">Tên gợi nhớ <span class="required">*</span></label>
                            <input type="text" id="keyName" name="keyName" class="form-input"
                                   placeholder="VD: Laptop ASUS, PC nhà..." required>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Thuật toán <span class="required">*</span></label>
                            <div class="radio-group">
                                <label class="radio-label">
                                    <input type="radio" name="algorithm" value="RSA" checked class="radio-input">
                                    <span class="radio-text">RSA</span>
                                </label>
                                <label class="radio-label">
                                    <input type="radio" name="algorithm" value="DSA" class="radio-input">
                                    <span class="radio-text">DSA</span>
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="publicKey">Public Key (Base64) <span class="required">*</span></label>
                            <textarea id="publicKey" name="publicKey" rows="5" required
                                      placeholder="Dán chuỗi Public Key tại đây..."
                                      class="form-textarea"></textarea>
                        </div>

                        <div class="form-group">
                            <label class="file-upload-label" for="fileUpload">
                                <span class="file-upload-text">Chọn file khóa công khai (.pub / .txt)</span>
                                <input type="file" id="fileUpload" accept=".pub,.txt" class="file-upload-input">
                            </label>
                        </div>

                        <button type="submit" class="btn-submit-key">Kích hoạt &amp; Lưu</button>
                        <p class="form-note">Khóa mới sẽ thay thế khóa cũ nếu đã tồn tại.</p>
                    </form>
                </div>

                <div class="key-history-card">
                    <h2 class="card-title">Lịch sử khóa đã dùng</h2>

                    <div class="table-wrapper">
                        <table class="key-table">
                            <thead>
                            <tr>
                                <th>Tên gợi nhớ</th>
                                <th>Thuật toán</th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th>
                                <th class="text-center">Thao tác</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="key-row">
                                <td class="key-name-cell">
                                    <span class="key-display-name">Laptop ASUS - Làm việc</span>
                                    <span class="key-id">ID: pub_83492...</span>
                                </td>
                                <td><span class="algo-badge algo-rsa">RSA-3072</span></td>
                                <td class="key-date">13/06/2026<br>15:30:12</td>
                                <td>
                                    <span class="status-badge status-active">
                                        <span class="status-indicator status-indicator-active"></span>
                                        Đang hoạt động
                                    </span>
                                </td>
                                <td class="text-center">
                                    <button onclick="openRevokeModal()" class="btn-revoke">Thu hồi</button>
                                </td>
                            </tr>
                            <tr class="key-row key-row-revoked">
                                <td class="key-name-cell">
                                    <span class="key-display-name key-display-name-revoked">PC Đồ Án Học Tập (Cũ)</span>
                                    <span class="key-id">ID: pub_19482...</span>
                                </td>
                                <td><span class="algo-badge algo-dsa">DSA-2048</span></td>
                                <td class="key-date">10/05/2026<br>09:15:44</td>
                                <td>
                                    <span class="status-badge status-inactive">
                                        <span class="status-indicator status-indicator-inactive"></span>
                                        Đã hủy bỏ
                                    </span>
                                </td>
                                <td class="text-center key-revoked-note">Đã hủy 13/06/2026</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div id="revokeModal" class="modal-overlay hidden">
    <div class="modal-container">
        <div class="modal-header">
            <h3 class="modal-title">Xác nhận thu hồi khóa</h3>
        </div>
        <div class="modal-body">
            <p>Bạn sắp thu hồi khóa: <strong class="modal-key-name">"Laptop ASUS - Làm việc"</strong>.</p>
            <div class="modal-warning">
                <p class="modal-warning-title">Sau khi thu hồi:</p>
                <ul>
                    <li>Khóa sẽ <strong>mất hiệu lực ngay lập tức</strong>, không ai có thể dùng nó để ký đơn hàng giả mạo.</li>
                    <li>Hệ thống vẫn giữ Public Key này để đối chiếu lịch sử đơn hàng cũ.</li>
                </ul>
            </div>
            <p class="modal-confirm-text">Hành động này không thể hoàn tác. Bạn chắc chắn muốn thu hồi?</p>
        </div>
        <div class="modal-footer">
            <button onclick="closeRevokeModal()" class="btn-modal-cancel">Hủy</button>
            <button onclick="executeRevoke()" class="btn-modal-confirm">Xác nhận thu hồi</button>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
<script>
    window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/user_key.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/sidebar_account_change_avatar.js"></script>
</html>
