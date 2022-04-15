package com.dj.boot.aspect;

import com.dj.boot.annotation.privacy.PrivacyField;
import com.dj.boot.annotation.privacy.PrivacyFieldType;
import com.dj.boot.aspect.privacy.PrivacyBeanUtils;
import com.dj.boot.common.constant.privacy.SingleClassConstants;
import com.dj.boot.common.constant.privacy.handler.SimpleTypeHandler;
import com.dj.boot.common.constant.privacy.handler.TypeHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**AnnotationAwareAspectJAutoProxyCreator
 * @ClassName TestAspect
 * @Description TODO
 * @Author wj
 * @Date 2019/11/20 13:51
 * @Version 1.0
 **/
@Aspect
@Order(4)
@Component
public class PrivacyTransferAspect {


    private final static Logger log = LogManager.getLogger(PrivacyTransferAspect.class);

    private TypeHandler typeHandler = new SimpleTypeHandler();

    @Pointcut("@annotation(com.dj.boot.annotation.privacy.PrivacyMethod)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("doAround()");
        //方法参数
        Object[] objs = joinPoint.getArgs();
        //返回结果
        Object proceed = joinPoint.proceed(objs);
        proceed = this.filter(proceed);
        return proceed;
    }

    private Object filter(Object proceed) {
        if (SingleClassConstants.isSingleClass(proceed)) {
            log.debug("结果为普通类型，直接返回:{}", proceed.getClass());
            return proceed;
        }
//        PrivacyContext privacyContext = PrivacyContextHolder.get();
//        if (privacyContext == null) {
//            privacyContext = new PrivacyContext();
//        }

        String dataName = "";//privacyContext.getDataName();
        String group = "info";//privacyContext.getGroup()
        String fieldName ="customerName";// privacyContext.getFieldName();
        boolean isHidden = true;//privacyContext.isHidden();
        if (!isHidden) {
            if (StringUtils.isNotBlank(group) && "all".equals(group)) {
                return proceed;
            }
            this.show(proceed, group, fieldName);
        } else {
            this.tranfer(proceed);
        }
        return proceed;
    }


    private void show(Object result, String group, String fieldName) {
        try {
            if (result == null) {
                return;
            }
            if (result instanceof Collection) {
                Iterator var12 = ((Collection)result).iterator();

                while(var12.hasNext()) {
                    Object o = var12.next();
                    this.show(o, group, fieldName);
                }
                return;
            }

            List<Field> allFields = PrivacyBeanUtils.getAllFields(result.getClass());
            Iterator var5 = allFields.iterator();

            while(var5.hasNext()) {
                Field field = (Field)var5.next();
                field.setAccessible(true);
                PropertyDescriptor propertyDescriptor = null;

                try {
                    //PropertyDescriptor类：(属性描述器) 必须要有get  set方法
                    propertyDescriptor = new PropertyDescriptor(field.getName(), result.getClass());
                } catch (IntrospectionException var9) {
                    log.debug("field.getName() 获取方法异常{}", var9.getMessage());
                    continue;
                }

                Object fieldValue = propertyDescriptor.getReadMethod().invoke(result);
                if (fieldValue != null) {
                    if (!SingleClassConstants.isSingleClass(fieldValue)) {
                        this.show(fieldValue, group, fieldName);
                    } else if (!this.isShowField(field, group, fieldName)) {
                        propertyDescriptor.getWriteMethod().invoke(result, "");
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException var10) {
            var10.printStackTrace();
            log.error("获取对象方法失败", var10);
        }

    }

    private boolean isShowField(Field field, String group, String fieldName) {
        PrivacyField annotation = (PrivacyField)field.getAnnotation(PrivacyField.class);
        if (annotation != null) {
            if (!org.springframework.util.StringUtils.isEmpty(fieldName) && fieldName.equals(field.getName())) {
                return true;
            } else {
                String fieldGroup = annotation.group();
                return !org.springframework.util.StringUtils.isEmpty(group) && group.equals(fieldGroup);
            }
        } else {
            return true;
        }
    }

    private void tranfer(Object result) {
        try {
            if (result == null) {
                return;
            }
            if (result instanceof Collection) {
                Iterator var18 = ((Collection)result).iterator();

                while(var18.hasNext()) {
                    Object value = var18.next();
                    this.tranfer(value);
                }
                return;
            }
            log.debug("result:{}", result);
            List<Field> allFields = PrivacyBeanUtils.getAllFields(result.getClass());
            Iterator var3 = allFields.iterator();

            while(var3.hasNext()) {
                Field field = (Field)var3.next();
                field.setAccessible(true);
                PropertyDescriptor propertyDescriptor = null;

                try {
                    propertyDescriptor = new PropertyDescriptor(field.getName(), result.getClass());
                } catch (IntrospectionException var15) {
                    log.debug("field.getName() 获取方法异常{}", var15.getMessage());
                    continue;
                }

                Object fieldValue = propertyDescriptor.getReadMethod().invoke(result);
                if (fieldValue != null) {
                    if (!SingleClassConstants.isSingleClass(fieldValue)) {
                        this.tranfer(fieldValue);
                    } else {
                        PrivacyField annotation = (PrivacyField)field.getAnnotation(PrivacyField.class);
                        if (annotation != null) {
                            PrivacyFieldType type = annotation.type();
                            int[] remainIndexs = annotation.remainIndexs();
                            if (type == null && (remainIndexs == null || remainIndexs.length < 1)) {
                                log.error("{}属性未设置隐私隐藏类型及隐藏策略,隐藏失败", field.getName());
                            } else {
                                log.debug("type:{},remainIndexs:{}", type, remainIndexs);
                                int leftRemain = 0;
                                int rightRemain = 0;
                                if (remainIndexs != null && remainIndexs.length > 0) {
                                    leftRemain = remainIndexs[0];
                                    if (remainIndexs.length > 1) {
                                        rightRemain = remainIndexs[1];
                                    }
                                } else {
                                    leftRemain = type.getLeftRemain();
                                    rightRemain = type.getRightReamin();
                                    log.debug("leftRemain:{},rightRemain:{}", leftRemain, rightRemain);
                                }

                                Method readMethod = propertyDescriptor.getReadMethod();
                                Class<?> returnType = readMethod.getReturnType();
                                if (returnType.equals(String.class)) {
                                    String filedValue = (String)readMethod.invoke(result);
                                    propertyDescriptor.getWriteMethod().invoke(result, this.typeHandler.transfer(filedValue, leftRemain, rightRemain));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException var16) {
            var16.printStackTrace();
            log.error("获取对象方法失败", var16);
        }
    }

}
