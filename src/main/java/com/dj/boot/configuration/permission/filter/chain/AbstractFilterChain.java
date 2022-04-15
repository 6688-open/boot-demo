package com.dj.boot.configuration.permission.filter.chain;

import com.dj.boot.configuration.permission.filter.SecurityControlFilter;
import com.dj.boot.configuration.permission.filter.parser.url.SimpleUrlParser;
import com.dj.boot.configuration.permission.filter.parser.url.UrlParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 抽象过滤链，实现了大多数SecurityFilterChain的方法，增加了链规则，只有当符合规则的url才会进入到链中进行过滤
 * 集成了多个过滤器，每个过滤器实现不同的过滤操作，存储了当前过滤链，便于在过滤过程中随时跳出链体，实现下一个链式操作
 *
 * User: wj
 */
public abstract class AbstractFilterChain implements SecurityFilterChain{
    private UrlParser urlParser=new SimpleUrlParser();

    //过滤链规则
    private List<String> chainRules;
    //过滤器
    private List<SecurityControlFilter> filters;
    //当前过滤链
    protected FilterChain currentFilterChain;
    protected final Log log = LogFactory.getLog(getClass());
    //当前遍历位置
    protected int currentPosition=0;

    public UrlParser getUrlParser() {
        return urlParser;
    }

    public void setUrlParser(UrlParser urlParser) {
        this.urlParser = urlParser;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void initChain(FilterChain chain){
        this.currentPosition=0;
        this.setCurrentFilterChain(chain);
    }

    /***
     * 判断当前的url是否符合rule规则，符合返回true
     * @param url 当前访问url地址
     * @param rule   制定的规则
     * @return
     */
    private boolean equals(String url,String rule){
        //定义equal，确定当前url和rule是否相等，默认为false
        //rule_contains_end 判断当前rule是否以'/’结尾，
        //url_contains_end 判断当前url是否以'/'结尾
        boolean equal=false,rule_contains_end=rule.endsWith("/"),url_contains_end=url.endsWith("/");
        //当url与rule绝对相等时，返回true
        if(url.equals(rule)){
                                  equal=true;
        }
        else{
            //如果rule以‘/'结尾，判断加了'/'的url是否与rule相等
            if(rule_contains_end){
                equal=rule.equals(url+"/");
            } else
            {
                //如果url以'/'结尾，判断加了'/'的rule是否与url相等
                equal=url.equals(rule+"/") ;
            }
        }
           return equal;
    }

    /***
     * 过滤链前置过滤器，用来过滤当前请求是否存在于需要过滤的行列中，如果不需要过滤则跳出过滤器
     * @param url 当前请求
     * @return
     */
    public boolean needFilter(String url){
        //将url解析成标准url
        url=urlParser.parseChainUrl(url);
        //默认为不匹配
        boolean matched=false;
        //规则为空，返回不匹配，即不需要过滤
         if(null==chainRules)return matched;
        else

         {
             Iterator<String> iterator=chainRules.iterator();
             while (iterator.hasNext()){
                 String originalRule=  iterator.next();
                 //发现当前规则不为空
                 if(StringUtils.isNotEmpty(originalRule))     {
                     //1:去空格
                     originalRule= originalRule.trim().replaceAll(" ","")   ;
                     if(StringUtils.isEmpty(originalRule))continue;
                     //2:规则完全匹配 /
                    if(matched=this.equals(url,originalRule))  {
                        if(log.isDebugEnabled()){
                            log.debug("在执行过滤器链之前，请求地址\""+url+"\"与chainRules匹配，匹配规则为："+originalRule);
                        }
                        break;
                    }

                     //2:如果规则中含有' * '，进行*正则匹配
                     if(originalRule.contains("*"))
                     {
                         String   rule= originalRule.replaceAll("\\*",FILTERS_MATCH_EXPRESSION);
                         //匹配url是否符合rule的正则表达式规则
                         if(matched=  url.matches( rule ))   {
                             if(log.isDebugEnabled()){
                                 log.debug("在执行过滤器链之前，请求地址\""+url+"\"与chainRules匹配，匹配规则为："+originalRule);
                             }
                             break;
                         }
                     }




                 }
             }
         }

        if(!matched){
            if(log.isDebugEnabled()){
                log.debug("未发现过滤器链中有符合\""+url+"\"的规则");
            }
        }
        return matched;
    }

    /***
     * url去问号
     * @param url
     * @return
     */
    protected String parseUrl(String url){
        int index=url.indexOf(URL_END_CHAR)  ;
        if(index!=-1)
            url= url.substring(0,url.indexOf(URL_END_CHAR))  ;
        return url;
    }

    /***
     * 过滤链运行主体，首先初始化当前过滤链，之后运行doFilter()进行过滤 ，最后进行过滤链重置
     * @param request  当前请求
     * @param servletResponse 相应
     * @param chain 当前过滤链
     * @throws IOException
     * @throws javax.servlet.ServletException
     */
    public synchronized   void run(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {


        if(log.isDebugEnabled()){
            log.debug("运行过滤链 "+this.getClass().getSimpleName()+" ... ...");
            log.debug("初始化FilterChain:"+chain.getClass().getSimpleName());
        }
        //初始化当前过滤链
        this.initChain(chain);
        if(log.isDebugEnabled()){
            log.debug("开始执行过滤......");
        }
        //过滤链过滤
        this.doFilter(request,servletResponse);

    }

//    /***
//     * 将chainRule进行去回车，封装List处理
//     * @param rules
//     * @return
//     */
//    protected Collection<String> transform(String rules) {
//        Collection<String> rs=null;
//        //按照linux系统和windows系统进行回车过滤
//         String linux_enter="\r\n",win_enter="\n";
//          if(null!=rules && !rules.isEmpty()) {
//
//             if(rules.contains(linux_enter))
//               rs= Arrays.asList(rules.split(linux_enter) );
//              if(rules.contains(win_enter))
//                  rs=Arrays.asList(rules.split(win_enter ))   ;
//          }
//
//                  return rs;
//    }

    public List<String> getChainRules() {

        return chainRules;
    }

    public void setChainRules(List<String> chainRules) {
        this.chainRules = chainRules;
    }

    public AbstractFilterChain(){

    }



    AbstractFilterChain(List<SecurityControlFilter> filters) {
        this.filters = filters;

    }

    public List<SecurityControlFilter> getFilters() {
        return this.filters;
    }


    public void setFilters(List<SecurityControlFilter> filters) {
        this.filters = filters;
    }
    public void setCurrentFilterChain(FilterChain currentFilterChain){

        this.currentFilterChain=currentFilterChain  ;
    }

    public FilterChain getCurrentFilterChain( ){

        return this.currentFilterChain;
    }

    /***
     * 对过滤链中的过滤器进行过滤处理
     */
    public void reset(){
        int passed=0;
        if(null!=filters&&filters.size()>0){

            for(SecurityControlFilter filter:filters){
                if(currentPosition==passed)
                    break;
                filter.reset();
                passed++;
            }
        }
    }
}
