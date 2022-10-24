package ohj.dao.impl;

import ohj.dao.BookDAO;
import ohj.pojo.Book;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-17 13:19
 */
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public int addBook(Book book) {
        String sql="INSERT INTO t_book( `name`,`author`,`price`,`sales`,`stock`,`img_path`) values(?,?,?,?,?,?)";
        return update(sql,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {
        String sql="delete from t_book where `id`=?";
        return update(sql,id);
    }

    @Override
    public int updateBook(Book book) {
        String sql="update t_book set `name`=?,`author`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? where `id`=?";
        return update(sql,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath(),book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {
        String sql="select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` as imgPath from t_book where `id`=?";
        return queryForOne(sql,Book.class,id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql="select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` as imgPath from t_book ";
        return queryForList(sql,Book.class);
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql="select count(*) from t_book";
        Number count= (Number) queryForSingleValue(sql);
        return count.intValue();  //转成Integer类型
    }

    @Override
    public Integer queryForPageTotalCount(int min, int max) {
        //根据价格，查询总记录数
        String  sql="select count(*) from t_book where price between ? and ?";
       Number count =(Number) queryForSingleValue(sql, min, max);
       return count.intValue();
    }

    @Override
    public List<Book> queryForItems(int beign, int pageSize) {
        String sql="select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` from t_book limit ?,?";
        return queryForList(sql,Book.class,beign,pageSize);
    }

    @Override
    public List<Book> queryForItems(int beign, int pageSize, int min, int max) {
        //根据价格，查询数据
        String sql="select `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` from t_book where price between ? and ? order by `price` limit ?,?";
        return queryForList(sql, Book.class, min, max, beign, pageSize);
    }


}
