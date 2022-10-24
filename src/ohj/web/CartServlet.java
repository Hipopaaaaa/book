package ohj.web;

import com.google.gson.Gson;
import ohj.pojo.Book;
import ohj.pojo.Cart;
import ohj.pojo.CartItem;
import ohj.service.BookService;
import ohj.service.impl.BookServiceImpl;
import ohj.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ohj
 * @Date 2022-07-19 15:20
 * 购物车模块的程序
 */
public class CartServlet extends  BaseServlet{
    private BookService bookService=new BookServiceImpl();

    /**
     * 功能： 往购物车中添加商品项 （已弃用）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 商品编号
        int id= WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookSerivce.queryBookById 得到图书的信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        // 从Session域中获取购物车cart，若无则创建一个购物车cart，并放入session域中
        Cart cart= (Cart) req.getSession().getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        //把CartItem商品项 添加到购物车cart
        cart.addItem(cartItem);

        //把最后一个加入的商品放到session域中，用于数据的回显
        req.getSession().setAttribute("lastName",cartItem.getName());

        //重定向回点击加入购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));

    }

    /**
     * 功能： 往购物车中添加商品项  ajax方式
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求的参数 商品编号
        int id= WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookSerivce.queryBookById 得到图书的信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        // 从Session域中获取购物车cart，若无则创建一个购物车cart，并放入session域中
        Cart cart= (Cart) req.getSession().getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        //把CartItem商品项 添加到购物车cart
        cart.addItem(cartItem);

        //把最后一个加入的商品放到session域中，用于数据的回显
        req.getSession().setAttribute("lastName",cartItem.getName());

        //返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultManJsonString = gson.toJson(resultMap);
        resp.getWriter().write(resultManJsonString);
    }

    /**
     * 功能：根据id删除购物车中的商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取商品编号
        int id=WebUtils.parseInt(req.getParameter("id"),0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart!=null){
            cart.deleteItem(id);
        }
        //重定向回原来购物车展示的页面
        resp.sendRedirect(req.getHeader("Referer"));

    }


    /**
     * 功能：清空购物车的商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车对象,并对商品进行删除
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            cart.clear();
        }

        //重定向回原来购物车展示的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 功能：根据id修改购物车中的商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 商品编号，商品数量
        int id=WebUtils.parseInt(req.getParameter("id"),0);
        int count=WebUtils.parseInt(req.getParameter("count"),1);

        //获取购物车对象，并对商品进行修改
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            cart.updateCount(id,count);
        }
        //重定向回原来购物车展示的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
