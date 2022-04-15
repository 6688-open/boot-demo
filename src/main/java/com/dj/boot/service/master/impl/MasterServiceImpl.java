package com.dj.boot.service.master.impl;

import com.dj.boot.btp.common.util.idUtil.SomsNoUtil;
import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.btp.common.util.noUtil.OrderNoUtil;
import com.dj.boot.pojo.master.Dept;
import com.dj.boot.pojo.master.DeptEntity;
import com.dj.boot.service.master.MasterService;
import com.dj.boot.service.master.covertor.DeptConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.service.master.impl
 * @Author: wangJia
 * @Date: 2021-12-23-11-07
 */
@Component
public class MasterServiceImpl implements MasterService {


    @Override
    public DeptEntity getDept(String deptNo) {
        Long deptId = OrderNoUtil.reverseNo2Id(deptNo, Constant.BizType.CBU_SERVICE);
        Dept dept = new Dept();
        dept.setId(1L);
        dept.setDeptNo("CBU22222222222");
        dept.setDeptName("事业部");
        dept.setBdOwnerNo("业主号");
        //deptService.getDept(deptId);
        return Optional.ofNullable(dept).map(DeptConverter.INS::toDomain).orElse(null);
    }
}
