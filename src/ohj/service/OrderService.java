package ohj.service;

import ohj.pojo.Cart;
import ohj.pojo.Order;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-19 21:53
 */
public interface OrderService {
    public String createOrder(Cart cart,Integer userId);

    public List<Order> showAllOrders();
}
