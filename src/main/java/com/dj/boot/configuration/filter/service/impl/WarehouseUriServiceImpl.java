package com.dj.boot.configuration.filter.service.impl;

import com.dj.boot.configuration.filter.service.WarehouseUriService;
import com.dj.boot.configuration.permission.domain.PermissionType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class WarehouseUriServiceImpl implements WarehouseUriService {

    @Override
    public String[] authData(HttpServletRequest request, PermissionType authType) {
        String[] warehouseNos = new String[5];
        for (int i = 0, j = 5; i < j; i++) {
            if (authType == PermissionType.ID) {
                warehouseNos[i] = String.valueOf(i);
            } else if (authType == PermissionType.NO) {
                warehouseNos[i] = "WN00000"+i;
            }
        }
        return warehouseNos;
    }
}
