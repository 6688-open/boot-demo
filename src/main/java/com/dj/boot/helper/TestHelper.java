package com.dj.boot.helper;

import com.dj.boot.common.util.SpringContextUtil;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.service.test.TestUserService;
import com.dj.boot.service.test.impl.TestUserServiceImpl;

/**测试帮助类
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.helper
 * @Author: wangJia
 * @Date: 2021-06-25-15-09
 */
public class TestHelper extends BaseController {

    private static final TestUserService testUserService;

    static {
        testUserService = SpringContextUtil.getBean("testUserServiceImpl", TestUserServiceImpl.class);
    }


    public static String getUserInfo(){
        return testUserService.getUser();
    }
}
