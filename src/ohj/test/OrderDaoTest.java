package ohj.test;

import ohj.dao.OrderDao;
import ohj.dao.impl.OrderDAOImpl;
import ohj.pojo.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-19 21:45
 */
class OrderDaoTest {

    @Test
    void saveOrder() {
        OrderDao orderDao=new OrderDAOImpl();
        orderDao.saveOrder(new Order("1234567890",new Date(),new BigDecimal(100),0,1));
    }

    @Test
    void  queryOrders(){
        OrderDao orderDao=new OrderDAOImpl();
        List<Order> orders = orderDao.queryOrders();
        orders.forEach(System.out::println);
    }
}