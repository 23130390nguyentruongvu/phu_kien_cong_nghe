package vn.edu.hcmuaf.fit.pkcn.service.order;

import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDao orderDao;
    private ProductDao productDao;

    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public List<OrderShowAsItem> getOrdersShowAsItem(int userId) {
        HashMap<Integer, OrderShowAsItem> res = orderDao.getOrdersShowAsItem(userId);
        if (res.isEmpty()) return null;
        //Lấy các id của order để lấy các order detail
        List<Integer> orderIds = res.keySet().stream().toList();

        HashMap<Integer, List<ProductVariantWrapOrder>> orderDetails = productDao.getProdVarWrapOrder(orderIds);
        for (Map.Entry<Integer, List<ProductVariantWrapOrder>> entry : orderDetails.entrySet())
            res.get(entry.getKey()).setOrderDetails(entry.getValue());

        return res.values().stream().toList();
    }
}
