package vn.edu.hcmuaf.fit.pkcn.model.common;

import java.math.BigDecimal;

public class VoucherApplication {
    private final int voucherId;
    private final String code;
    private final String name;
    private final String discountType;
    private final BigDecimal eligibleSubtotal;
    private final BigDecimal discountAmount;

    public VoucherApplication(int voucherId, String code, String name, String discountType,
                              BigDecimal eligibleSubtotal, BigDecimal discountAmount) {
        this.voucherId = voucherId;
        this.code = code;
        this.name = name;
        this.discountType = discountType;
        this.eligibleSubtotal = eligibleSubtotal;
        this.discountAmount = discountAmount;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDiscountType() {
        return discountType;
    }

    public BigDecimal getEligibleSubtotal() {
        return eligibleSubtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
}
