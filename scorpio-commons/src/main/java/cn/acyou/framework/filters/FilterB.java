package cn.acyou.framework.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/23]
 **/
//@Component
@WebFilter(filterName = "fiterB", urlPatterns = {"/**"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FilterB implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("FilterB 初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FilterB 前置");
        filterChain.doFilter(request, response);
        System.out.println("FilterB 后置");
    }

    @Override
    public void destroy() {
        System.out.println("FilterB 销毁了");
    }
}