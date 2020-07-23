package cn.acyou.framework.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/23]
 **/
//@Component
@WebFilter(filterName = "fiterA", urlPatterns = {"/**"})
public class FilterA implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("FilterA 初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FilterA 前置");
        filterChain.doFilter(request, response);
        System.out.println("FilterA 后置");
    }

    @Override
    public void destroy() {
        System.out.println("FilterA 销毁了");
    }
}
