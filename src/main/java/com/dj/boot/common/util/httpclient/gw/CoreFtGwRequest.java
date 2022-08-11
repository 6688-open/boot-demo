package com.dj.boot.common.util.httpclient.gw;

import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.httpclient.gw.vo.UserResponseVo;
import com.dj.boot.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/***
 * 核心网关服务
 *
 * @author wangjia@fescotech.com
 * @Date: 2022/6/10
 */
@Component
@Slf4j
public class CoreFtGwRequest extends AbstractFtGwRequest {


    public UserResponseVo userEcho(User user) throws Exception {
        return this.doPost(Urls.USER_ECHO.getUrl(), user, UserResponseVo.class);
    }
}
