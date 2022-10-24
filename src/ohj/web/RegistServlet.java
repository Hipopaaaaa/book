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
 * @Date 2022-07-12 19:36
 * 注册模块的程序（已弃用）
 */
public class RegistServlet  extends HttpServlet {
    //web层调service层
    private UserService userService=new UserServiceImpl();

    /**
     * 功能：完成注册
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
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //2.检查验证码是否正确    验证码写死为abcde
        if("abcde".equalsIgnoreCase(code)){


            //检查用户名是否可用
            if(userService.existsUsername(username)){
                //把回显信息，保存到Request域中
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);
                //不可用,也跳回注册页面
                System.out.println("用户名["+username+"]已存在");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else {
                //可用，则保存到数据库
                userService.registUser(new User(null,username,password,email));
                //跳到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
            }


        }else {
            //把回显信息，保存到Request域中
            req.setAttribute("msg","验证码错误！！");
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            //跳回注册页面
            System.out.println("验证码["+code+"]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }
}
