package ohj.filter;

import ohj.utils.JdbcUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author ohj
 * @Date 2022-07-20 22:15
 * 功能：给所有Servlet程序都加上事务管理
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtilsByDruid.commitAndClose(); //提交事务
        } catch (Exception e) {
            JdbcUtilsByDruid.rollbackAndClose(); //回滚事务
            throw new RuntimeException(e);


        }
    }

    @Override
    public void destroy() {

    }
}
