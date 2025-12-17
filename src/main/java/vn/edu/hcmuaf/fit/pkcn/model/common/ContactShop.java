package vn.edu.hcmuaf.fit.pkcn.model.common;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class ContactShop {
    private int id;
    private String shopName;
    private String shopAddress;
    private String phoneNumber;
    private String hotline;
    private String email;
    private String urlFb;
    private String urlIns;
    private String avatar;
    private int status;

    public int getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHotline() {
        return hotline;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlFb() {
        return urlFb;
    }

    public String getUrlIns() {
        return urlIns;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getStatus() {
        return status;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    @ColumnName("shop_name")
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @ColumnName("shop_address")
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    @ColumnName("shop_phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ColumnName("shop_hotline")
    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    @ColumnName("shop_email")
    public void setEmail(String email) {
        this.email = email;
    }

    @ColumnName("facebook_url")
    public void setUrlFb(String urlFb) {
        this.urlFb = urlFb;
    }

    @ColumnName("instagram_url")
    public void setUrlIns(String urlIns) {
        this.urlIns = urlIns;
    }

    @ColumnName("img_url")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @ColumnName("status")
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContactShop{" +
                "id=" + id +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hotline='" + hotline + '\'' +
                ", email='" + email + '\'' +
                ", urlFb='" + urlFb + '\'' +
                ", urlIns='" + urlIns + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status=" + status +
                '}';
    }
}
