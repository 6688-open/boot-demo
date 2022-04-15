package com.dj.boot.configuration.permission.filter.chain;


import com.dj.boot.configuration.permission.filter.SecurityControlFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wj
 * To change this template use File | Settings | File Templates.
 */
public interface SecurityFilterChain extends FilterChain {

    //匹配*的表达式,/**通用此表达式
    //表达式含义：字母/数字+/+.+字母/数字,任意部分都可有可无
    public String FILTERS_MATCH_EXPRESSION="[\\w*/*]*.*\\w*";
    public String URL_END_CHAR="?" ;

    public void setCurrentFilterChain(FilterChain chain);

    public void reset();


    public void initChain(FilterChain chain);

    public List<SecurityControlFilter> getFilters();

    public int getCurrentPosition();

    /**
     * 获取实现chain的规则信息
     * @return
     */
    public List<String> getChainRules();


    /**
     * 匹配url规则，满足进行filterchain
     * @param url
     * @return
     */
    public boolean needFilter(String url);

    public void run(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException;
}
