package com.dj.boot.configuration.filter.config;

import com.dj.boot.configuration.filter.CsrfRefererCheckFilter;
import com.dj.boot.configuration.filter.DecryptFilter;
import com.dj.boot.configuration.permission.filter.chain.SecurityFilterChainManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.filter.config
 * @Author: wangJia
 * @Date: 2021-05-12-16-55
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean decryptFilterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DecryptFilter());
        //bean.addUrlPatterns("/user/*"); //拦截目标
        registration.addUrlPatterns("/*"); //拦截所有
        registration.setName("DecryptFilter"); //过滤器的名字也是web容器中的实例对象名，也可以不写这行，可以在bean注解
        registration.setOrder(2);//多个过滤器的执行顺序，数字越小优先级越高
        //参数 k-v  map 排除url
        registration.setInitParameters(getInitParameters());
        return registration;
    }


    @Bean
    public FilterRegistrationBean csrfRefererCheckFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CsrfRefererCheckFilter());
        List<String> urls = new ArrayList<>();
        //urls.add("/user/echo");
        urls.add("/user/findUserListPage");
        registration.setUrlPatterns(urls);
        registration.setName("CsrfRefererCheckFilter"); //过滤器的名字也是web容器中的实例对象名，也可以不写这行，可以在bean注解
        registration.setOrder(3);//多个过滤器的执行顺序，数字越小优先级越高
        return registration;
    }

//    @Bean
//    public FilterRegistrationBean csrfTokenCheckFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CsrfTokenCheckFilter());
//        List<String> urls = new ArrayList<>();
//        urls.add("/user/echo");
//        urls.add("/user/findUserListPage");
//        registration.setUrlPatterns(urls);
//        registration.setName("CsrfTokenCheckFilter"); //过滤器的名字也是web容器中的实例对象名，也可以不写这行，可以在bean注解
//        registration.setOrder(3);//多个过滤器的执行顺序，数字越小优先级越高
//        return registration;
//    }



    private Map<String, String> getInitParameters(){
        Map<String, String> map = new HashMap<>();
        map.put("excludeUrlPatterns", "/user/findUserListPage,/user/testFinally");
        return map;
    }



    @Bean
    public FilterRegistrationBean securityFilterChainManagerRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("SecurityFilter");
        registration.setFilter(new SecurityFilterChainManager());

        //bean.addUrlPatterns("/user/*"); //拦截目标
        registration.addUrlPatterns("/*"); //拦截所有
        registration.setOrder(1);//多个过滤器的执行顺序，数字越小优先级越高
        //参数 k-v  map 排除url
        registration.setInitParameters(getSecurityFilterInitParameters());
        return registration;
    }

    private Map<String, String> getSecurityFilterInitParameters() {
        Map<String, String> map = new HashMap<>();
        map.put("loginChain", "loginFilterChain");
        map.put("permissionChain", "PermissionFilterChain");
        return map;
    }

}
