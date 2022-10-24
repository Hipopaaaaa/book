package ohj.test;

import ohj.dao.OrderDao;
import ohj.dao.impl.OrderDAOImpl;
import ohj.pojo.Cart;
import ohj.pojo.CartItem;
import ohj.service.OrderService;
import ohj.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-19 22:06
 */
class OrderServiceTest {

    @Test
    void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"cpp",1,new BigDecimal(200),new BigDecimal(200)));
        System.out.println("当前购物车的内容："+cart);

        System.out.println("生成订单！！！！！");

        OrderService orderService=new OrderServiceImpl();
        String order = orderService.createOrder(cart, 1);
        System.out.println("生成的订单号："+order);
    }

    @Test
    void showAllOrders(){
        OrderService orderService=new OrderServiceImpl();
        orderService.showAllOrders().forEach(System.out::println);
    }
}