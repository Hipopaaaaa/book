package ohj.dao.impl;

import ohj.utils.JdbcUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author ohj
 * @Date 2022-07-12 14:23
 * 带数据一致性的 BaseDAO
 */
public  class BaseDAO<T> {
    //使用DBUtils操作数据库
    private QueryRunner queryRunner =new QueryRunner();

    /**
     * 功能：执行 dml语句
     * @return
     */
    public int update(String sql,Object... parameter){
        Connection connection =null;

        try {
            connection=JdbcUtilsByDruid.getConnection();
            return queryRunner.update(connection, sql, parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能： 获取一个javaBena对象
     * @param sql
     * @param clazz
     * @param parameter
     * @return
     */
    public T queryForOne(String sql,Class<T> clazz,Object... parameter){
        Connection connection=null;

        try {
            connection=JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能：获取多个javaBean
     * @param sql
     * @param clazz
     * @param parameter
     * @return
     */
    public List<T> queryForList(String sql,Class<T> clazz,Object... parameter){
        Connection connection=null;
        try {
            connection=JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection,sql,new BeanListHandler<T>(clazz),parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能： 获取JavaBean的单个值
     * @param sql
     * @param parameter
     * @return
     */
    public Object queryForSingleValue(String sql,Object... parameter){
        Connection connection=null;
        try {
            connection=JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new ScalarHandler(), parameter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
