<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận và Ký đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_order.css">
    <script>
        window.orderConfig = {
            userId: ${requestScope.userId != null ? requestScope.userId : "null"},
            orderId: ${requestScope.orderId != null ? requestScope.orderId : "null"}
        };
    </script>
</head>
<body>
<div class="sign-container">
    <% if (request.getAttribute("ErrorMessage") != null) { %>
    <div class="alert-box alert-danger">
        <h2>Lỗi Tiến Trình</h2>
        <p>${requestScope.ErrorMessage}</p>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-secondary">Quay lại danh sách</a>
    </div>
    <% } else { %>
    <div class="sign-card">
        <div class="card-header">
            <h1>Xác Thực Cấu Trúc Đơn Hàng Trước Khi Ký</h1>
        </div>
        <div class="card-body">
            <div class="alert-box alert-info">
                <p>${requestScope.message}</p>
            </div>

            <div class="form-group">
                <label>1. Chi tiết dữ liệu đơn hàng:</label>
                <pre id="formatDataArea" class="data-display-box">${requestScope.formatData}</pre>
            </div>

            <div class="form-group">
                <label>2. Mã băm dữ liệu SHA-256 tương ứng:</label>
                <div class="hash-wrapper">
                    <input type="text" id="hashDataInput" class="hash-input" value="${requestScope.hashData}" readonly>
                    <button id="btnCopyHash" class="btn btn-primary">Copy mã băm</button>
                    <button id="btnDownloadHash" class="btn btn-success">Xuất file .txt</button>
                </div>
            </div>

            <div class="form-group verification-group">
                <label for="signatureInput">3. Nhập chữ ký số nhận được từ Tool:</label>
                <div class="signature-action-bar" style="margin-bottom: 10px; display: flex; gap: 10px;">
                    <input type="file" id="fileSignatureInput" accept=".txt" style="display: none;">
                    <button type="button" id="btnUploadSignature" class="btn btn-primary" style="background-color: #6a1b9a;">
                        Chọn file chữ ký (.txt)
                    </button>
                </div>
                <div class="signature-wrapper">
                    <textarea id="signatureInput" class="signature-textarea" placeholder="Dán chuỗi chữ ký số Base64 hoặc tải file .txt chứa chữ ký tại đây..." rows="3"></textarea>
                    <button id="btnConfirmSignature" class="btn btn-submit-sig">Xác Nhận Ký</button>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <p class="footer-note">Sau khi lấy mã băm, hãy dùng Tool của chúng tôi để thực hiện ký bằng Khóa bí mật (Private Key) của bạn.</p>
        </div>
    </div>
    <% } %>
</div>
<script type="module" src="${pageContext.request.contextPath}/js/sign_order.js"></script>
</body>
</html>