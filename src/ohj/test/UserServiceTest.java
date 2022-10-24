package ohj.test;

import ohj.pojo.User;
import ohj.service.UserService;
import ohj.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ohj
 * @Date 2022-07-12 15:42
 */
class UserServiceTest {
    UserService userService=new UserServiceImpl();

    @Test
    void registUser() {
        userService.registUser(new User(null,"text2","text2","text2@qq.com"));

    }

/*    @Test
    void login() {
        System.out.println(userService.login(new User(null, "ohj", "ohj", "ohj@qq.com")));
    }*/

    @Test
    void existsUsername() {
        if(userService.existsUsername("ohj11111")){
            System.out.println("用户名可用");
        }else {
            System.out.println("用户名已存在");
        }
    }
    //模拟Servlet中有多个功能，即有多个方法
    public void login(){
        System.out.println("这是login()方法被调用了");
    }
    public void regist(){
        System.out.println("这是regist方法被调用了");
    }
    public void update(){
        System.out.println("这是update()方法被调用了");
    }
    public void updateUser(){
        System.out.println("这是updateUser()方法被调用了");
    }
    public void updatePwd(){
        System.out.println("这是updatePwd()方法被调用了");
    }

    public static void main(String[] args) {
        String action="login";
        try {
            //获取action业务鉴别字符串，获取相应的业务 方法反射对象
            Method declaredMethod = UserServiceTest.class.getDeclaredMethod(action);
            //调用目标业务方法
            declaredMethod.invoke(new UserServiceTest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}