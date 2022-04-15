package com.dj.boot.service.log;

import com.dj.boot.common.base.Response;
import com.dj.boot.pojo.log.SoCreateRequest;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-01-24-18-51
 */
public interface StockService {


    Response transportSoOrder(String pin, SoCreateRequest soCreateRequest);


}
