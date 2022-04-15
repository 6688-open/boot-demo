package com.dj.boot.configuration.permission.filter.login;


import com.dj.boot.configuration.permission.filter.SecurityControlFilter;
import com.dj.boot.configuration.permission.filter.chain.AbstractFilterChain;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: wj
 */
public class LoginFilterChain extends AbstractFilterChain {
     private boolean SSO;

    public boolean isSSO() {
        return SSO;
    }

    public void setSSO(boolean SSO) {
        this.SSO = SSO;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse) throws IOException, ServletException {

        if (currentPosition == this.getFilters().size()) {
            return;
        } else {

            currentPosition++;

            SecurityControlFilter filter = this.getFilters().get(currentPosition - 1);

            filter.doFilterInternal(request, servletResponse, this);
            this.doFilter(request, servletResponse);
        }

    }


}
