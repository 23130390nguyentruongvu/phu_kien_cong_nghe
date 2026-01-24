package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

public class PaymentMethod {
    private int id;
    private String nameMethod;
    private String subtitle;

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getNameMethod() {
        return nameMethod;
    }
    @ColumnName("name_method")
    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    public String getSubtitle() {
        return subtitle;
    }
    @ColumnName("subtitle")
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return id == that.id && Objects.equals(nameMethod, that.nameMethod) && Objects.equals(subtitle, that.subtitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameMethod, subtitle);
    }
}
