package ohj.test;

import ohj.dao.BookDAO;
import ohj.dao.OrderDao;
import ohj.dao.impl.BookDAOImpl;
import ohj.dao.impl.OrderDAOImpl;
import ohj.pojo.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-17 13:33
 */
class BookDAOTest {
    private BookDAO bookDAO=new BookDAOImpl();
    @Test
    void addBook() {
        bookDAO.addBook(new Book(null,"ohj好帅","ohj",new BigDecimal(9999),11000,0,null));
    }

    @Test
    void deleteBookById() {
        bookDAO.deleteBookById(23);
    }

    @Test
    void updateBook() {
        bookDAO.updateBook(new Book(21,"ohj好帅","ohj",new BigDecimal(9999),11000,1000,null));
    }

    @Test
    void queryBookById() {
        System.out.println(bookDAO.queryBookById(21));
    }

    @Test
    void queryBooks() {
        bookDAO.queryBooks().forEach(System.out::println);
    }

    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDAO.queryForPageTotalCount());
    }

    @Test
    public void queryForItems() {
        List<Book> books = bookDAO.queryForItems(8, 4);
        books.forEach(System.out::println);
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDAO.queryForPageTotalCount(10,50));
    }

    @Test
    public void queryForItemsByPrice() {
        List<Book> books = bookDAO.queryForItems(1, 4,10,50);
        books.forEach(System.out::println);
    }



}