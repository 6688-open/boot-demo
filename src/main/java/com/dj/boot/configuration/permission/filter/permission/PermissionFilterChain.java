package com.dj.boot.configuration.permission.filter.permission;


import com.dj.boot.configuration.permission.filter.SecurityControlFilter;
import com.dj.boot.configuration.permission.filter.chain.AbstractFilterChain;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 权限过滤链，对定义的权限过滤器进行链式递归过滤
 * User: wj
 */
public class PermissionFilterChain extends AbstractFilterChain {

	public PermissionFilterChain() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse) throws IOException, ServletException {

		//根据currentPosition值判断轮询是否结束
		if (currentPosition == this.getFilters().size()) {
			//轮询结束，跳出过滤链
			return;
		} else {

			currentPosition++;
			//执行下一个过滤器，进行信息过滤
			SecurityControlFilter filter = this.getFilters().get(currentPosition - 1);

			//调用内部过滤机制，传递当前filterchain给过滤器
			filter.doFilterInternal(request, servletResponse, this);
			//遍历当前方法
			this.doFilter(request, servletResponse);
		}

	}

	public void reset() {

		//重置过滤器
		for (SecurityControlFilter filter : this.getFilters()) {
			filter.reset();
		}
	}

}
