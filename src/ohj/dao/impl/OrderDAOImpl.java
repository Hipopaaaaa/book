package ohj.dao.impl;

import ohj.dao.OrderDao;
import ohj.pojo.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-19 21:36
 */
public class OrderDAOImpl extends  BaseDAO<Order> implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql="insert into t_order(`orderId`,`createTime`,`price`,`status`,`userId`) values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }


    @Override
    public List<Order> queryOrders() {
        //String sql="select `order_id`,`create_time`,`price`,`status`,`user_id` from t_order";
        String sql="select * from t_order";
        return queryForList(sql, Order.class);

    }

}
