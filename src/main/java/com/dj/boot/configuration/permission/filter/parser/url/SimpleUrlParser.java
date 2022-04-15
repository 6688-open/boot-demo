package com.dj.boot.configuration.permission.filter.parser.url;

/**
 * @author : wj
 */
public class SimpleUrlParser implements UrlParser {


    @Override
    public String parseFilterUrl(String url) {
        int index=url.indexOf(URL_END_CHAR)  ;
        if(index!=-1)
            url= url.substring(0,url.indexOf(URL_END_CHAR))  ;
        return url;
    }

    @Override
    public String parsePermissionUrl(String url) {
        return parseFilterUrl(url);
    }

    @Override
    public String parseChainUrl(String url) {
        return parseFilterUrl(url);
    }
}
