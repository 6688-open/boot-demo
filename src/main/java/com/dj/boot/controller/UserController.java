package com.dj.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.boot.adapter.WayBillServiceAdapter;
import com.dj.boot.adapter.WayBillServiceAdapterHolder;
import com.dj.boot.annotation.Login;
import com.dj.boot.annotation.privacy.PrivacyMethod;
import com.dj.boot.annotation.sensitive.SensitiveMask;
import com.dj.boot.aspect.hodler.LoginContext;
import com.dj.boot.aspect.hodler.LoginUserContextHodler;
import com.dj.boot.aspect.hodler.PermissionContext;
import com.dj.boot.btp.common.util.markutil.InBoundMarkEnum;
import com.dj.boot.btp.common.util.markutil.MarkUtils;
import com.dj.boot.btp.common.util.sensitive.sensitiveutil.SensitiveUtil;
import com.dj.boot.btp.common.util.sensitive.type.DataMethodType;
import com.dj.boot.btp.exception.BizException;
import com.dj.boot.btp.exception.ParamException;
import com.dj.boot.combine.CombineService;
import com.dj.boot.combine.dto.BusinessTypeEnum;
import com.dj.boot.combine.dto.Command;
import com.dj.boot.combine.dto.Result;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.page.PageRequestParam;
import com.dj.boot.common.converter.StringToDateConverter;
import com.dj.boot.common.excel.exc.ExcelType;
import com.dj.boot.common.excel.exc.ExcelUtil;
import com.dj.boot.common.mapper.convert.UserToUserDtoConvert;
import com.dj.boot.common.util.*;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.common.util.cookie.CookieUtils;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.common.util.md5.PasswordSecurityUtil;
import com.dj.boot.configuration.dispatch.proxy.ProxyDispatch;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.so.constant.SoErrResume;
import com.dj.boot.helper.TestHelper;
import com.dj.boot.helper.UserHelper;
import com.dj.boot.pojo.KdnTrackReqDto;
import com.dj.boot.pojo.TenantDto;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.pojo.log.SoCreateRequest;
import com.dj.boot.pojo.master.DeptEntity;
import com.dj.boot.pojo.useritem.UserItem;
import com.dj.boot.pojo.validate.GroupInterface;
import com.dj.boot.service.async.AsyncService;
import com.dj.boot.service.log.StockService;
import com.dj.boot.service.master.MasterService;
import com.dj.boot.service.user.UserService;
import com.dj.boot.service.user.impl.UserAdapter;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.util.StringValueResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *dispatcherServlet 查询List<HandlerMapping> handlerMappings 首先查询 BeanNameUrlHandlerMapping 再查询RequestMappingHandlerMapping
 *
 * RequestMappingHandlerMapping
 *              map集合是如何将RequestMapping注解对应的方法存进去的
 *              实现了InitializingBean接口
 *                      afterPropertiesSet()
 *                          从容器中获取所有候选的beanName 根据名称获取类型
 *                          isHandler 判断该类上是否@Controller @RequestMapping 的注解
 *                          反射获取该类上所有的方法 获取所有存在@RequestMapping注解的方法
 *                          遍历符合条件的方法
 *                                  将@RequestMapping封装成对象    controller和方法上的@RequestMapping url拼接 RequestMappingInfo  /user/delete
 *                                  将controller和method 封装成对象 HandlerMethod (controller和method 信息 userController delete)
 *                                  将 RequestMappingInfo为key HandlerMethod value 存储到map集合里面 mappingLookup
 *                                  将url [/user/delete]为key RequestMappingInfo 为 value  存储到map集合里面 urlLookup
 *
 *                  HandlerMappings映射器
 *                      一个url[/user/delete] 过来，先从urlLookup这个集合中找到@requestMapping信息封装的对象，
 *                      然后以该对象作为key,从mappingLookup中获取对应的controller和方法 找到指定的Handler后
 *
 *                          三种方式通过url获取到的handler最终被封装成了HandlerExecutionChain
 *                  HandlerAdapters适配器
 *                          查找适配器过程
 *                              userController 第一种controller实现方法，HandlerExecutionChain的handler属性对象其实是HandlerMethod对象，
 *                              MyController   第二种方式，就是Controller接口的实现类对象，
 *                              MyController2  第三种方式：HttpRequestHandler接口实现类对象
 *
 *                          找处理方式的过程就是适配过程，也可以叫做查找适配器过程，给适配器起名叫做HandlerAdapter，
 *                          至少存在3种HandlerAdapter实现类，该接口要提供一个方法，判断支不支持当前处理器，支持，就可以调用该类的另一个执行处理器的方法
 *
 *
 *                          实现RequestMapping注解的方法，因为参数是多样化的：RequestMappingHandlerAdapter
 *                          实现Controller接口方式的controller对应的适配器：SimpleControllerHandlerAdapter
 *                          实现HttpRequestHandler接口的方式：HttpRequestHandlerAdapter
 *
 *                          通过url找到一个handler，并且如何通过hanlder找到执行器，
 *                          大概流程是 : url->分别判断BeanNameUrlHandlerMapping和RequestMappingHandlerMapping找到对应hanlder
 *                          ->分别判断HttpRequestHandlerAdapter，SimpleControllerHandlerAdapter，RequestMappingHandlerAdapter
 *                          找到对应的适配器进行方法调用；
 *
 *                viewResolvers视图解析器
 *
 *
 *      我们上面分析的HandlerMapping和HandlerAdapter是什么时候添加到DispathcerServlet的呢
 *
 *              WebMvcAutoConfiguration
 *                      WebMvcConfigurationSupport
 *                              RequestMappingHandlerMapping
 *                              BeanNameUrlHandlerMapping
 *
 *                              RequestMappingHandlerAdapter
 *                              SimpleControllerHandlerAdapter
 *                              HttpRequestHandlerAdapter
 *
 *                              ViewResolver
 *                              等等 注入到spring中
 *
 *  请求过来  DispatcherServlet实现了父类 FrameworkServlet
 *                  FrameworkServlet 的doGet/doPost。。。。。
 *                          processRequest
 *                              请求对象封装到request中 传入doService
 *          DispatcherServlet
 *                  doService 初始主题/国际化解析器  准备工作
 *                      doDispatch 核心
 *                              校验请求是不是文件上传类型
 *                              通过请求对象的url,遍历各个HandlerMapping，找到对应的Handler
 *                              通过找到的Handler，遍历HandlerAdapter,看看哪个可以执行该Handler的方法
 *                              执行拦截器链条的所有前置拦截处理
 *                              执行目标方法，得到ModleAndView对象
 *                              执行拦截器的后置处理方法
 *                              处理返回结果，如进行视图渲染 获取view的 name
 *
 *
 *
 *
 * 用户控制器
 * @author wangjia
 */
