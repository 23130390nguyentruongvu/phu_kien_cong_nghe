package vn.edu.hcmuaf.fit.pkcn.model.product;

import java.util.List;

public class ProductDetail {
    private int id;
    private String name;
    private String categoryName;
    private int warrantyPolicy;
    private String subDescription, description;
    private List<ProductVariant> variants;

    public int getId() {
        return id;
    }
}
