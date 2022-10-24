package ohj.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author ohj
 * @Date 2022-07-17 00:38
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    /**
     * 功能：根据传入的action，反射调用不同的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //解决响应中文乱码问题
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");

        try {
            // 根据action业务判别字符串，获取相应的 方法反射对象
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class, HttpServletResponse.class);
            // 调用目标业务方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
