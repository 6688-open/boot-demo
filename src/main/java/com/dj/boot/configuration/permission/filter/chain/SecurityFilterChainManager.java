package com.dj.boot.configuration.permission.filter.chain;

import com.dj.boot.configuration.permission.filter.login.LoginFilterChain;
import com.dj.boot.configuration.permission.filter.permission.PermissionFilterChain;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 安全过滤链管理器是整个过滤链的入口，通过引入它实现了对权限和登陆的过滤处理，引入过程在web.xml中设置
 * <filter></filter>和<filterMapping></filterMapping>，并在filter中指定application，loginChain和permissionChain。即完成在管理器中定义
 * 当前所属系统的名称，采用登录过滤链和权限过滤链对登录和权限进行过滤，在init过程中通过spring注入
 * 将loginFilterChain和permissionFilterChain进行实例化，并获取当前操作系统名称或者id，用于在
 * 权限判定时通过应用系统名甄别相关资源的权限访问，进行过滤时，首先判断当前链接是否需要该过滤链
 * 的chainRule，符合就进入链体进行过滤，否则进入下一个过滤链进行过滤
 * User: wj
 */
public class SecurityFilterChainManager implements Filter {

	protected final Log log = LogFactory.getLog(getClass());
	//当前spring的webApplicationContext
	private WebApplicationContext webApplicationContext;
	//过滤器配置
	FilterConfig filterConfig;
	//Http的servletContext
	private ServletContext servletContext;

	//登陆过滤连param-name
	public static final String loginChainTargetName = "loginChain";
	//权限过滤链param-name
	public static final String permissionChainTargetName = "permissionChain";

	//判断是否sso登录
	private static boolean IS_SSO = false;

	//登录过滤链
	LoginFilterChain loginChain;
//	//权限过滤链
	PermissionFilterChain permissionChain;

	protected final ServletContext getServletContext() {
		return (this.filterConfig != null ? this.filterConfig.getServletContext() : this.servletContext);
	}

	protected WebApplicationContext findWebApplicationContext() {
		if (this.webApplicationContext != null) {
			if (this.webApplicationContext instanceof ConfigurableApplicationContext) {
				if (!((ConfigurableApplicationContext) this.webApplicationContext).isActive()) {
					((ConfigurableApplicationContext) this.webApplicationContext).refresh();
				}
			}
			return this.webApplicationContext;
		}

		return WebApplicationContextUtils.getWebApplicationContext(getServletContext());

	}

	/**
	 * 初始化Filter
	 */
	private void initFilterBean() {
		WebApplicationContext context = this.findWebApplicationContext();
		//获取loginChain和permissionChain实例名
		String login_chain = this.getFilterConfig().getInitParameter(loginChainTargetName);
		String permission_chain = this.getFilterConfig().getInitParameter(permissionChainTargetName);
		if (!StringUtils.isEmpty(login_chain)) {
			this.loginChain = context.getBean(login_chain, LoginFilterChain.class);
			IS_SSO = this.loginChain.isSSO();
		}
		if (!StringUtils.isEmpty(permission_chain)) {
			this.permissionChain = context.getBean(permission_chain, PermissionFilterChain.class);
		}

	}

	public static boolean isSSO() {
		return IS_SSO;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public LoginFilterChain getLoginChain() {
		return loginChain;
	}

	public void setLoginChain(LoginFilterChain loginChain) {
		this.loginChain = loginChain;
	}

	public PermissionFilterChain getPermissionChain() {
		return permissionChain;
	}

	public void setPermissionChain(PermissionFilterChain permissionChain) {
		this.permissionChain = permissionChain;
	}

	public SecurityFilterChainManager() {

	}

	@Override
	public final void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.servletContext = this.getServletContext();
		initFilterBean();
	}

	/**
	 * 当前访问url去工程名
	 *
	 * @param request
	 * @return
	 */
	private String getRequestUrlFilterContextPath(HttpServletRequest request) {
		String uri = request.getRequestURI(), contextPath = request.getContextPath();

		if (null != contextPath)
			uri = uri.replace(contextPath, "");
		return uri;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		log.debug("进入security manager的chain是：" + chain.getClass());
		//获取精确uri
		String requestURI = this.getRequestUrlFilterContextPath((HttpServletRequest) servletRequest);
		if (log.isDebugEnabled()) {
			log.debug("进入SecurityFilterChain管理中心，对定义的过滤链进行过滤，请求URL:" + requestURI);
		}
		//如果符合loginChain过滤条件，进入loginChain进行过滤
		if (null != loginChain && loginChain.needFilter(requestURI)) {
			loginChain.run(servletRequest, servletResponse, chain);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("请求URL:" + requestURI + "不包含在过滤链 " + (null != loginChain ? loginChain.getClass().getSimpleName() : "loginChain==null") + " 的过滤规则中，不进行过滤,过滤规则:\n" + (null != loginChain ? loginChain.getChainRules() : "null"));
			}
		}

		//如果符合permissionChain过滤条件，进入permissionChain进行过滤
		if (null != permissionChain && permissionChain.needFilter(requestURI)) {
			permissionChain.run(servletRequest, servletResponse, chain);

		} else {
			if (log.isDebugEnabled()) {
				log.debug("请求URL:" + requestURI + "不包含在过滤链 " + (null != permissionChain ? permissionChain.getClass().getSimpleName() : "permissionChain==null") + " 的过滤规则中，不进行过滤,过滤规则:\n" + (null != permissionChain ? permissionChain.getChainRules() : "null"));
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("SecurityFilterChain管理中心完成过滤，进入请求URL:" + requestURI);
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
		this.loginChain = null;
		this.permissionChain = null;
	}

	public void reset() {
		//过滤链重置
		this.loginChain.reset();
		this.permissionChain.reset();
		if (log.isDebugEnabled()) {
			log.debug("过滤链 " + this.loginChain.getClass().getSimpleName() + " 执行完成！");
			log.debug("过滤链 " + this.permissionChain.getClass().getSimpleName() + " 执行完成！");
		}
	}
}
