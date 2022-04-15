package com.dj.boot.configuration.permission.filter;



import com.dj.boot.configuration.permission.filter.chain.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * 安全控制过滤器，继承自javax.servlet.Filter，属于Filter的一个拓展接口，包括内部过滤功能，页面重定向，免过滤，重置功能。
 * @see javax.servlet.Filter
 * User: wj
 */
public interface SecurityControlFilter extends Filter {

    /**
     * 普通URL后缀
     */
    public String URL_END_CHAR="?" ;

    /***
     * 当filter执行完之后，用于重置，很多filter在执行后，都要进行一些数据的重置，此方法用来重置filter内的相关数据
     */
    void reset();

    /***
     * 获取免过滤的url列表
     * @return
     */
    public List<String> getFilterProcessUrls();

    /***
     * 验证url是否属于免过滤url，符合返回true
     * @param url 需要验证的url
     * @return 返回验证结果
     */
    public boolean process(String url);

    /***
     * 当filter发生错误或者校验不通过，需要进行处理，此方法用来完成出现问题时的处理过程
     * @param request 当前请求
     * @param response   当前返回
     * @throws IOException
     */
    void redirectTo(ServletRequest request, ServletResponse response) throws IOException ;

    /***
     * 内部过滤过程，为了和javax.servlet.Filter有别，定义了此方法。有些时候单单一个doFilter满足不了需求，在执行doFilter时，需要进行一些数据的判定，封装和初始化。
     * 此方法用来实现内部
     * @param servletRequest
     * @param servletResponse
     * @param chain
     * @throws IOException
     * @throws javax.servlet.ServletException
     */
    void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, SecurityFilterChain chain)   throws IOException, ServletException;
}
