package ohj.dao;

import ohj.pojo.User;

/**
 * @Author ohj
 * @Date 2022-07-12 14:51
 */
public interface UserDAO {
    /**
     * 功能：根据用户名查询用户信息
     * @param username
     * @return 返回null，说明没有用户
     */
    public User queryUserByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     * @return  返回-1 表示保存失败；1表示成功
     */
    public int saverUser(User user);

    /**
     * 功能：根据用户名和密码查询用户信息
     * @param username
     * @return 返回null，说明没有用户
     */
    public User queryUserByUsernameAndPwd(String username,String password);
}
