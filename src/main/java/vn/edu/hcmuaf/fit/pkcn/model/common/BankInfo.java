package vn.edu.hcmuaf.fit.pkcn.model.common;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class BankInfo {
    private int id;
    private int paymentMethodId;
    private String bankName, bankOwnerName, bankAccountNumber;

    public BankInfo() {
    }

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    @ColumnName("payment_method_id")
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getBankName() {
        return bankName;
    }

    @ColumnName("bank_name")
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankOwnerName() {
        return bankOwnerName;
    }

    @ColumnName("bank_owner_name")
    public void setBankOwnerName(String bankOwnerName) {
        this.bankOwnerName = bankOwnerName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    @ColumnName("bank_account_number")
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
