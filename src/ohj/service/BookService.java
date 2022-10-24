package ohj.service;

import ohj.pojo.Book;
import ohj.pojo.Page;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-17 13:47
 */
public interface BookService {
    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public  Book queryBookById(Integer id);

    public List<Book> queryBook();

    Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize,int min,int max);
}
