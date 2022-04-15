/*
package com.dj.boot.configuration.permission.service.impl;

import com.dj.boot.configuration.permission.domain.PermissionType;
import com.dj.boot.configuration.permission.interceptor.ParameterRequestWrapper;
import com.dj.boot.configuration.permission.service.PermissionService;
import com.dj.boot.configuration.permission.service.PermissionUriService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

*/
/**
 * Created with IntelliJ IDEA.
 *//*

@Service("warehouseUri")
public class WarehouseUriServiceImpl implements PermissionUriService {
    @Resource
    private PermissionService permissionService;

    @Override
    public String[] authData(HttpServletRequest request, PermissionType authType) {

        String[] warehouseNos = new String[5];
        for (int i = 0, j = 5; i < j; i++) {
            if (authType == PermissionType.ID) {
                warehouseNos[i] = String.valueOf(1);
            } else if (authType == PermissionType.NO) {
                warehouseNos[i] = "WN000003";
            }
        }

        return warehouseNos;

    }
}
*/
