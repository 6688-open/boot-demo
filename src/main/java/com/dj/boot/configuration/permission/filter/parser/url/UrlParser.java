package com.dj.boot.configuration.permission.filter.parser.url;

/**
 * @author : wj
 */
public interface UrlParser {
    public String URL_END_CHAR="?" ;

    /***
     * 解析过滤器规则定义的url
     * @param url  request请求的requestURI
     * @return
     */
    public String parseFilterUrl(String url);

    /***
     * 解析权限判定时需要的url
     * @param url   request请求的requestURI
     * @return
     */
    public String parsePermissionUrl(String url);

    /***
     * 解析过滤链规则定义的url
     * @param url
     * @return
     */
    public String parseChainUrl(String url);

}
