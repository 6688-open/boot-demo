package com.dj.boot.configuration.filter.service;

import com.dj.boot.configuration.permission.domain.PermissionType;

import javax.servlet.http.HttpServletRequest;

public interface WarehouseUriService {

    String[] authData(HttpServletRequest request, PermissionType authType);
}
