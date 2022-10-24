package ohj.service;

import ohj.pojo.User;

/**
 * @Author ohj
 * @Date 2022-07-12 15:28
 */
public interface UserService {

    /**
     * 功能：注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 功能：登陆
     * @param user
     * @return  登陆成功则返回一个用户，否则为null
     */
    public User login(User user);

    /**
     * 功能： 检查用户名是否可用
     * @param username
     * @return  返回true表示用户已存在，返回false表示用户可用
     */
    public boolean existsUsername(String username);
}
