package ohj.service.impl;

import ohj.dao.BookDAO;
import ohj.dao.impl.BookDAOImpl;
import ohj.pojo.Book;
import ohj.pojo.Page;
import ohj.service.BookService;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-17 13:50
 */
public class BookServiceImpl implements BookService {
    private BookDAO bookDAO = new BookDAOImpl();

    @Override
    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDAO.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDAO.queryBookById(id);
    }

    @Override
    public List<Book> queryBook() {
        return bookDAO.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<>();


        //设置每页显示的数量
        page.setPageSize(pageSize);
        //设置总记录数
        Integer pageTotalCount = bookDAO.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
        //获取总页码
        Integer pageTotal = pageTotalCount / pageSize;
        //设置总页码
        if (pageTotalCount % pageSize > 0) {
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
        //设置当前页面
        page.setPageNo(pageNo);

        //设置当前页数据
        int begin=(page.getPageNo()-1)*pageSize;
        List<Book> items=bookDAO.queryForItems(begin,pageSize);
        page.setItems(items);
        return page;
    }

    public Page<Book> pageByPrice(int pageNo,int pageSize,int min,int max){
        Page<Book> page = new Page<>();
        //设置每页显示的数量
        page.setPageSize(pageSize);
        //设置总记录数
        Integer pageTotalCount = bookDAO.queryForPageTotalCount(min, max);
        page.setPageTotalCount(pageTotalCount);

        //获取总页码
        Integer pageTotal=pageTotalCount / pageSize;
        //设置总页码
        if(pageTotalCount % pageSize>0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        //设置当前页码
        page.setPageNo(pageNo);

        //设置当前页数据
        int begin=(page.getPageNo()-1)*pageSize;
        List<Book> items = bookDAO.queryForItems(begin, pageSize, min, max);
        page.setItems(items);
        return page;
    }
}
