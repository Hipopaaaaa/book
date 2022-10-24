package ohj.test;

import ohj.utils.JdbcUtilsByDruid;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * @Author ohj
 * @Date 2022-07-12 13:39
 */
public class JdbcUtilsTest {
    @Test
    public void testJdbcUtils(){
        for (int i = 0; i < 100; i++) {
            Connection connection = JdbcUtilsByDruid.getConnection();
            System.out.println(connection);
        }

    }
}
