package vn.edu.hcmuaf.fit.pkcn.model.slidershow;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class SliderShow {
    private int id;
    private String title;
    private String urlImage;
    private int orderIndex;

    public SliderShow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    @ColumnName("url_image")
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @ColumnName("order_index")
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
