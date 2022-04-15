package com.dj.boot.configuration.filter;

import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.configuration.filter.permission.ChangeRequestWrapper;
import com.dj.boot.configuration.filter.service.DeptUriService;
import com.dj.boot.configuration.filter.service.PermissionService;
import com.dj.boot.configuration.filter.service.WarehouseUriService;
import com.dj.boot.configuration.permission.domain.PermissionType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-05-12-16-55
 */
@Slf4j
//@Component   FilterConfig里面已经将此过滤器注册到容器中  冲突
/*@WebFilter(urlPatterns = "/user/*")*/
public class DecryptFilter implements Filter {

    @Autowired
    DeptUriService deptUriService;
    @Autowired
    WarehouseUriService warehouseUriService;
    @Autowired
    PermissionService permissionService;
    private FilterConfig config;

    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("excludeUrlPatterns");
        if (StringUtils.isNotBlank(urls)) {
            excludedUrls = Arrays.asList(urls.split(","));
        }
        config = filterConfig;
        initMethod(filterConfig);
    }


    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(".....................进入过滤器.................");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (CollectionUtils.isNotEmpty(excludedUrls) && StringUtils.isNotBlank(requestURI) && excludedUrls.contains(requestURI)) {
            filterChain.doFilter(request,response);
            return;
        }

        ChangeRequestWrapper changeRequestWrapper = permissionService.processRequest(servletRequest);
        //使用复写后的wrapper
        filterChain.doFilter(changeRequestWrapper, servletResponse);
        //filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }


    /**
     * web应用启动的顺序是：listener->filter->servlet，而因为项目应用了spring mvc，
     * 所以我们会有一个配置文件(applixationContext.xml)，我们在配置spring时会用到spring的listener，
     * 它会读取application.xml里的配置对spring context进行初始化；项目启动时，
     * 先初始化listener，因此配置在applicationContext.xml里的bean会被初始化和注入；
     * 然后再来就filter的初始化，再接着才到我们的dispathServlet的初始化，
     * 因此，当我们需要在filter里注入一个注解的bean时，就会注入失败，因为filter初始化时，注解的bean还没初始化，没法注入。
     */
    private void initMethod(FilterConfig filterConfig) {
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
        if(cxt != null){
            if (cxt.getBean(DeptUriService.class) != null && deptUriService == null) {
                deptUriService = (DeptUriService) cxt.getBean(DeptUriService.class);
            }
            if (cxt.getBean(WarehouseUriService.class) != null && warehouseUriService == null) {
                warehouseUriService = (WarehouseUriService) cxt.getBean(WarehouseUriService.class);
            }
            if (cxt.getBean(PermissionService.class) != null && permissionService == null) {
                permissionService = (PermissionService) cxt.getBean(PermissionService.class);
            }

        }
    }
}
