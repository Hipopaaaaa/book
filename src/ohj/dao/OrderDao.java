package ohj.dao;

import ohj.pojo.Order;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-19 21:34
 */
public interface OrderDao {
    public int saveOrder(Order order);

    public List<Order> queryOrders();
}
