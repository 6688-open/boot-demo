package com.dj.boot.service.master;

import com.dj.boot.pojo.master.DeptEntity;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.service.master
 * @Author: wangJia
 * @Date: 2021-12-23-11-07
 */
public interface MasterService {
    /**
     * 获取事业部
     *
     * @param deptNo 编码
     * @return 事业部
     */
    DeptEntity getDept(String deptNo);
}
