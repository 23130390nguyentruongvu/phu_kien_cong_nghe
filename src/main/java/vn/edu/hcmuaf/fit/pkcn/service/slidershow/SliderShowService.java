package vn.edu.hcmuaf.fit.pkcn.service.slidershow;

import vn.edu.hcmuaf.fit.pkcn.dao.slidershow.SliderShowDao;
import vn.edu.hcmuaf.fit.pkcn.model.slidershow.SliderShow;

import java.util.List;

public class SliderShowService {
    private SliderShowDao sliderShowDao;
    public SliderShowService(SliderShowDao sliderShowDao) {
        this.sliderShowDao = sliderShowDao;
    }

    public List<SliderShow> getSliderShowByStatus(int status, boolean isOrder) {
        return sliderShowDao.getSliderShowsByStatus(status, isOrder);
    }
}
