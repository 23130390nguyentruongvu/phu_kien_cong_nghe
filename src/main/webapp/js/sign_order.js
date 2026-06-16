document.addEventListener("DOMContentLoaded", () => {
    const btnCopyHash = document.getElementById("btnCopyHash");
    const btnDownloadHash = document.getElementById("btnDownloadHash");
    const hashDataInput = document.getElementById("hashDataInput");
    const formatDataArea = document.getElementById("formatDataArea");

    const btnConfirmSignature = document.getElementById("btnConfirmSignature");
    const signatureInput = document.getElementById("signatureInput");

    const btnUploadSignature = document.getElementById("btnUploadSignature");
    const fileSignatureInput = document.getElementById("fileSignatureInput");

    if (btnCopyHash && hashDataInput) {
        btnCopyHash.addEventListener("click", () => {
            const hashValue = hashDataInput.value;
            navigator.clipboard.writeText(hashValue)
                .then(() => {
                    const originalText = btnCopyHash.textContent;
                    btnCopyHash.textContent = "Đã Copy ✓";
                    btnCopyHash.style.backgroundColor = "#155724";
                    setTimeout(() => {
                        btnCopyHash.textContent = originalText;
                        btnCopyHash.style.backgroundColor = "rgb(4, 107, 210)";
                    }, 2000);
                })
                .catch(err => {
                    alert("Không thể tự động copy, vui lòng chọn thủ công!");
                });
        });
    }

    if (btnDownloadHash && hashDataInput) {
        btnDownloadHash.addEventListener("click", () => {
            const currentConfig = window.orderConfig || { orderId: "unknown" };
            const hashValue = hashDataInput.value.trim();
            const fileContent = hashValue;

            const blob = new Blob([fileContent], { type: "text/plain;charset=utf-8" });
            const url = URL.createObjectURL(blob);

            const link = document.createElement("a");
            link.href = url;
            link.download = `Ma_Bam_Don_Hang_${currentConfig.orderId}.txt`;

            document.body.appendChild(link);
            link.click();

            document.body.removeChild(link);
            URL.revokeObjectURL(url);
        });
    }

    if (btnUploadSignature && fileSignatureInput) {
        btnUploadSignature.addEventListener("click", () => {
            fileSignatureInput.click();
        });

        fileSignatureInput.addEventListener("change", (event) => {
            const file = event.target.files[0];
            if (!file) return;

            const reader = new FileReader();
            reader.onload = (e) => {
                const fileContent = e.target.result.trim();
                if (signatureInput) {
                    signatureInput.value = fileContent;
                }
            };
            reader.onerror = () => {
                alert("Không thể đọc được nội dung tệp tin này!");
            };
            reader.readAsText(file, "UTF-8");

            // Reset giá trị để có thể chọn lại cùng một file nếu cần
            fileSignatureInput.value = "";
        });
    }

    if (btnConfirmSignature && signatureInput) {
        btnConfirmSignature.addEventListener("click", () => {
            const signatureValue = signatureInput.value.trim();
            const currentConfig = window.orderConfig || { userId: null, orderId: null };

            if (!signatureValue) {
                alert("Vui lòng nhập hoặc tải tệp chứa chuỗi chữ ký số Base64 nhận được từ Tool của chúng tôi!");
                signatureInput.focus();
                return;
            }

            btnConfirmSignature.disabled = true;
            const originalBtnText = btnConfirmSignature.textContent;
            btnConfirmSignature.textContent = "Đang xác thực...";

            const payload = {
                userId: currentConfig.userId,
                orderId: currentConfig.orderId,
                signature: signatureValue
            };

            const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1));
            const targetUrl = `${window.location.origin}${contextPath}/verify-and-save-signature`;

            fetch(targetUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                },
                body: JSON.stringify(payload)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Không thể kết nối đến máy chủ.");
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        alert("Xác nhận chữ ký số thành công! Đơn hàng đã được ký bảo vệ.");
                        window.location.href = `${window.location.origin}${contextPath}/order-success?orderId=${currentConfig.orderId}`;
                    } else {
                        alert(`LỖI XÁC THỰC: ${data.message || "Chữ ký không hợp lệ hoặc dữ liệu đơn hàng đã bị thay đổi!"}`);
                        btnConfirmSignature.disabled = false;
                        btnConfirmSignature.textContent = originalBtnText;
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("Đã xảy ra lỗi hệ thống trong quá trình gửi chữ ký.");
                    btnConfirmSignature.disabled = false;
                    btnConfirmSignature.textContent = originalBtnText;
                });
        });
    }
});