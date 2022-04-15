package com.dj.boot.controller.echars;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.echars
 * @Author: wangJia
 * @Date: 2021-03-26-17-47
 */

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.echars.EcharsDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/echars/")
public class EcharsController extends BaseController {

    @RequestMapping(value = "echarsShow")
    @ResponseBody
    public List<EcharsDto> findById(Model model) {
        List<EcharsDto> list = new ArrayList<>();
        list.add(new EcharsDto("帽子",50));
        list.add(new EcharsDto("鞋子",126));
        list.add(new EcharsDto("毛衣",75));
        list.add(new EcharsDto("羽绒服",201));
        list.add(new EcharsDto("羊毛衫",172));
        logger.error("数据:{}", JSONObject.toJSONString(list));
        return list;
    }

    @GetMapping(value = "toEchars")
    public String toEchars(Model model){
        System.err.println("========开始");
        model.addAttribute("orderNo","CXXXX100000000000");
        model.addAttribute("orderType","11");
        return "echars";
    }

    @RequestMapping(value = "queryUserInfo.do")
    @ResponseBody
    public Map<String, Object> queryUserInfo(User user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(user.getUserName())) {
            return null;
        }
        try {
            user.setPhone("18351867657");
            map.put("phone", "18351867657");
            return map;
        } catch (Exception e) {
            throw new RuntimeException("查询异常");
        }
    }
}
