package com.dj.boot.common.base.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.base.auth
 * @Author: wangJia
 * @Date: 2021-06-30-10-08
 */
public class AuthValidateUtil {
    public static AuthReturn isContain(String[] authData, String userData) {
        if (authData == null || authData.length <= 0 || StringUtils.isBlank(userData)) {
            return new AuthReturn(false);
        }
        List<String> authDataList = Arrays.asList(authData);
        return new AuthReturn(authDataList.contains(userData));
    }


    public static AuthReturn isContain(String[] authData, String userData, Model model) {
        if (authData == null || authData.length <= 0 || StringUtils.isBlank(userData)) {
            return new AuthReturn(false);
        }
        if(model != null){
            model.addAttribute(AuthReturn.NO_PERMISSION_KEY,AuthReturn.NO_PERMISSION_DESC);
        }
        List<String> authDataList = Arrays.asList(authData);
        return new AuthReturn(authDataList.contains(userData));
    }
}
