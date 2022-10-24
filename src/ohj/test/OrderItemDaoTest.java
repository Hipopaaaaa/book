package ohj.test;

import ohj.dao.OrderItemDao;
import ohj.dao.impl.OrderItemDAOImpl;
import ohj.pojo.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-19 21:48
 */
class OrderItemDaoTest {

    @Test
    void saveOrderItem() {
        OrderItemDao orderItemDao=new OrderItemDAOImpl();
        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"1234567890"));

    }
}