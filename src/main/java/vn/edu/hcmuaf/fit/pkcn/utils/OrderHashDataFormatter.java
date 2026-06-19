package vn.edu.hcmuaf.fit.pkcn.utils;

import vn.edu.hcmuaf.fit.pkcn.model.order.snap.AddressOrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderDetailSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OrderHashDataFormatter {
    /**mẫu
     *THÔNG TIN ĐƠN HÀNG
     * Mã đơn hàng: 12
     * Mã người dùng: 1
     * Mã địa chỉ nơi nhận: 1
     * Phương thức thanh toán: Chuyển khoản ngân hàng
     * Ghi chú đơn hàng: Giao vào giờ hành chính giúp
     *
     * THÔNG TIN NGƯỜI NHẬN: Mã (1)
     * Người nhận: Nguyễn Văn A
     * Số điện thoại: 0901234567
     * Địa chỉ: Số 1 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh
     * Ghi chú địa chỉ: Bấm chuông cửa màu xanh
     *
     * CHI TIẾT SẢN PHẨM
     * Mã đơn hàng chi tiết: 1
     * Mã của đơn hàng chứa đơn chi tiết: 12
     * - Mã biến thể: 102 | Điện thoại iPhone 15 Pro Max (Tự Nhiên) | SKU: IP15PM-256-NAT | Trọng lượng: 221g | Màu sắc: Titan Tự Nhiên | Kích cỡ: 256GB | Đơn giá: 29990000.00 | Số lượng: 1 | Tổng dòng: 29990000.00
     *
     * CHI PHÍ THANH TOÁN
     * Phí vận chuyển: 30000.00
     * Ngày tạo đơn: xxxx
     * TỔNG TIỀN PHẢI TRẢ: 30020000.00
     */
    public static String buildOrderFormatForHash(OrderSnapshot order) {
        if (order == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("THÔNG TIN ĐƠN HÀNG\n");
        sb.append("Mã đơn hàng: ").append(order.getId()).append("\n")
                .append("Mã người dùng: ").append(order.getUserId())
                .append("Mã địa chỉ nơi nhận: " ).append(order.getAddressOrderId());
        sb.append("Phương thức thanh toán: ")
                .append(order.getPaymentMethodSnapshot() != null ? order.getPaymentMethodSnapshot() : "").append("\n");
        sb.append("Ghi chú đơn hàng: ")
                .append(order.getNote() != null ? order.getNote() : "").append("\n");

        AddressOrderSnapshot address = order.getAddressOrderSnapshot();
        sb.append("\nTHÔNG TIN NGƯỜI NHẬN: Mã ("+ ((address.getId() != null)?address.getId():"Unknown") +")\n");
        if (address != null) {
            sb.append("Người nhận: ")
                    .append(address.getReceiverName() != null ? address.getReceiverName() : "").append("\n");
            sb.append("Số điện thoại: ")
                    .append(address.getPhoneNumber() != null ? address.getPhoneNumber() : "").append("\n");
            sb.append("Địa chỉ: ")
                    .append(address.getAddressDetail() != null ? address.getAddressDetail() : "").append(", ")
                    .append(address.getDistrict() != null ? address.getDistrict() : "").append(", ")
                    .append(address.getProvinceCity() != null ? address.getProvinceCity() : "").append("\n");
            sb.append("Ghi chú địa chỉ: ")
                    .append(address.getNote() != null ? address.getNote() : "").append("\n");
        } else {
            sb.append("Địa chỉ: Chưa cập nhật\n");
        }

        List<OrderDetailSnapshot> details = order.getOrderDetailSnapshots();
        sb.append("\nCHI TIẾT SẢN PHẨM\n");
        if (details != null && !details.isEmpty()) {
            details.sort(
                    Comparator.comparing(OrderDetailSnapshot::getId)
            );
            for (OrderDetailSnapshot item : details) {
                sb.append("Mã đơn hàng chi tiết: ").append((item.getId() != null)?item.getId():"Unknown");
                sb.append("Mã của đơn hàng chứa đơn chi tiết: ").append((item.getOrderId() != null)?item.getOrderId():"Unknown");
                sb.append("- Mã biến thể: ").append(item.getProductVariantId()).append(" | ")
                        .append(item.getProductNameSnapshot() != null ? item.getProductNameSnapshot() : "");

                if (item.getVariantNameSnapshot() != null && !item.getVariantNameSnapshot().trim().isEmpty()) {
                    sb.append(" (").append(item.getVariantNameSnapshot()).append(")");
                }

                sb.append(" | SKU: ").append(item.getSkuSnapshot() != null ? item.getSkuSnapshot() : "")
                        .append(" | Trọng lượng: ").append(item.getGramSnapshot() != null ? item.getGramSnapshot() + "g" : "0g")
                        .append(" | Màu sắc: ").append(item.getColorSnapshot() != null ? item.getColorSnapshot() : "N/A")
                        .append(" | Kích cỡ: ").append(item.getSizeSnapshot() != null ? item.getSizeSnapshot() : "N/A")
                        .append(" | Đơn giá: ").append(item.getVariantPriceSnapshot() != null ? item.getVariantPriceSnapshot().toPlainString() : "0")
                        .append(" | Số lượng: ").append(item.getQuantity())
                        .append(" | Tổng dòng: ").append(item.getPriceTotal() != null ? item.getPriceTotal().toPlainString() : "0")
                        .append("\n");
            }
        } else {
            sb.append("Danh sách sản phẩm trống\n");
        }

        sb.append("\nCHI PHÍ THANH TOÁN\n");
        sb.append("Phí vận chuyển: ").append(order.getShippingFee() != null ? order.getShippingFee().toPlainString() : "0").append("\n");
        sb.append("Ngày tạo đơn: ")
                .append(order.getOrderDate() != null
                        ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        : "")
                .append("\n");

        sb.append("TỔNG TIỀN PHẢI TRẢ: ").append(order.getTotalMustPay() != null ? order.getTotalMustPay().toPlainString() : "0");

        return sb.toString();
    }
}