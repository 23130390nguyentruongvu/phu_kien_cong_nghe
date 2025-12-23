package vn.edu.hcmuaf.fit.pkcn.dao.slidershow;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.slidershow.SliderShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SliderShowDao {
    private Jdbi jdbi;

    public SliderShowDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<SliderShow> getSliderShowsByStatus(int status, boolean isOrder) {
        if (status < 0 || status > 1) return null;
        String sql = "SELECT id, title, url_image, order_index " +
                "FROM slider_shows " +
                "WHERE status = :status ";
        return jdbi.withHandle(handle -> {
            List<SliderShow> lst = new ArrayList<>();
            Iterator<SliderShow> i = handle.createQuery(sql)
                    .bind("status", status)
                    .mapToBean(SliderShow.class).stream().iterator();
            while (i.hasNext()) {
                lst.add(i.next());
            }
            if(isOrder) {//sort tang dan
                lst.sort(Comparator.comparingInt(SliderShow::getOrderIndex));
            }
            return lst.isEmpty() ? null : lst;
        });
    }
}
