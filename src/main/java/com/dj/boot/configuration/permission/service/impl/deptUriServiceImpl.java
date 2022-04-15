/*
package com.dj.boot.configuration.permission.service.impl;

import com.dj.boot.configuration.permission.domain.PermissionType;
import com.dj.boot.configuration.permission.service.PermissionUriService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service("deptUri")
public class deptUriServiceImpl implements PermissionUriService {


    @Override
    public String[] authData(HttpServletRequest request, PermissionType authType) {
        String[] depts = new String[5];
        for (int i = 0, j = 5; i < j; i++) {
            if (authType == PermissionType.ID) {
                depts[i] = String.valueOf(1);
            } else if (authType == PermissionType.NO) {
                depts[i] = "CBB000000000000001";
            }
        }

        return depts;

    }
}
*/
