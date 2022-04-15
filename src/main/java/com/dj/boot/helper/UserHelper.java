package com.dj.boot.helper;

import com.dj.boot.controller.base.BaseController;
import com.dj.boot.service.test.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**用户帮助类
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-05-20-17-21
 */
@Component
public class UserHelper extends BaseController {

    @Autowired
    private TestUserService testUserService;


    public String getUserInfo(){
        return testUserService.getUser();
    }


}
