//package com.dj.boot.configuration.filter;
//
//import com.dj.boot.common.util.StringUtils;
//import com.dj.boot.common.util.UUIDUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @ProjectName: boot_demo
// * @Author: wangJia
// * @Date: 2021-05-12-16-55
// */
//public class CsrfTokenCheckFilter implements Filter {
//
//    private static final Logger log= LoggerFactory.getLogger(CsrfTokenCheckFilter.class);
//
//    private static final String CSRF_TOKEN = "csrfToken";
//
//    public CsrfTokenCheckFilter() {
//    }
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        Cookie[] cookies = servletRequest.getCookies();
//        String csrfToken = request.getParameter(CSRF_TOKEN);
//
//        if (StringUtils.isBlank(csrfToken)) {
//            chain.doFilter(servletRequest, response);
//            return;
//        }
//        if (isExistCheckToken(cookies)) {
//            for (Cookie item : cookies) {
//                if (CSRF_TOKEN.equals(item.getName())) {
//                    if (csrfToken.equals(item.getValue())) {
//                        //正常处理用户请求
//                        chain.doFilter(servletRequest, response);
//                        log.error("CsrfTokenCheckFilter success");
//                    } else {
//                        //判定为CSRF攻击
//                        log.error("CsrfTokenCheckFilter csrfToken{},item.getValue():{}", csrfToken, item.getValue());
//                        chain.doFilter(servletRequest, response);
//                        //throw new ServletException("CSRF invalid  exception!");
//                    }
//                }
//            }
//        } else {//用户成功登录业务系统后，随机生成csrfToken并在Cookie中设置
//            String token = UUIDUtil.getTerseUuid();
//            Cookie cookie = new Cookie("csrfToken", token);
//            HttpServletResponse servletResponse = (HttpServletResponse) response;
//            servletResponse.addCookie(cookie);
//        }
//    }
//
//    private boolean isExistCheckToken(Cookie[] cookies) {
//        Boolean checkToken = false;
//        for (Cookie item : cookies) {
//            if (CSRF_TOKEN.equals(item.getName())) {
//                checkToken = true;
//            }
//        }
//        return checkToken;
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
