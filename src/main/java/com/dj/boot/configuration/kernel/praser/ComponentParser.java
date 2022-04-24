package com.dj.boot.configuration.kernel.praser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-10-35
 */
public class ComponentParser implements BeanDefinitionParser {
    public ComponentParser() {
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String className = element.getAttribute("class");
        if (StringUtils.isBlank(className)) {
            throw new IllegalStateException("Component class is null");
        } else {
            Class classObj;
            try {
                classObj = Class.forName(className);
            } catch (ClassNotFoundException var17) {
                throw new IllegalStateException("class NotFound:" + className);
            }

            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(classObj);
            beanDefinition.setLazyInit(false);
            String id = element.getAttribute("id");
            if (StringUtils.isBlank(id)) {
                throw new IllegalStateException("[component]This bean do not set spring bean id " + id);
            } else if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("[component]Duplicate spring bean id " + id);
            } else {
                parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
                Method[] arr$ = classObj.getMethods();
                int len$ = arr$.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    Method setter = arr$[i$];
                    if (this.isProperty(setter)) {
                        String name = setter.getName();
                        String property = name.substring(3, 4).toLowerCase() + name.substring(4);
                        int proType = this.getPropertyType(property);
                        String value = element.getAttribute(property);
                        switch(proType) {
                            case 1:
                                RuntimeBeanReference reference;
                                if (StringUtils.isNotBlank(value)) {
                                    BeanDefinition refBean = parserContext.getRegistry().getBeanDefinition(value);
                                    if (!refBean.isSingleton()) {
                                        throw new IllegalStateException("[component]The exported service ref " + value + " must be singleton! Please set the " + value + " bean scope to singleton, eg: <bean id=\"" + value + "\" scope=\"singleton\" ...>");
                                    }

                                    reference = new RuntimeBeanReference(value);
                                } else {
                                    reference = null;
                                }

                                beanDefinition.getPropertyValues().addPropertyValue(property, reference);
                                break;
                            case 2:
                                if (StringUtils.isNotBlank(value)) {
                                    beanDefinition.getPropertyValues().addPropertyValue(property, value);
                                }
                        }
                    }
                }

                return beanDefinition;
            }
        }
    }

    private int getPropertyType(String propertyName) {
        byte type = -1;
        if ("filterChain".equals(propertyName)) {
            type = 1;
        } else if ("postWorkChain".equals(propertyName)) {
            type = 1;
        } else if ("validatorChain".equals(propertyName)) {
            type = 1;
        } else if ("id".equals(propertyName)) {
            type = 2;
        }

        return type;
    }

    private boolean isProperty(Method method) {
        String methodName = method.getName();
        boolean flag = methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 1;
        return flag;
    }
}
