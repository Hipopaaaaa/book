package ohj.service.impl;

import ohj.dao.UserDAO;
import ohj.dao.impl.UserDAOImpl;
import ohj.pojo.User;
import ohj.service.UserService;

/**
 * @Author ohj
 * @Date 2022-07-12 15:32
 */
public class UserServiceImpl implements UserService {
    //因为需要操作数据库，所以需要一个DAO
    private UserDAO userDAO=new UserDAOImpl();

    @Override
    public void registUser(User user) {
        userDAO.saverUser(user);
    }

    @Override
    public User login(User user) {
        return  userDAO.queryUserByUsernameAndPwd(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        User user = userDAO.queryUserByUsername(username);

        //说明没查到，表示可用
        if(user!=null){
            return true;
        }else {
            return false;
        }
    }
}
