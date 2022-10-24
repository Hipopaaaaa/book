package ohj.test;

import ohj.dao.UserDAO;
import ohj.dao.impl.UserDAOImpl;
import ohj.pojo.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-12 15:14
 * 测试UserDao和UserDAOimpl的功能
 */
class UserDAOTest {

    @Test
    void queryUserByUsername() {
        UserDAO userDAO=new UserDAOImpl();
        System.out.println(userDAO.queryUserByUsername("ohj111"));
    }

    @Test
    void saverUser() {
        UserDAO userDAO = new UserDAOImpl();
        User user = new User(null, "text", "text", "text@qq.com");

        System.out.println(userDAO.saverUser(user));
    }

    @Test
    void queryUserByUsernameAndPwd() {
        UserDAO userDAO=new UserDAOImpl();
        System.out.println(userDAO.queryUserByUsernameAndPwd("ohj","ohj"));
    }
}