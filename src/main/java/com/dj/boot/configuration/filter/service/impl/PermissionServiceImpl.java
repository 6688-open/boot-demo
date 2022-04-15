package com.dj.boot.configuration.filter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.configuration.filter.permission.ChangeRequestWrapper;
import com.dj.boot.configuration.filter.service.DeptUriService;
import com.dj.boot.configuration.filter.service.PermissionService;
import com.dj.boot.configuration.filter.service.WarehouseUriService;
import com.dj.boot.configuration.permission.domain.PermissionType;
import com.dj.boot.configuration.permission.domain.PermissionUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.permission.service
 * @Author: wangJia
 * @Date: 2021-04-30-14-39
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final static Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final static String ajaxHeader = "x-requested-with";
    private final static String ajaxsign = "XMLHttpRequest";


    @Autowired
    DeptUriService deptUriService;
    @Autowired
    WarehouseUriService warehouseUriService;



    @Override
    public ChangeRequestWrapper processRequest(ServletRequest servletRequest) throws IOException {
        log.debug("设置权限数据开始");

        ChangeRequestWrapper changeRequestWrapper = new ChangeRequestWrapper((HttpServletRequest) servletRequest);
        Map<String, String[]> parameterMap = new HashMap<>(changeRequestWrapper.getParameterMap());



        //2、引用类型修改
//        String jsonData="{\"x\":1,\"y\":2}";
//        byte[] reqBodyBytes = jsonData.getBytes();
//        changeRequestWrapper.setBody(reqBodyBytes);


        String[] names = {"deptNoList","warehouseNoList"};
        PermissionType[] authTypes = {PermissionType.NO,PermissionType.NO};
        PermissionUri[] authUris = {PermissionUri.dept,PermissionUri.warehouse};
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
                        authDate = deptUriService.authData(null, authTypes[i]);
                        break;
                    case warehouse:
                        authDate = warehouseUriService.authData(null, authTypes[i]);
                        break;
                    default:
                        break;
                }
                if (authDate != null && authDate.length > 0) {
                    authDateList.add(authDate);
                }
            }

        }

        if (authDateList.size() > 0 && authDateList.size() == names.length) {  //如果存在则在request上根据注解设置param
            for (int i = 0, j = authDateList.size(); i < j; i++) {
                log.debug("权限数据为：" + JSONObject.toJSONString(authDateList.get(i)) + "，key：" + names[i]);
                //1、值类型参数修改
                parameterMap.put(names[i], authDateList.get(i));
                changeRequestWrapper.setParameterMap(parameterMap);
            }
        }

        return changeRequestWrapper;
    }
}
