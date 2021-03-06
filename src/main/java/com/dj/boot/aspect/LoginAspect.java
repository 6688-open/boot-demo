package com.dj.boot.aspect;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.annotation.Login;
import com.dj.boot.aspect.hodler.LoginContext;
import com.dj.boot.aspect.hodler.LoginUserContextHodler;
import com.dj.boot.aspect.hodler.PermissionContext;
import com.dj.boot.aspect.hodler.UserAndPermissionContext;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.json.JsonUtils;
import com.dj.boot.common.util.ProceedingJoinPointUtils;
import com.dj.boot.controller.TestController;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.service.user.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**AnnotationAwareAspectJAutoProxyCreator
 * @ClassName TestAspect
 * @Description TODO
 * @Author wj
 * @Date 2019/11/20 13:51
 * @Version 1.0
 **/
@Aspect
@Order(3)
@Component
public class LoginAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private UserService userService;

    private static Set<String> whiteParamList; // ??????????????????key??????????????????????????????
    static {
        whiteParamList = new HashSet<>();
        whiteParamList.add("deptNoList");
    }

    private final static Logger log = LogManager.getLogger(LoginAspect.class);

    @Around(value = "com.dj.boot.aspect.Pointcuts.pointcutForActions()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        //testConcurrentLock(joinPoint);

        //????????????????????????
        //paramTest(joinPoint);
        //????????????  ???????????????????????????  ????????????
        setUserAndPermission();
        System.out.println("aspect   test   start................................................");
        //????????????????????????????????? @Login  ??????
        if (!ProceedingJoinPointUtils.isAnnotation(joinPoint, Login.class)) {
            return joinPoint.proceed();
        }




        //?????? TestController ??????????????????   ??????????????????  ?????? ?????????
        //??????????????????
        Method[] methods = TestController.class.getDeclaredMethods();
        if(methods != null){
            for(Method method : methods){
                //????????????????????????get ?????????????????????
                Login annotation = method.getAnnotation(Login.class);
                if(annotation == null) {
                    continue;
                }
                //??????????????????  ?????????  ????????????????????????
                int i = annotation.value();
                System.out.println(i);
            }
        }

        String name = joinPoint.getSignature().getName();
        //????????????????????????
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("?????????" + arg);
        }


        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.error("request.getParameterMap:{}", request.getParameterMap());
        log.error("request.getParameterMap:{}", JSONObject.toJSONString(whiteParamList));
        String paramString = getParamString(request.getParameterMap());
        log.error("paramString:{}", paramString);


        User user = userService.getById(1);
        //User user = userService.getById(123);

        if (user == null) { //??????????????? ??????????????????
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=utf-8");
            try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                Response result = new Response();
                result.setMsg("id 1  ???????????????");
                result.setData("400");
                String body = JsonUtils.serialize(result);
                byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return joinPoint.proceed();
    }

    private void testConcurrentLock(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        //??????????????????
        Method method = signature.getMethod();
        // ??????????????????
        ConcurrentLock annotation = method.getAnnotation(ConcurrentLock.class);

        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            System.out.println(parameterNames[i]);
            System.out.println(args[i]);
            context.setVariable(parameterNames[i], args[i]);
        }
        String redisKey = "";
        if (StringUtils.isNotBlank(annotation.prefix())) {
            redisKey += annotation.prefix() + annotation.connector();
        }
        String bizNo = parser.parseExpression(annotation.bizNo()).getValue(context, String.class);

        redisKey += bizNo;
        if (StringUtils.isNotBlank(annotation.suffix())) {
            redisKey += annotation.connector() + annotation.suffix();
        }
    }

    private void setUserAndPermission() {

        UserAndPermissionContext userAndPermissionContext = new UserAndPermissionContext();

        //????????????
        LoginContext loginContext = new LoginContext();
        loginContext.setUserName("UserName");
        loginContext.setPassword("Password");

        //????????????
        PermissionContext permissionContext = new PermissionContext();
        permissionContext.setOrgNo("XXX202003213530");

        userAndPermissionContext.setLoginContext(loginContext);
        userAndPermissionContext.setPermissionContext(permissionContext);

        LoginUserContextHodler.set(userAndPermissionContext);

    }


    public static Object paramTest(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //?????????????????????
        String[] parameterNames = methodSignature.getParameterNames();

        //???????????????pin ??????  ?????????????????????
        int pinIndex = ArrayUtils.indexOf(parameterNames, "pin");
          String pin = (String) args[pinIndex];

        //  ?????????????????????
        UserDto userDto = new UserDto();
        Object a = args[0];
        BeanUtils.copyProperties(a, userDto);

        System.out.println(userDto.getPin());
        return null;
    }




    /**
     * ??????????????????
     *
     * @param map
     * @return
     */
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            if (whiteParamList.contains(e.getKey())) {
                continue;
            }
            if (i != 0) {
                sb.append(",");
            }
            sb.append("\"").append(e.getKey()).append("\":\"");
            String[] value = e.getValue();
            if (value.length == 1) {
                sb.append(value[0]).append("\"");
            } else {
                sb.append(Arrays.toString(value)).append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }




    //?????? TestController ??????????????????   ??????????????????  ?????? ?????????
    public static void main(String[] args) {
        //??????????????????
        Method[] methods = TestController.class.getDeclaredMethods();
        if(methods != null){
            for(Method method : methods){
                //????????????????????????get ?????????????????????
                Login annotation = method.getAnnotation(Login.class);
                if (method.isAnnotationPresent(Login.class)) {
                    System.out.println(method+ "????????????test??????");
                }
                if(annotation == null) {
                    continue;
                }
                //??????????????????  ?????????  ????????????????????????
                int i = annotation.value();
                System.out.println(i);
            }
        }
    }




//    //Aop JoinPoint????????????
//    ????????????
//    /*????????????????????????*/
//    Object[] args = point.getArgs();                                    //  [1] ????????????
//    /*??????????????????(??????????????????)*/
//    Object target = point.getTarget();
//    /*??????signature ??????????????????????????????????????? MethodSignature*/
//    MethodSignature signature = (MethodSignature) point.getSignature();
//    /*?????????*/
//    String signatureName = signature.getName();                         //  findById
//    /*??????????????????(???args?????????????????????)*/
//    String[] parameterNames = signature.getParameterNames();            //  [i] ????????????
//    /*???????????????????????????Method??????*/
//    Method method = signature.getMethod();                              //  public void com.draymond.aop2.service.UserService.findById(int)
//    /*?????????????????????*/
//    Class returnType = signature.getReturnType();                       //  void
//    /*????????????????????????*/
//    WebAnnotation webAnnotation = method.getDeclaredAnnotation(WebAnnotation.class);
//
//
//    //????????????????????? request / response
//
// ??????// ??????request/response(ThreadLocal??????)
//    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//    HttpServletRequest request = servletRequestAttributes.getRequest();
//    HttpServletResponse response = servletRequestAttributes.getResponse();
}
