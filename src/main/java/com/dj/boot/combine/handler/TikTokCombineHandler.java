package com.dj.boot.combine.handler;


import com.dj.boot.combine.CombineService;
import com.dj.boot.combine.dto.Command;
import com.dj.boot.combine.dto.Result;
import com.dj.boot.combine.handler.enums.HandlerType;

/**
 * @Author: wangJia
 * @Date: 2022-04-13-10-41
 */
@HandlerType(businessType = "TIKTOK")
public class TikTokCombineHandler implements CombineService {


    @Override
    public Result<String> execute(Command<String> command) {
        System.out.println("TikTokCombineHandler");
        return null;
    }
}
