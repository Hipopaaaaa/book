package ohj.service.impl;

import ohj.dao.BookDAO;
import ohj.dao.OrderDao;
import ohj.dao.OrderItemDao;
import ohj.dao.impl.BookDAOImpl;
import ohj.dao.impl.OrderDAOImpl;
import ohj.dao.impl.OrderItemDAOImpl;
import ohj.pojo.*;
import ohj.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ohj
 * @Date 2022-07-19 21:54
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao=new OrderDAOImpl();
    private BookDAO bookDAO=new BookDAOImpl();
    private OrderItemDao orderItemDao=new OrderItemDAOImpl();

    /**
     * 功能：生成订单，并修改图书的相关信息
     * @param cart
     * @param userId
     * @return
     */
    @Override
    public String createOrder(Cart cart, Integer userId) {
        //生成订单号==唯一性,用时间戳+用户id来实现
        String orderId=System.currentTimeMillis()+""+userId;
        //生成订单
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        //保存订单
        orderDao.saveOrder(order);



        //把购物车的每一个商品项，转成订单项，并保存到数据库
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            //取出商品项
            CartItem cartItem=entry.getValue();
            //转成订单项，并保存
            OrderItem orderItem=new OrderItem(null,cartItem.getName(),cartItem.getCount(),
                    cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            orderItemDao.saveOrderItem(orderItem);

            //修改商品项（图书）的销量和库存
            Book book = bookDAO.queryBookById(cartItem.getId());
            book.setSales(book.getSales()+cartItem.getCount());  //销量
            book.setStock(book.getStock()-cartItem.getCount());  //库存
            bookDAO.updateBook(book);
        }
        //生成订单后，清空购物车
        cart.clear();
        return orderId;
    }

    /**
     * 功能：查询全部订单
     * @return
     */
    @Override
    public List<Order> showAllOrders() {
        return orderDao.queryOrders();
    }
}
