package com.dj.boot.common.util.httpclient.gw.vo;

import com.dj.boot.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述信息
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-11-09-42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseVo {

    private Integer code;
    private String msg;
    private UserVo data;
}
