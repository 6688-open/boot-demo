package com.dj.boot.common.base.auth;

import org.springframework.web.servlet.ModelAndView;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.base.auth
 * @Author: wangJia
 * @Date: 2021-06-30-10-09
 */
public class AuthReturn {
    public final static String NO_PERMISSION_KEY = "error";

    public final static String NO_PERMISSION_DESC = "无权限!!!!!";

    public final static String NO_PERMISSION_VIEW = "/error/error";
    private ModelAndView mav;
    private boolean isContain;

    public AuthReturn(boolean isContain) {
        if (!isContain) {
            mav = new ModelAndView();
            mav.addObject(NO_PERMISSION_KEY, NO_PERMISSION_DESC);
            mav.setViewName(NO_PERMISSION_VIEW);
        }
        this.isContain = isContain;
    }

    public boolean isContain() {
        return isContain;
    }

    public ModelAndView getMav() {
        return mav;
    }
}
