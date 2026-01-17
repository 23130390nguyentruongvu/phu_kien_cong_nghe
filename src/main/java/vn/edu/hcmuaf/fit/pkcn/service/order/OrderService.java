package vn.edu.hcmuaf.fit.pkcn.service.order;

import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;
    private ProductDao productDao;
    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }
    public List<OrderShowAsItem> getOrdersShowAsItem(int userId) {
        List<OrderShowAsItem> res = orderDao.getOrdersShowAsItem(userId);
        if(res.isEmpty()) return null;
        //Lấy các id của order để lấy các order detail
        List<Integer> orderIds = new ArrayList<>();


    }
}
