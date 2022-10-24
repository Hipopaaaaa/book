package ohj.dao;

import ohj.pojo.Book;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-17 13:17
 */
public interface BookDAO {

    public int addBook(Book book);
    public  int deleteBookById(Integer id);
    public  int updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();

    public Integer queryForPageTotalCount();

    public Integer queryForPageTotalCount(int min,int max);

    public List<Book> queryForItems(int beign,int pageSize);
    public List<Book> queryForItems(int beign,int pageSize,int min,int max);
}
