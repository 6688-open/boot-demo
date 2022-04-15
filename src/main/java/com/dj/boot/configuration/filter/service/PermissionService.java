package com.dj.boot.configuration.filter.service;


import com.dj.boot.configuration.filter.permission.ChangeRequestWrapper;

import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.permission.service
 * @Author: wangJia
 * @Date: 2021-04-30-14-38
 */
public interface PermissionService {

    ChangeRequestWrapper processRequest(ServletRequest servletRequest) throws IOException;
}
