package com.dj.boot.configuration.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-05-12-16-55
 */
public class CsrfRefererCheckFilter implements Filter {


    public CsrfRefererCheckFilter() {
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        URL referer = new URL(servletRequest.getHeader("Referer"));
        String host = referer.getHost();
        // 判断Referer是否符合规则
        if (host.endsWith("localhost")) {
            // 正常处理业务请求
            chain.doFilter(servletRequest, response);
        } else {
            //不符合域名
            throw new ServletException("CSRF invalid  exception!");
        }
    }

    @Override
    public void destroy() {
    }
}
