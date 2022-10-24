package ohj.web;

import ohj.pojo.Book;
import ohj.pojo.Page;
import ohj.service.BookService;
import ohj.service.impl.BookServiceImpl;
import ohj.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ohj
 * @Date 2022-07-18 13:16
 * 用户显示模块的程序
 */
public class ClientBookServlet extends BaseServlet {
    private BookService bookService=new BookServiceImpl();

    /**
     * 功能：显示当前页的数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 pageNo和pageSize，默认第一页
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //调用BookService.page(pageNo,pageSize) 返回一个page对象
        Page<Book> page=bookService.page(pageNo,pageSize);

        //设置请求地址
        page.setUrl("client/clientBookServlet?action=page");

        //保存page对象到request域
        req.setAttribute("page",page);

        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    /**
     * 功能：根据传入的商品价格范围，显示数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 pageNo和pageSize，默认第一页
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //获取要查询的范围
        int min=WebUtils.parseInt(req.getParameter("min"),0);
        int max=WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);

        //调用BookService.pageByPrice(pageNo,pageSize,min,max) 返回一个page对象
        Page<Book> page=bookService.pageByPrice(pageNo,pageSize,min,max);

        //设置请求地址,给分页条使用

        StringBuilder sb = new StringBuilder("client/clientBookServlet?action=pageByPrice");
        //如果有最小/大价格参数，追加到分页条地址参数中
        if(req.getParameter("min")!=null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        if(req.getParameter("max")!=null){
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());

        //保存page对象到request域
        req.setAttribute("page",page);

        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
