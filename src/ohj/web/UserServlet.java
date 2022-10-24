package ohj.web;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import ohj.pojo.User;
import ohj.service.UserService;
import ohj.service.impl.UserServiceImpl;
import ohj.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @Author ohj
 * @Date 2022-07-16 23:34
 * 用户模块程序（该Servlet结合了LoginServlet和RegistServlet）
 */
public class UserServlet  extends BaseServlet {  //继承BaseServlet，而不是HttpServlet
    //web层调用service层
    private UserService userService=new UserServiceImpl();

/*
    这段代码转移到 BaseServlet
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            // 根据action业务判别字符串，获取相应的 方法反射对象
           Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class, HttpServletResponse.class);
           // 调用目标业务方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

    /**
     * 功能：处理登陆
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(new User(username, password));
        if(user!=null){
            ////登陆成功,保存用户登陆之后的信息到Session域中
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            //跳到成功页面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }else {

            //把错误信息，和回显的表单项信息，保存到request域中
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",username);
            //登陆失败，跳回登陆页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }
    }

    /**
     * 功能：处理注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //把请求参数注入javaBean对象
        User user=WebUtils.copyParamToBean(req.getParameterMap(),new User());


        //获取Session中的验证码后，删除Session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //2.检查验证码是否正确
        if(token!=null&&token.equalsIgnoreCase(code)){


            //检查用户名是否可用(已无效)
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
                userService.registUser(user);
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

    /**
     * 功能：用于注册时体现用户，用户名是否可用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数 username
        String username = req.getParameter("username");

        //检查用户名是否可用
        boolean existsUsername = userService.existsUsername(username);
        //把返回的结果封装成为map对象
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("existsUsername", existsUsername);
        //转换成json，返回给客户端
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);
    }

    /**
     * 功能： 注销
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、销毁Session域中的内容 或销毁Session
        req.getSession().invalidate();
        //2、 重定向回到首页
        resp.sendRedirect(req.getContextPath());
    }
}
