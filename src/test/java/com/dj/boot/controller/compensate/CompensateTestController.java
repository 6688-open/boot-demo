package com.dj.boot.controller.compensate;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.pojo.compensate.CompensateItem;
import com.dj.boot.pojo.compensate.CompensateItemCondition;
import com.dj.boot.service.compensate.CompensateItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangJia
 * @Date: 2021-09-01-11-39
 */
public class CompensateTestController extends BootDemoApplicationTests {

    @Autowired
    private CompensateItemService compensateItemService;

    @Override
    public void run() throws Exception {
        CompensateItem compensateItem = new CompensateItem();


        CompensateItemCondition condition = new CompensateItemCondition();
        condition.setCompensateId(4444L);
        condition.setClaimId("4444");
        List<CompensateItem> compensateItemList = compensateItemService.getCompensateItemList(condition);

        compensateItem.setId(111L);
        compensateItem.setCompensateId(20000000338L);
        compensateItem.setClaimId("CA1111111111111");
        compensateItem.setCreateTime(new Date());
        compensateItem.setUpdateTime(new Date());
        compensateItem.setGoodOwner(1);
        compensateItem.setGoodOwnerName("商家A");
        compensateItem.setPaymentRealMoney(new BigDecimal(3.11));
        compensateItem.setCreateUser("system");
        compensateItem.setUpdateUser("system");
        compensateItem.setItemStatus(20);
        compensateItem.setFirstClaimCause("1111");
        compensateItem.setSecondClaimCause("1111");
        compensateItem.setYn((byte)1);
        System.out.println(JSONObject.toJSONString(compensateItem));

        compensateItemService.insertCompensateItem(compensateItem);


        compensateItem.setClaimId("22222222");
        compensateItem.setCreateTime(new Date());
        compensateItem.setUpdateTime(new Date());
        compensateItem.setGoodOwner(2);
        compensateItem.setGoodOwnerName("2");
        compensateItem.setPaymentRealMoney(new BigDecimal(2.22));
        compensateItem.setCreateUser("system");
        compensateItem.setUpdateUser("system");
        compensateItem.setItemStatus(2);
        compensateItem.setFirstClaimCause("2222");
        compensateItem.setSecondClaimCause("2222");
        compensateItemService.updateCompensateItem(compensateItem);
    }
}