@Api(value = "用户操作接口")
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController implements EmbeddedValueResolverAware {


    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;
    @Autowired
    private AsyncService asyncService;
    @Value("${qiniu.url}")
    private String qiniuUrl;
    @Value("${actualFlag}")
    private Boolean actualFlag;
    @Value("${j.as}")
    private String alias;
    @Value("${j.version}")
    private String version;
    @Resource
    private UserAdapter userAdapter;
    @Resource
    private UserHelper userHelper;
    @Resource
    private MasterService masterService;
    @Resource(name ="wayBillServiceAdapterHolder")
    private WayBillServiceAdapterHolder wayBillServiceAdapterHolder;
    @Resource
    private StockService stockService;

    private StringValueResolver stringValueResolver;

    @Resource(name = "testKeyConfig")
    private Map<Integer, String> testKeyConfig;



    /**
     * 假如配置文件里面有e.lHost则从配置文件里读取
     * 否则就是默认值 http://baidu.com/interface/register
     */
    @Value("${e.lHost:http://baidu.com/interface/register}")
    private String loginHost;
    @Autowired
    private TestController testController;
    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    List<User> userList = Lists.newArrayList();
    /**
     * 是Java自己的注解
     * 该注解@PostConstruct被用来修饰一个非静态的void（）方法。
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行
     * BeanPostProcessor有个实现类CommonAnnotationBeanPostProcessor 继承 InitDestroyAnnotationBeanPostProcessor，就是专门处理@PostConstruct  @PreDestroy注解。
     *
     */
    @PostConstruct
    private void getData() {
        userList.add(new User(1,2, "女", "234598765432",  "2222222"));
        userList.add(new User(2,2, "女", "234598765432",  "2222222"));
        logger.error("初始化的后置方法 init：{}", JSONObject.toJSONString(userList));
    }

    /**
     * 脱敏
     * @param id
     * @return
     */
    @SensitiveMask(value = DataMethodType.PERCOLATE_VIEW)
    public User queryUserSensitive(Integer id){
        return userService.getById(id);
    }
    @SensitiveMask(value = DataMethodType.PERCOLATE_LIST)
    public List<User> queryUserListSensitive(Integer id){
        List<User> list = Lists.newArrayList();
        list.add(userService.getById(id));
        return list;
    }

    /**
     * 点击查看
     * @param request
     * @param id
     * @param fieldNames
     * @return
     */
    @PostMapping("queryCommonSensitive")
    public Response<Object> queryCommonSensitive (HttpServletRequest request,String id,String fieldNames) {
        Response<Object> response = Response.success();
        User user = this.getById(Integer.valueOf(id));
        response.setData(SensitiveUtil.putValuesIntoMap(id, fieldNames, user));
        return response;
    }

    /**
     *
     *  @ProxyDispatch("alien") 代理
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("userListAlien")
    @ProxyDispatch("alien")
    public Response userListAlien(User user) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSex(3);
        int pageNo = 1;
        int pageSize = 10;
        try {
            Page<User> page = new Page<>();
            //设置当前页
            page.setCurrent(pageNo);
            //每页条数
            page.setSize(pageSize);
            Page<User> pageInfo = userService.findUserListPage(page, userDto);
            return Response.success(pageInfo);
        } catch (Exception e) {
            logger.error("获取用户列表异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM,"异常"+e.getMessage());
        }
    }

    /**
     * 测试
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("echo")
    public Response<User> echo(@RequestBody User user) throws Exception {

        String configStr = testKeyConfig.get(1);
        String[] splits = configStr.split(",");
        logger.error("配置信息:{},{}",splits[0], splits[1]);


        String topic = stringValueResolver.resolveStringValue("${ip00001}");

        /*适配模式*/
        WayBillServiceAdapter adapter = wayBillServiceAdapterHolder.getBizWayBillServiceAdapterByShipper("19");
        String str = adapter.queryWbMainBySoNo("sono");

        /*日志接口*/
        SoCreateRequest soCreateRequest = new SoCreateRequest();
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("3100");
        user1.setPassword("123456");
        soCreateRequest.setUserDeliveryOrder(user1);
        stockService.transportSoOrder("wj", soCreateRequest);
        /*JDK1.8*/
        DeptEntity deptEntity = masterService.getDept("CBU12345678");
        logger.error("deptEntity:{}", JSONObject.toJSONString(deptEntity));
        /*转换器*/
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToDateConverter());
        Date date = conversionService.convert("2021-10-12 12:12:12", Date.class);

        String result1 = HttpUtil.sendPost("http://localhost:8082/user/testParam");
        UserDto userDto = new UserDto();
        String result2 = HttpUtil.sendPost("http://localhost:8082/user/list",JSONObject.toJSONString(userDto),5,5);
        String result3 = HttpUtil.sendPost("http://localhost:8082/user/test",JSONObject.toJSONString(user),5,5);
        //User sensitive = SpringContextUtil.getBean("userController", UserController.class).queryUserSensitive(1);
        List<User> userList = SpringContextUtil.getBean("userController", UserController.class).queryUserListSensitive(1);
        logger.error(JSONObject.toJSONString(userList));
        Response<String> test = testController.test("2222");
        String userInfo = TestHelper.getUserInfo();
        String userInfo1 = userHelper.getUserInfo();
        Response<User>  response = Response.success();
        return response;
    }

    /**
     * 测试
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("test")
    @Login
    public Response<KdnTrackReqDto> test(@RequestBody User user) throws Exception {

        if (logger.isInfoEnabled()) {
            logger.info("UserController --> test:{}", JSONObject.toJSONString(user));
        }

        if (Objects.nonNull(user.getOperateTime())) {
            Date operateTime = user.getOperateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            System.out.println("把当前时间转换成字符串：" + sdf.format(operateTime));
        }

        Response<KdnTrackReqDto>  response = Response.success();
        try {
            Response<String> generateOrderResponse = userAdapter.generateOrder();
            if (generateOrderResponse.isSuccess() && null != generateOrderResponse.getData()) {
                logger.info(generateOrderResponse.getData());
            }
            KdnTrackReqDto kdnTrackReqDto = new KdnTrackReqDto();
            kdnTrackReqDto.setCustomerName("18311113333");
            response.setData(kdnTrackReqDto);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }

    /**
     * 应急脱敏组件
     * @return 返回数据
     */
    @ApiOperation(value = "应急脱敏组件测试")
    @PostMapping("testPrivacyMethod")
    @PrivacyMethod
    public Response<KdnTrackReqDto> testPrivacyMethod(@RequestBody User user) throws Exception {

        Response<KdnTrackReqDto>  response = Response.success();
        try {
            KdnTrackReqDto kdnTrackReqDto = new KdnTrackReqDto();
            kdnTrackReqDto.setCustomerName("18311113333");
            response.setData(kdnTrackReqDto);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }

    /**
     *  queryUserList
     * @param request
     * @return
     */
    @PostMapping("findUserListPage")
    public PageRequestParam<User> findUserListPage(String aoData, HttpServletRequest request, UserDto userDto) throws Exception {
        // String referer = request.getHeader("Referer"); 通过referer 它记录了该HTTP请求的来源地址 判断请求来自于哪个地址 可以防止CSRF攻击
        PageRequestParam<User> pageParams = new PageRequestParam<>();
        try {
            Page<User> page = new Page<>();
            //导出专用
            if (StringUtils.isNotBlank(aoData)) {
                pageParams =  (PageRequestParam<User>) PageRequestParam.getPageParams(aoData);
                page.setCurrent(pageParams.getiDisplayStart() == 0 ? 1: pageParams.getiDisplayStart()/pageParams.getiDisplayLength()+1);//设置当前页
                page.setSize(pageParams.getiDisplayLength()); //每页条数
            } else {
                page.setCurrent(1);//设置当前页
                page.setSize(Integer.MAX_VALUE); //每页条数
            }
            logger.error("queryUserList入参:{}", JSONObject.toJSONString(pageParams));
            translateLineToColumn(userDto);
            Page<User> pageInfo = userService.findUserListPage(page, userDto);
            //分页结果
            pageParams.setAaData(translatePageResult(pageInfo.getRecords()));
            pageParams.setiTotalRecords((int) pageInfo.getTotal());
        } catch (Exception e) {
            logger.error("查询异常:{}", e.getMessage());
            throw new RuntimeException("查询异常");
        }
        return pageParams;
    }


    private List<User> translatePageResult(List<User> userList) {
        if (CollectionUtils.isNullOrEmpty(userList)) {
            return userList;
        }
        userList.forEach(user -> {
            if(null != user.getCreateTime()){
                user.setCreateTimeStr(pattern.format(user.getCreateTime()));
            }

        });
        return userList;
    }

    /**
     * 测试 行转列  动态列实现
     * @param userDto
     */
    private void translateLineToColumn(UserDto userDto){
        List<String> p = Lists.newArrayList();
        p.add("2021");
        p.add("2022");
        p.add("2020");
        userDto.setPasswordList(p);
        List<User> userList = userService.queryUserTranslateLineToColumn(userDto);
        logger.error("查询结果:{}", JSONObject.toJSONString(userList));
        userDto.setPasswordList(null);
    }



    /**
     * 登录
     * @return 返回数据
     */
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Response<Object> login(HttpServletRequest request, HttpServletResponse response, String userName, String password){

        String deptPo = request.getParameter("deptPo");
        String ipAddress = HttpRequestUtil.getIpAddress(request);
        System.out.println(ipAddress+"--------------------------------------------");
        Map<String, Object> map = new HashMap<>(16);
        try {
            if (StringUtils.isBlank(userName)) {
                throw new IllegalArgumentException("缺失参数【userName】");
            }
            User user = userService.findUserByUsernameAndPassword(userName);
            if (Objects.isNull(user)){
                return Response.error(BaseResponse.ERROR_BUSINESS, "用户不存在");
            }
            //加盐    MD5  加盐 判断密码是否一致
            String salt = user.getSalt();
            //前台加密后 +  盐  ----->加密    最终密码
            String pwd = PasswordSecurityUtil.generatePassword(password, salt);
            if(!user.getPassword().equals(pwd)) {
                return Response.error(BaseResponse.ERROR_PARAM, "密码输入错误");
            }
            setCookie(request, response, user);
            return Response.success(map);
        } catch (Exception e) {
            logger.error("注册用户异常:{}", e.getMessage());
            String msg = e.getMessage();
            if (e instanceof IllegalArgumentException) {
                msg = e.getMessage();
            }
            return Response.error(BaseResponse.ERROR_SYSTEM, "异常" + msg);
        }
    }

    private void setCookie(HttpServletRequest request, HttpServletResponse response, User user) {
        CookieUtils.setCookie(request, response, "user", JSONObject.toJSONString(user), true);
        CookieUtils.setCookie(request, response, "csrfToken", UUIDUtil.getTerseUuid(), true);
    }

    private void checkParam(User user){
        Preconditions.checkArgument(user != null, "参数不能为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(user.getUserName()),"用户名不为空");
    }

    /**
     * 参数校验
     * @param user
     * @return
     */
    private Response<User> validComponent (User user) {
        //只会校验加了GroupInterface标识的字段属性
        //ValidatorUtil.Result result = ValidatorUtil.validate(user, GroupInterface.class);
        //只会校验原生的 不加任何标识 不会校验加了GroupInterface 。的字段属性
        ValidatorUtil.Result result = ValidatorUtil.validate(user);
        if (!result.isPass()) {
            logger.error("用户校验结果："+ StringUtils.join(result.getErrMsgList(), ";"));
            return Response.success(BaseResponse.ERROR_BUSINESS,StringUtils.join(result.getErrMsgList(), ";"));
        }
        return Response.success();
    }

    /**
     * user列表展示
     * @return 返回数据
     */
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public Response register(User user, String fileListJson){
        logger.error(fileListJson);
        //1、入参校验
        Response<User> validResponse = validComponent(user);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //非空校验
        checkParam(user);
        Map<String, Object> map = new HashMap<>();
        try {
            //生成保存盐
            String salt = PasswordSecurityUtil.generateSalt();
            String overPassword = PasswordSecurityUtil.generatePassword(user.getPassword(), salt);
            user.setPassword(overPassword);
            user.setSalt(salt);
            user.setCreateTime(LocalDateTime.now());
            user.setEoType((byte)4);
            user.setEoMark(new MarkUtils().toString());
            userService.save(user);
            return Response.success(205,"success",map);
        } catch (Exception e) {
            logger.error("注册用户异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM,"异常"+e.getMessage());
        }
    }


    /**
     * 注册去重
     * @param userName 用户名
     * @param phone  手机号
     * @param email  邮箱
     * @return
     */
    @PostMapping("uniq")
    public Boolean uniq(String userName, String phone, String email){
        try{
            // User user = userService.findUserToUniq(userName, phone, email);
            //判断是否有值
//            if(null != user){
//                return false;//失败
//            }
            return true;//成功
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }





    /**
     * 删除
     * @return 返回数据
     */
    @ApiOperation(value = "删除")
    @PostMapping("deleteUser")
    public Response<Object> deleteUser(Integer id){
        Response<Object> response = Response.success();
        try {
            boolean remove = userService.removeById(id);
            if (!remove) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("数据不存在");
            }
        } catch (Exception e) {
            logger.error("注册用户异常:{}", e.getMessage());
            response.setCode(BaseResponse.ERROR_PARAM);
            response.setMsg(BaseResponse.DEFAULT_MESSAGE);
        }
        return response;
    }






    @PostMapping("updateBatchSaleableWarehouseStock")
    public Response<User> updateBatchSaleableWarehouseStock() throws Exception {

        Response<User>  response = Response.success();
        UserDto userDto = new UserDto();
        userDto.setEoType("4");

        List<User> list = userService.findUserListByCondition(userDto);
        List<Integer> ids = list.stream().map(User::getId).collect(Collectors.toList());
        Integer count = userService.updateBatchSaleableWarehouseStock(list, ids);
        logger.error("修改成功改的数量{}:", count);
        return response;
    }



    /**
     *
     *  @ProxyDispatch("alien") 代理
     * @param user2
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "从配置文件里读取信息--- 用户列表", notes="从配置文件里读取信息--- 用户列表")
    @GetMapping("userList")
    @ProxyDispatch("alien")
    public Response userList(@RequestBody @Validated(GroupInterface.class) User user2 ) throws Exception {
        //通过beanID 从IOC 获取bean  在controller 调用 里面的方法
        User user1 = SpringContextUtil.getBean("userController", UserController.class).getById(1);
        System.out.println(user1);


        //存储登录信息 权限信息
        if (LoginUserContextHodler.get().getPermissionContext() == null) {
           logger.error("error");
        }
        //获取上下文
        PermissionContext permissionContext = LoginUserContextHodler.get().getPermissionContext();
        LoginContext loginContext = LoginUserContextHodler.get().getLoginContext();

        /**
         * 校验入参
         */
        Response<User> validResponse = validComponent(user2);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        Page<User> page = new Page<>();
        //设置当前页
        page.setCurrent(1);
        //每页条数
        page.setSize(10);

        UserDto userDto = new UserDto();
        userDto.setEmail("183@163.com");
        userDto.setId(2);
        //e, UserDto userDto

        User u = new User();


        List<User> userList = userService.getList(page, userDto, u);
        userList.forEach(user -> u.setPassword("222222"));

        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("111", "22222");


        //获取配置文件里的内容
        String carbonCallInterface = environment.getProperty("commercial.test");
        System.out.println(qiniuUrl);
        System.out.println(alias);
        System.out.println(actualFlag);
        System.out.println("loginHost: "+loginHost);
        System.out.println("version: "+version);
        System.out.println(carbonCallInterface);
//        List<User> list = userService.list();
//        User user = new User();
//        BeanUtils.copyProperties(userVo, user);
//        Integer pageNo = 1;
//        return Response.success(userService.findUserAll(pageNo, user));
        return Response.success(map);
    }




    /**
     * user列表展示
     * @return 返回数据
     */
    @ApiOperation(value = "mapper 用户列表", notes="用户列表")
    @PostMapping("list")
    public Response list(UserDto userDto){

        userDto.setCreateTime(LocalDateTime.now());

        userDto.setReturnFlag(false);
        userDto.setSex(3);



        int pageNo = 1;
        int pageSize = 10;
        Map<String, Object> map = new HashMap<>();
        try {
            Page<User> page = new Page<>();
            //设置当前页
            page.setCurrent(pageNo);
            //每页条数
            page.setSize(pageSize);
            Page<User> pageInfo = userService.findUserListPage(page, userDto);
            //分页结果
            map.put("list", pageInfo.getRecords());
            map.put("totalPage", pageInfo.getPages());
            map.put("pageNo", pageNo);

            return Response.success(map);
        } catch (Exception e) {
            logger.error("获取用户列表异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM,"异常"+e.getMessage());
        }
    }


    /**
     * 批量修改
     * @return 返回数据
     */
    @ApiOperation(value = "批量修改", notes="批量修改")
    @PostMapping("batchUpdate")
    public Response batchUpdate(){
        Response<Object> response = Response.success(Response.SUCCESS_CODE, Response.SUCCESS_MESSAGE);

        List<User> list = Lists.newArrayList();
        User user = new User().setUserName("wj-01").setPassword("123-01").setId(11290);
        User user1 = new User().setUserName("wj-02").setPassword("123-02").setId(11291);
        list.add(user);
        list.add(user1);
        //Integer integer = userService.updateBatch(list);

        List<String> nameList = Lists.newArrayList();
        nameList.add("wj-01");
        nameList.add("wj-02");
        userService.updateEventOrderStatusByEoNos(nameList);

        /*if (integer < 0) {
            response.setCode(Response.ERROR_BUSINESS);
            return response;
        }*/
        return response;
    }


    /**
     * 批量添加
     * @return 返回数据
     */
    @ApiOperation(value = "批量添加", notes="批量添加")
    @PostMapping("insertBatch")
    public Response insertBatch(){
        Response<Object> response = Response.success();

        List<User> list = Lists.newArrayList();
        User user = new User().setUserName("insertBatch-wj-01").setPassword("insertBatch-123-01").setId(11146).setSex(1).setSalt("12345fgh45").setCreateTime(LocalDateTime.now());
        User user1 = new User().setUserName("insertBatch-wj-02").setPassword("insertBatch-123-02").setId(11147).setSex(2).setSalt("12345fgh45").setCreateTime(LocalDateTime.now());
        list.add(user);
        list.add(user1);
        Integer integer = userService.insertBatch(list);

        if (integer < 0) {
            response.setCode(Response.ERROR_BUSINESS);
            return response;
        }
        return response;
    }







    @ApiOperation(value = "测试return finally执行顺序", notes="测试return finally执行顺序")
    @GetMapping("testFinally")
    public List<User> testFinally(){
        //先执行return，把返回结果保存在返回结果区域，并没有返回，再执行finally，最后把保存在结果区域的结果返回给调用者
        List<User> list = null;
        try {
            list = userService.list();
            list.stream().map(user -> user.setCreateTimeStr(pattern.format(user.getCreateTime()))).collect(Collectors.toList());
            LogUtils.info("return1111");
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogUtils.info("finally");

        }
        LogUtils.info("return22222");
        return list;
    }


    @ApiOperation(value = "异步回调", notes="异步回调")
    @GetMapping("testCompletableFuture")
    public User testCompletableFuture(){
        System.out.println("start");
        User byId = userService.getById(1);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user = new User().setUserName("异步").setSex(33);
            userService.save(user);
            System.out.println(Thread.currentThread().getName() + "\t" + "添加成功");
        });

        System.out.println(222);

        return byId;
    }









    @ApiOperation(value = "测试@Async", notes="测试@Async")
    @GetMapping("testAsync")
    public String testAsync(){
        LocalDateTime beginTime = LocalDateTime.now();
        //     @Async 在主线程里打断点   断点走的是主线程 就算异步里面打了断点也不会进断点
        //            直接在异步里面打断点  断点肯定会走  走的不是主线程  走的是另一个新开启的线程
        //      @Async  不能在一个类里面   启动类加上 @EnableAsync开启异步
        //      @Async  新开一个线程去执行 不会影响主线程
        String s = asyncService.testAsync();
        LocalDateTime endTime = LocalDateTime.now();
        LogUtils.info("time" + (endTime.getSecond()-beginTime.getSecond()));
        return s;
    }


    @ApiOperation(value = " 根据id 获取数据", notes="根据id 获取数据")
    @GetMapping("getById")
    public User getById(Integer id){
        User user = userService.getById(id);
        return user;
    }


    /**
     * 测试 @Transactional 注解
     * @return
     */
    @ApiOperation(value = " 添加User和item", notes="添加User和item")
    @PostMapping("addUserAndItem")
    public User addUserAndItem() throws Exception {
        User user = new User();
        try {
            user.setUserName("wj").setPassword("11111").setSex(1).setSalt("333333");
            UserItem userItem = new UserItem();
            userService.userAndItemAdd(user, userItem);
            return user;
        } catch (BizException e) {
            logger.info(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return user;
    }



    /**
     * 非空校验
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("testParam")
    public Response<User> testParam(){
        Response<User>  response = Response.success(Response.SUCCESS_CODE, Response.SUCCESS_MESSAGE);
        User user = new User();
        user.setSex(2);
        try {
            String[] fileCode = {"id","sex", "userName","password"};
            String[] fileName = {"主键","性别","名字","密码"};
            this.fieldMustCheck(user,fileCode,fileName);
            response.setData(user);
        } catch (ParamException e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }


    /**
     * 校验必填项
     * @param
     */
    public void fieldMustCheck( User user, String[] fileCodes, String[] fileNames) {
        try {
            if (fileCodes.length > 0) {
                for (int i = 0; i < fileCodes.length; i++) {
                    Field tbField = user.getClass().getDeclaredField(fileCodes[i]);
                    tbField.setAccessible(true);
                    if (isNullOrEmpty(tbField.get(user))) {
                        throw new ParamException(400, fileNames[i] + " 不可为空!");
                    }
                }
            }
        } catch (Exception e) {
            throw  new ParamException(400, e.getMessage());
        }
    }
    /**
     * 字段为空校验
     * @param object
     * @return
     */
    private boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String && StringUtils.isBlank((String) object)) {
            return true;
        }
        return false;
    }


    /**
     * 根据商家编号获取事业部信息列表，开通预收预付单的事业部
     * @param id   商家id  事业部id  仓库id  承运商id
     * @param type  seller dept  warehouse   shopId  商家--事业部--店铺/仓库  三级联动
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getTenantId.do")
    public Response getTenantId(String id, String type) throws Exception {
        Response response = Response.success();
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            response.setCode(BaseResponse.ERROR_PARAM);
            response.setMsg("参数id: ["+ id + "] && type: [" + type +"] 不允许为空");
            return response;
        }
        List<TenantDto> list = Lists.newArrayList();
        if (StringUtils.equals(type, "city")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("100000");
            dto1.setName("南京");
            TenantDto dto2 = new TenantDto();
            dto2.setId("200000");
            dto2.setName("上海");
            TenantDto dto3 = new TenantDto();
            dto3.setId("300000");
            dto3.setName("北京");
            list.add(dto1);
            list.add(dto2);
            list.add(dto3);
        }
        if (StringUtils.equals(type, "seller")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("sellerId001");
            dto1.setName("sellerName001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("sellerId002");
            dto2.setName("sellerName002");
            list.add(dto1);
            list.add(dto2);
        }
        if (StringUtils.equals(type, "dept")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("deptId001");
            dto1.setName("deptName001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("deptId002");
            dto2.setName("deptName002");
            list.add(dto1);
            list.add(dto2);
        }
        if (StringUtils.equals(type, "warehouse")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("warehouse001");
            dto1.setName("warehouse001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("warehouse002");
            dto2.setName("warehouse002");
            list.add(dto1);
            list.add(dto2);
        }
        response.setData(list);
        return response;
    }




    /**
     * 获取商家信息列表 开通预收预付单的事业部
     *
     * @return errors
     */
    @RequestMapping(value = "getAllSeller.do")
    public Object getAllSeller(String sellerName,String query) throws Exception {
        if(StringUtils.isBlank(sellerName)){
            sellerName = query;
        }

        logger.error("开始获取商家权限信息列表：" + sellerName);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();


        try {
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("id", "111");
            map1.put("sellerName", "商家A");

            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("id", "222");
            map2.put("sellerName", "商家B");

            Map<String, String> map3 = new HashMap<String, String>();
            map3.put("id", "333");
            map3.put("sellerName", "商家C");

            list.add(map1);
            list.add(map2);
            list.add(map3);
        } catch (Exception e) {
            logger.error("查询承运商接口异常",e);
        }

        return list;
    }

    /**
     * 获取订单信息
     * @return
     */
    @PostMapping(value = "getOrderInfo")
    public Response getOrderInfo(String bizOrderNo, String bizType){
        Response<Object> response = Response.success();
        Map<String, String> orderInfo = new HashMap<String, String>();
        orderInfo.put("deptId", "2");
        orderInfo.put("deptNo", "deptNo");
        orderInfo.put("deptName", "deptNo");
        orderInfo.put("shopId", "4");

        String deptId = orderInfo.get("deptId");
        String shopId = orderInfo.get("shopId");
        if (orderInfo.containsKey("deptId") && StringUtils.isNotBlank(deptId)) {
            orderInfo.put("deptFlag", "1");
        } else {
            orderInfo.put("deptFlag", "0");
        }
        if (orderInfo.containsKey("shopId") && StringUtils.isNotBlank(shopId)) {
            orderInfo.put("shopFlag", "1");
        } else {
            orderInfo.put("shopFlag", "0");
        }
        response.setData(orderInfo);
        return response;
    }

    /**
     * 用户列表导出
     * @param request
     * @param response
     * @param userDto
     * @throws Exception
     */
    @RequestMapping(value = "exportUserListPage")
    public void exportUserListPage(HttpServletRequest request, HttpServletResponse response, UserDto userDto) throws Exception {
        try {
            PageRequestParam<User> page = SpringContextUtil.getBean("userController", UserController.class).findUserListPage(null, request, userDto);
            List<User> userList = page.getAaData();
            List<com.dj.boot.common.excel.exc.UserDto> userDtoList = UserToUserDtoConvert.INSTANCE.userToDtoList(userList);
            ExcelUtil<com.dj.boot.common.excel.exc.UserDto> excelUtil = new ExcelUtil<>(com.dj.boot.common.excel.exc.UserDto.class);
            excelUtil.exportExcelToWeb(response, userDtoList,"用户信息表", ExcelType.XLSX);
        } catch (Exception e) {
            logger.error("导出异常:{}", e.getMessage());
        }
    }

    @PostMapping(value = "testParamUUID")
    public Response<Object> testParamUUID(User user , String uuid) throws Exception {
        logger.error("{},exportEventOrderDetailPage-->入参:{}", uuid, JSONObject.toJSONString(user));
        return Response.success(200,"success");
    }

    /**
     * 评价
     *  orderFlowType 1-普通赔付 2-mcss赔付 3-留言  5-评价
     * @param comments
     * @param remark
     * @return
     */
    @RequestMapping(value = "submitComment", method = RequestMethod.POST)
    //@PermissionValidate(name = {"deptList","warehouseIdList"},type = {PermissionType.NO,PermissionType.NO},uri = {PermissionUri.dept,PermissionUri.warehouse})
    public Response<Object> submitComment(String[] deptNoList,String[] warehouseNoList,Integer id, String comments, String remark, Integer orderFlowType) {
        // PermissionValidate 功能暂未实现
        Response<Object> response = Response.success();
        User user = userService.getById(id);
        if (Objects.isNull(user)) {
            response.setCode(BaseResponse.ERROR_PARAM);
            response.setMsg(BaseResponse.PERMIT_EXCEPTION_MESSAGE);
            return response;
        }
        MarkUtils markUtils = new MarkUtils(user.getEoMark());
        if (orderFlowType == 1 || orderFlowType == 2) {//赔付
            if (markUtils.isMark(InBoundMarkEnum.MARK_8_1)) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("该条数据已转赔付, 不允许操作, 请核实!");
                return response;
            }
            logger.error("submitComment 转赔付");
            markUtils.inChar(InBoundMarkEnum.MARK_8_1);//已赔付
        }
        if (orderFlowType == 3) {//留言
            logger.error("submitComment 留言");
            char oldValue = markUtils.charAt(9);
            if (oldValue == '9') {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("留言达到上限, 不允许操作, 请核实!");
                return response;
            }
            int i = oldValue-'0';  //只能表示0-9的数字
            i = i + 1;
            char newValue = (char)(i +'0');
            markUtils.inChar(InBoundMarkEnum.MARK_9_x.bit(), newValue);
        }
        if (orderFlowType == 5){//评价
            if (markUtils.isMark(InBoundMarkEnum.MARK_7_1)) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("该条数据已完成评价, 不允许操作, 请核实!");
                return response;
            }
            int i = 1;
            for (String scoreValue : comments.split(InBoundMarkEnum.split)) {
                switch (i) {
                    case 1:
                        markUtils.inChar(InBoundMarkEnum.MARK_1_x.bit(), scoreValue.charAt(0));
                        break;
                    case 2:
                        markUtils.inChar(InBoundMarkEnum.MARK_2_x.bit(), scoreValue.toCharArray()[0]);
                        break;
                    case 3:
                        markUtils.inChar(InBoundMarkEnum.MARK_3_x.bit(), scoreValue.charAt(0));
                        break;
                }
                i++;
            }
            char c = markUtils.charAt(InBoundMarkEnum.MARK_1_x);
            char c3 = markUtils.charAt(InBoundMarkEnum.MARK_1_x.bit());
            boolean mark = markUtils.isMark(InBoundMarkEnum.MARK_1_x);//false

            markUtils.inChar(InBoundMarkEnum.MARK_6_1);
            boolean mark1 = markUtils.isMark(InBoundMarkEnum.MARK_6_1);//true
            markUtils.inChar(InBoundMarkEnum.MARK_7_1);//标记已评价
        }

        user.setEoMark(markUtils.toString());
        boolean b = userService.updateById(user);
        if (!b) {
            response.setCode(BaseResponse.ERROR_PARAM);
            response.setMsg("修改失败");
            return response;
        }
        return response;
    }



    @PostMapping(value = "editItem")
    public Response<Object> editItem(String items) throws Exception {
        List<User> userList = JsonUtil.fromJson(items, new TypeReference<List<User>>(){});
        Map<Integer, User> map = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0))));
        logger.error("editItem-->入参:{}", JSONObject.toJSONString(userList));
        return Response.success(200,"success");
    }




    /**
     * 输出流 下载文件
     * @param id
     * @param response
     */
    @RequestMapping(value = "fileDownload" ,produces = "text/plain;charset=utf-8;")
    public void fileDownload(Integer id, HttpServletResponse response) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        List<User> list = userService.findUserListByCondition(userDto);
        User user = list.get(0);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名
        response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(user.getSalt(),"utf-8")+"");
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        ServletOutputStream out = null;
        try {

            String fileUrl = user.getUserName();
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection)url.openConnection();
            //得到输入流
            inputStream = conn.getInputStream();
            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[2048];
            while ((b = inputStream.read(buffer))>0){
                //4.写到输出流(out)中
                out.write(buffer,0,b);
            }
        } catch (Exception e) {
            logger.error("【error】下载图片错误",e);
        }finally{
            if(out != null){
                try {
                    out.close();
                    out.flush();
                } catch (IOException e) {
                    logger.error("【error】下载图片流关闭错误",e);
                }
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("【error】下载图片流关闭错误",e);
            }
            conn.disconnect();
        }
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }

}
