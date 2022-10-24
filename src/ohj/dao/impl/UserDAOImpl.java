package ohj.dao.impl;

import ohj.dao.UserDAO;
import ohj.pojo.User;

/**
 * @Author ohj
 * @Date 2022-07-12 14:57
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public User queryUserByUsername(String username) {
        String sql="select `id`,`username`,`password`,`email` from t_user where username =?";
        return queryForOne(sql,User.class,username);

    }

    @Override
    public int saverUser(User user) {
        String sql="insert into t_user values(null,?,?,?)";
        return update(sql, user.getUsername(), user.getPassword(),user.getEmail());
    }

    @Override
    public User queryUserByUsernameAndPwd(String username, String password) {
       String sql="select `id`,`username`,`password`,`email` from t_user where username=? and password=?";
       return queryForOne(sql,User.class,username,password);
    }
}
