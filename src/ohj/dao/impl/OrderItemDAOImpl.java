package ohj.dao.impl;

import ohj.dao.OrderItemDao;
import ohj.pojo.OrderItem;

/**
 * @Author ohj
 * @Date 2022-07-19 21:42
 */
public class OrderItemDAOImpl extends  BaseDAO<OrderItem> implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql="insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        return update(sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(),
                orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
