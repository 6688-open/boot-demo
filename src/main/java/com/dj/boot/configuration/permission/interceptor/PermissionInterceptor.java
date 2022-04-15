/*
package com.dj.boot.configuration.permission.interceptor;

import com.dj.boot.configuration.permission.annontation.PermissionValidate;
import com.dj.boot.configuration.permission.service.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.permission.interceptor
 * @Author: wangJia
 * @Date: 2021-04-30-14-31
 *//*

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    private final static Logger log = Logger.getLogger(PermissionInterceptor.class);

    @Autowired
    private PermissionService permissionService;

    */
/**
     * 描述：执行方法前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     *//*

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("进入权限设置");
        PermissionValidate validate = ((HandlerMethod) handler).getMethodAnnotation(PermissionValidate.class);
        if (validate == null) {
            return true;
        } else {
            //拦截器里对request进行改变
            return permissionService.processRequest(request, response, validate);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
*/
