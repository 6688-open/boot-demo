/*
package com.dj.boot.configuration.permission.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.configuration.permission.annontation.PermissionValidate;
import com.dj.boot.configuration.permission.domain.PermissionType;
import com.dj.boot.configuration.permission.domain.PermissionUri;
import com.dj.boot.configuration.permission.interceptor.ParameterRequestWrapper;
import com.dj.boot.configuration.permission.service.PermissionService;
import com.dj.boot.configuration.permission.service.PermissionUriService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.permission.service
 * @Author: wangJia
 * @Date: 2021-04-30-14-39
 *//*

*/
/*@Service*//*

public class PermissionServiceImpl implements PermissionService {

    private final static Logger log = Logger.getLogger(PermissionServiceImpl.class);

    private final static String ajaxHeader = "x-requested-with";
    private final static String ajaxsign = "XMLHttpRequest";


    @Resource()
    PermissionUriService deptUri;

    @Resource()
    PermissionUriService warehouseUri;



    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response, PermissionValidate validate) throws IOException {
        log.debug("设置权限数据开始");

        ParameterRequestWrapper changeRequestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        Map<String, String[]> parameterMap = new HashMap<>(changeRequestWrapper.getParameterMap());
        log.error("request参数:{}"+JSONObject.toJSONString(parameterMap));

        String[] names = validate.name();
        PermissionType[] authTypes = validate.type();
        PermissionUri[] authUris = validate.uri();
        List<String[]> authDateList = new ArrayList<String[]>();

        if (names == null || authTypes == null || authUris == null ||
                names.length * authTypes.length * authUris.length < 0 ||
                names.length != authTypes.length || authTypes.length != authUris.length) {
            log.error("设置注解配置错误,参数为空或数量不匹配");
        } else {
            for (int i = 0, j = authUris.length; i < j; i++) {
                String[] authDate = null;
                switch (authUris[i]) {
                    case dept:
                        authDate = deptUri.authData(request, authTypes[i]);
                        break;
                    case warehouse:
                        authDate = warehouseUri.authData(request, authTypes[i]);
                        break;
                    default:
                        break;
                }
                if (authDate != null && authDate.length > 0) {
                    authDateList.add(authDate);
                }
            }

        }

        boolean isAjax = false;
        if (request.getHeader(ajaxHeader) != null && request.getHeader(ajaxHeader).equalsIgnoreCase(ajaxsign)) {//如果是ajax请求响应头会有x-requested-with
            isAjax = true;
        }
        log.debug("请求类型isAjax:" + isAjax);
        if (authDateList.size() > 0 && authDateList.size() == names.length) {  //如果存在则在request上根据注解设置param

            for (int i = 0, j = authDateList.size(); i < j; i++) {
                log.debug("权限数据为：" + authDateList.get(i) + "，key：" + names[i]);
                //request.addParameters(names[i], authDateList.get(i));
                parameterMap.put(names[i], authDateList.get(i));
                changeRequestWrapper.setParameterMap(parameterMap);
            }

            return true;

        } else {
            log.debug("权限数据为空");
            if (isAjax) {
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.append("无权限");
            } else {
                response.sendRedirect("/errorHtml/errorHtml");
            }
            return false;
        }
    }
}
*/
