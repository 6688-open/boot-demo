package com.dj.boot.configuration.filter.service.impl;

import com.dj.boot.configuration.filter.service.DeptUriService;
import com.dj.boot.configuration.permission.domain.PermissionType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class DeptUriServiceImpl implements DeptUriService {


    @Override
    public String[] authData(HttpServletRequest request, PermissionType authType) {
        String[] depts = new String[5];
        for (int i = 0, j = 5; i < j; i++) {
            if (authType == PermissionType.ID) {
                depts[i] = String.valueOf(i);
            } else if (authType == PermissionType.NO) {
                depts[i] = "CBB00000000000000"+i;
            }
        }

        return depts;

    }
}
