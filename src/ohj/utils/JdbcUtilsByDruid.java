package ohj.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.sun.org.apache.xpath.internal.operations.And;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author ohj
 * @Date 2022-07-12 13:14
 * 有数据一致性的   德鲁伊数据连接池+JDBC
 */
public class JdbcUtilsByDruid {
    private static DataSource dataSource;
    //让所有操作都是用同一个connection 实现数据一致性
    private static ThreadLocal<Connection> conns=new ThreadLocal<>();

    //完成初始化
    static {
        Properties properties = new Properties();

        try {
            //反射机制读取德鲁伊配置文件
            InputStream is = JdbcUtilsByDruid.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);

            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    //得到连接 ,返回null，则连接失败
    public static Connection getConnection()  {
        Connection connection=conns.get();
        if(connection==null){

            try {
                connection= dataSource.getConnection();
                conns.set(connection);  //保存到ThreadLocal对象中，供后面的JDBC操作使用

                connection.setAutoCommit(false); //设置手动管理，不默认提交事务

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    //提交事务，并关闭释放资源
    public static void commitAndClose(){
        Connection connection = conns.get();
        if(connection!=null){   //不等于null，说明之前使用过连接，操作过数据库

            try {

                connection.commit(); //提交事务

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    connection.close();  //关闭连接
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        //一定要执行remove操作，否则就会出错。(因为Tomcat服务器底层使用了线程池技术)
        conns.remove();
    }

    //回滚事务，并关闭释放资源
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if(connection!=null){   //不等于null，说明之前使用过连接，操作过数据库

            try {

                connection.rollback(); //回滚事务

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    connection.close();  //关闭连接
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        //一定要执行remove操作，否则就会出错。(因为Tomcat服务器底层使用了线程池技术)
        conns.remove();
    }

}
