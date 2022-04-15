package com.dj.boot.controller.page;

import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.auth.AuthReturn;
import com.dj.boot.common.base.auth.AuthValidateUtil;
import com.dj.boot.common.enums.BusinessSignEnum;
import com.dj.boot.common.enums.Option;
import com.dj.boot.common.enums.OrderTypeEnum;
import com.dj.boot.common.enums.StartOrStop;
import com.dj.boot.common.enums.base.SelectTypeEnum;
import com.dj.boot.common.util.PropertiesUtil;
import com.dj.boot.common.util.SpringContextUtil;
import com.dj.boot.configuration.quartz.util.SpringUtil;
import com.dj.boot.controller.UserController;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.enumtest.EnumController;
import com.dj.boot.controller.enumtest.EnumQueryParam;
import com.dj.boot.pojo.Person;
import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * http://localhost:8082/toLogin
 *
 * @ClassName UserPageController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 15:29
 * @Version 1.0
 **/
@Api(value = "用户跳转页面接口")
@Controller
public class UserPageController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${qiniu.url}")
    private String qiniuUrl;

    @Autowired
    private Person person;

    @Autowired
    private Environment environment;

    private static final Map<String, Object> baseDataMap;

    static {
        baseDataMap = new HashMap<>();
        baseDataMap.put("startOrStopList", StartOrStop.StartOrStop_opList);
        baseDataMap.put("orderTypeList", OrderTypeEnum.OPTIONS);
    }

    /**
     * http://localhost:8082/toLogin
     * @param model
     * @return
     */
    @ApiOperation(value = "跳转登录页面", notes="跳转登录页面")
    @GetMapping("toLogin")
    public String toLogin(Model model){
        return "login";
    }

    /**
     * 去注册
     * @return
     */
    @GetMapping("toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 忘记密码  手机号找回密码
     * @return
     */
    @GetMapping("toFindPassword")
    public ModelAndView toFindPassword(ModelAndView mav, String[] deptNoList, String[] warehouseNoList){
        String deptNo = "CBB000000000000001";
        AuthReturn authReturn = AuthValidateUtil.isContain(deptNoList, deptNo);
        if (!authReturn.isContain()) {
            return authReturn.getMav();
        }
        EnumQueryParam enumQueryParam = new EnumQueryParam(SelectTypeEnum.Select_BusinessSignEnum.getCode());
        Response response = SpringContextUtil.getBean("enumController", EnumController.class).queryEnum(enumQueryParam);

        mav.addObject("fileName","白冰.png");
        mav.addObject("businessSignList", response.getData());
        mav.setViewName("find_password");
        return mav;
    }
    /**
     * 跳转详情页
     * @return
     */
    @GetMapping("toFindDetails")
    public String toFindDetails(Model model){
        return "details";
    }
    /**
     * 跳转详情页
     * @return
     */
    @GetMapping("toFindTime")
    public String toFindTime(){
        return "times";
    }
    /**
     * 跳转展示树
     * @return
     */
    @GetMapping("toFindZTree")
    public String toFindZTree(){
        return "ztree";
    }
    /**
     * 跳转展示树
     * @return
     */
    @GetMapping("toImportExcel")
    public String toImportExcel(){
        return "importExcel";
    }
    /**
     * 测试jq 传值
     * @return
     */
    @GetMapping("transmissionValue")
    public String transmissionValue(Integer id, String year, String createTime){
        System.out.println(id);
        return "find_password";
    }

    @RequestMapping(value = "goToMainIframe", method = {RequestMethod.GET, RequestMethod.POST})
    public String goToMainIframe(HttpServletRequest request, Model model) {
        model.addAttribute("domain","domain");
        model.addAttribute("cookieName","cookieName");
        model.addAttribute("logoutName","logoutName");
        model.addAttribute("mdDomain", "mdDomain");
        model.addAttribute("mdAccount", "mdAccount");
        model.addAttribute("windowsOutUrls", "getWindowsOutUrls");
        return "mainIframe";
    }


    @ApiOperation(value = "跳转用户列表页面", notes="跳转用户列表页面")
    @GetMapping("list")
    public String list(Model model){
        List<User> list = userService.list();
        List<Map<String, String>> businessSignList = BusinessSignEnum.getBusinessSignList();
        model.addAttribute("userList",list);
        model.addAttribute("businessSignList",businessSignList);
        return "userList";
    }

    @ApiOperation(value = "获取配置文件信息 ${qiniu.url}", notes="获取配置文件信息 ${qiniu.url}")
    @GetMapping("testHtml")
    public String testHtml()  {
        System.out.println(qiniuUrl);
        System.out.println(person);
        String ips =  PropertiesUtil.getProperty("prop/ips.properties","ip00001");
        System.out.println(ips);
        //获取配置文件里的内容
        String carbonCallInterface = environment.getProperty("commercial.test");
        System.out.println(carbonCallInterface);
        return "test";
    }


    @ApiOperation(value = "测试java 反射机制 ", notes="测试java 反射机制")
    @PostMapping("testInvoke")
    public String testInvoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object o = SpringUtil.getBean("secondJop");
        //Object o = SpringUtil.getBean(SecondJop.class);
        if (Objects.isNull(o))  {
            return null;
        }
        //获取本类全部方法
        Method[] methods = o.getClass().getDeclaredMethods();
        Method method1 = o.getClass().getDeclaredMethod("execute");
        method1.invoke(o);
        for (Method method : methods) {
            method.invoke(o);
        }

        return "test";
    }



    /**
     * 跳转到保险单页面
     *
     * @return
     */
    @RequestMapping(value = "gotoInsuranceDetail.do")
    public String gotoInsuranceDetail(String id, String timestamp, Model model) {
        logger.error("id入参:{}", id);
        logger.error("timestamp入参:{}", timestamp);
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        if (!pattern.matcher(id).matches()) {
            logger.error("id入参含有特殊字符:{}", id);
            return "details";
        }
        Long aLong = Long.valueOf(id);
        UUID uuid = UUID.randomUUID();
        model.addAttribute("uuid", uuid);

        return "test";
    }


    /**
     *  http://localhost:8082/queryOrderInfo?orderNo=CYD01030020012891327&bizType=11
     * @param orderNo
     * @param bizType
     * @return
     */
    @RequestMapping(value = "queryOrderInfo", method = RequestMethod.GET)
    public ModelAndView queryOrderInfo(@RequestParam("orderNo") String orderNo, Integer bizType) {
        logger.error("queryOrderInfo======================"+orderNo);
        if(StringUtils.isBlank(orderNo) || bizType == null){
            return null;
        }
        String requestMapping = "";
        Long id = null;
        if(bizType != 13 && bizType != 14){
            id = Long.parseLong(orderNo.substring(3));
        }
        switch (bizType){
            case 1:
                requestMapping = "redirect:/user/xxxxx?id="+id+"&type=Eo";
                break;
            case 2:
                requestMapping = "redirect:/test/xxxxx?id="+id+"&type=Eo";
                break;
            case 11:
                requestMapping = "redirect:/gotoDetails?id="+id+"&type=Eo";
                break;
            case 12:
                requestMapping = "redirect:/detail/gotoDetails?id="+id+"&type=Eo";
                break;
            default:
                break;
        }
        ModelAndView mAndView = new ModelAndView(requestMapping);
        return mAndView;
    }



    /**
     * 去注册  http://localhost:8082/gotoDetails?id=1&type=2
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


    /**
     * 跳转详情页面
     */
    @RequestMapping(value = "gotoUserDetailPage", method = RequestMethod.GET)
    public String gotoUserDetailPage(Model model, String id, HttpServletRequest request) {
        if (StringUtils.isNotBlank(id)) {
            User user = SpringContextUtil.getBean("userController", UserController.class).queryUserSensitive(Integer.valueOf(id));
            //User user = userService.getById(id);
            model.addAttribute("user", user);
        }
        return "userDetails";
    }

    /**
     *  文件上传页面
     * @return
     */
    @GetMapping("toUpload")
    public String toUpload(Model model){
        return "upload/upload";
    }





}
