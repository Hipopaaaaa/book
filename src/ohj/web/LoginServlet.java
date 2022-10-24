package ohj.web;

import ohj.pojo.User;
import ohj.service.UserService;
import ohj.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ohj
 * @Date 2022-07-12 21:20
 * 登陆模块的程序（已弃用）
 */
public class LoginServlet extends HttpServlet {
    //web层调用service层
    private UserService userService=new UserServiceImpl();

    /**
     * 功能： 完成登陆
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(new User(username, password));
        if(user!=null){

            //登陆成功，跳到成功页面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }else {

            //把错误信息，和回显的表单项信息，保存到request域中
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",username);
            //登陆失败，跳回登陆页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }
    }
}
