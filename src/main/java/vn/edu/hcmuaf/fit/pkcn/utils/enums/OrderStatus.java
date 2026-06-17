package vn.edu.hcmuaf.fit.pkcn.utils.enums;

public enum OrderStatus {
    PENDING_SIGNATURE("pending_signature", "Chờ khách hàng ký số"),
    SIGNED("signed", "Đã ký số - Chờ Admin duyệt"),
    WAITING_RE_SIGN("waiting_re_sign", "Đơn bị sửa - Chờ khách ký lại"),

    APPROVED("approved", "Admin đã duyệt đơn"),
    REJECTED("rejected", "Admin từ chối duyệt"),
    SECURITY_ALERT("security_alert", "Cảnh báo bảo mật đơn"),

    PENDING("pending", "Đang xử lý đóng gói / Giao hàng"),
    SHIPPING("shipping", "Đơn hàng đang được vận chuyển"),
    COMPLETED("completed", "Hoàn thành đơn hàng"),
    CANCEL("cancel", "Đã hủy bởi người dùng");

    private final String code;
    private final String displayName;

    OrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static OrderStatus fromCode(String code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ: " + code);
    }
}