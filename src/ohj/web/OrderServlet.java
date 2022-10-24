package ohj.web;

import ohj.dao.BookDAO;
import ohj.dao.impl.BookDAOImpl;
import ohj.pojo.Cart;
import ohj.pojo.Order;
import ohj.pojo.User;
import ohj.service.OrderService;
import ohj.service.impl.OrderServiceImpl;
import ohj.utils.JdbcUtilsByDruid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-19 22:12
 * 订单模块的程序
 */
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();


    /**
     * 功能：生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //在Session域中获取购物车对象，和userId
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User loginUser = (User) req.getSession().getAttribute("user");
        //判断用户无登陆，则需要登陆
        if (loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);

            return;
        }
        Integer userId = loginUser.getId();

        //生成订单,得到订单号
        String orderId = orderService.createOrder(cart, userId);

        //把订单号保存到域中，用于回显
        req.getSession().setAttribute("orderId", orderId);


        //请求重定向
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }


    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取查询到的全部订单
        List<Order> orders = orderService.showAllOrders();
        //保存到session中
        req.getSession().setAttribute("orders", orders);

        //请求重定向
        resp.sendRedirect(req.getContextPath() + "/pages/order/order.jsp");
    }
}
