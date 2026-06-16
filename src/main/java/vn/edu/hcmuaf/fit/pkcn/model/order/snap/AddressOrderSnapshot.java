package vn.edu.hcmuaf.fit.pkcn.model.order.snap;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class AddressOrderSnapshot {
    private Integer id;
    private String phoneNumber;
    private String addressDetail;
    private String provinceCity;
    private String district;
    private String receiverName;
    private String note;

    public AddressOrderSnapshot() {}

    public AddressOrderSnapshot(Integer id, String phoneNumber, String addressDetail,
                                String provinceCity, String district, String receiverName, String note) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.provinceCity = provinceCity;
        this.district = district;
        this.receiverName = receiverName;
        this.note = note;
    }

    @ColumnName("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnName("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ColumnName("address_detail")
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    @ColumnName("province_city")
    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    @ColumnName("district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @ColumnName("receiver_name")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @ColumnName("note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}