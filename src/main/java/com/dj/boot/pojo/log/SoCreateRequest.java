/**
  * Copyright 2018 bejson.com 
  */
package com.dj.boot.pojo.log;

import com.dj.boot.pojo.User;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2018-01-19 13:44:52
 *
 * @website http://www.bejson.com/java2pojo/
 */
/*@XStreamAlias("request")*/
public class SoCreateRequest {
    /**
     * 用户主体信息
     */
    private User userDeliveryOrder;

    public User getUserDeliveryOrder() {
        return userDeliveryOrder;
    }

    public void setUserDeliveryOrder(User userDeliveryOrder) {
        this.userDeliveryOrder = userDeliveryOrder;
    }
}