package vn.edu.hcmuaf.fit.pkcn.model.product;

//lớp này có nhiệm vụ bọc dữ liệu của từng item con bên trong 1 item order của order history
public class ProductVariantWrapOrder {
    private int orderDetailId;
    private int prodVarId;//mã của biến thể
    private String name;//Tên sp cha
    private String type;//loại biến thể
    private int quantity;//số lượng trong order detail
    private double totalPrice;
    private String urlImage;
}
