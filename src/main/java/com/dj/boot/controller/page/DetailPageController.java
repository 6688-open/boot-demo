package com.dj.boot.controller.page;

import com.dj.boot.common.base.Response;
import com.dj.boot.common.enums.OrderTypeEnum;
import com.dj.boot.common.enums.StartOrStop;
import com.dj.boot.controller.base.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-17-21-22
 */
@Api(value = "用户跳转页面接口")
@Controller
@RequestMapping("/detail/")
public class DetailPageController extends BaseController {

    private static final Map<String, Object> baseDataMap;

    static {
        baseDataMap = new HashMap<>();
        baseDataMap.put("startOrStopList", StartOrStop.StartOrStop_opList);
        baseDataMap.put("orderTypeList", OrderTypeEnum.OPTIONS);
    }

    /**
     * 去注册
     * @return
     */
    @GetMapping("gotoDetails")
    public ModelAndView gotoDetails(String id, String type, ModelAndView mav){
        logger.error("id={}, type = {}:", id, type);
        mav.addObject("shipType","000111");
        if (StringUtils.isNotBlank(type)) {
            mav.setViewName("eo/register");
        } else {
            mav.setViewName("register");
        }
        mav.addAllObjects(baseDataMap);
        return mav;
    }


    @PostMapping(value = "getData")
    @ResponseBody
    public Response<Object> getData(String id) throws Exception {
        return Response.success(200,"success", "123456");
    }
}
