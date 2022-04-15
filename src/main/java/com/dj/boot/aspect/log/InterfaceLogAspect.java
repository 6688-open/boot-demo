package com.dj.boot.aspect.log;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.annotation.log.InterfaceLog;
import com.dj.boot.aspect.log.log4j.DefaultFactory;
import com.dj.boot.aspect.log.utils.InterfaceLogCache;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InterfaceLogAspect {

    private static final Logger log = DefaultFactory.getLogger(InterfaceLogAspect.class);
    ExpressionParser parser = new SpelExpressionParser(); //spel参数解析器

    @Around("@annotation(interfaceLog)")
    public Object doAround(ProceedingJoinPoint pjp, InterfaceLog interfaceLog) throws Throwable {

        long start = System.currentTimeMillis();
        String value = interfaceLog.value(); //注解值
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String interfaceName = signature.getDeclaringTypeName() + "#" + signature.getName(); //接口名+方法名
        String[] parameterNames = signature.getParameterNames(); //参数名
        Object[] args = pjp.getArgs();//参数
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        String bizNo = null;
        Object result = null;
        try {
            try {//解析注解上的字段的值
                bizNo = parser.parseExpression(value).getValue(context, String.class);
            } catch (ExpressionException e) {
                log.error(e);
            }
            result = pjp.proceed();//返回结果
        } finally {
            long end = System.currentTimeMillis();
            try {
                //记录日志
                log.error("业务单号：{}, 接口名:{}, 时间:{}, 结果:{}, 参数:{}", bizNo, interfaceName, end-start, JSONObject.toJSON(result), JSONObject.toJSON(args));
                //insert log table

                //InterfaceLogCache.printLog(bizNo, interfaceName, end - start, result, args);
                //将pom spring-boot-starter开头的pom 排除包
                /*<exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>*/
            } catch (Exception e) {
                log.error(e);
            }
        }
        return result;
    }

}
