package com.dj.boot.configuration.filter.service;

import com.dj.boot.configuration.permission.domain.PermissionType;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.filter.service
 * @Author: wangJia
 * @Date: 2021-05-12-17-50
 */
public interface DeptUriService {
    String[] authData(HttpServletRequest request, PermissionType authType);
}
