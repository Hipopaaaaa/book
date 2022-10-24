package ohj.test;

import ohj.pojo.Book;
import ohj.service.BookService;
import ohj.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-17 13:52
 */
class BookServiceTest {
    private BookService bookService =new BookServiceImpl();
    @Test
    void addBook() {
        bookService.addBook(new Book(null,"hhhh","1125",new BigDecimal(100000),1000,0,null));
    }

    @Test
    void deleteBookById() {
        bookService.deleteBookById(24);
    }

    @Test
    void updateBook() {
        bookService.updateBook(new Book(24,"aaaaa","1125",new BigDecimal(100000),1000,0,null));
    }

    @Test
    void queryBookById() {
        System.out.println(bookService.queryBookById(24));
    }

    @Test
    void queryBook() {
        bookService.queryBook().forEach(System.out::println);
    }

    @Test
    public void page(){
        System.out.println(bookService.page(1,4));
    }
}