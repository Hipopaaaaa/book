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
import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-17 14:00
 * 图书模块的程序
 */
public class BookServlet extends BaseServlet{
    private BookService bookService=new BookServiceImpl();

    /**
     * 功能：添加图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数，封装成为Book对象

        Book book= WebUtils.copyParamToBean(req.getParameterMap(), new Book());

        //调用bookService。addBook()保存图书
        bookService.addBook(book);

        //跳到图书列表页面，显示最后一页的内容
        /*req.getRequestDispatcher("/manager/bookServlet?action=list").forward(req,resp);*/
        int pageNo=WebUtils.parseInt(req.getParameter("pageNo"),0);
        pageNo++;
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+pageNo);
    }

    /**
     * 功能：根据id删除图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取要删除的图书id
        String id = req.getParameter("id");

        //调用bookService。deleteBook()删除图书
        bookService.deleteBookById(WebUtils.parseInt(id,0));

        //跳到图书列表页面,显示当前页
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));
    }

    /**
     * 功能：修改图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 并封装成为Book对象
        Book book=WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //修改图书
        bookService.updateBook(book);
        //重定向回去
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));
    }

    /**
     * 功能：显示所有图书（已弃用）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 通过BookService查询全部图书
        List<Book> books = bookService.queryBook();
        //2 把全部数据保存到request域
        req.setAttribute("books", books);
        //3. 请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    /**
     * 功能：根据id查找图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取图书编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //查询图书
        Book book = bookService.queryBookById(id);
        //保存图书到request域中
        req.setAttribute("book", book);
        //请求转发
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }

    /**
     * 功能：分页的方式显示图书
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
        page.setUrl("manager/bookServlet?action=page");

        //保存page对象到request域
        req.setAttribute("page",page);
        //请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
}
