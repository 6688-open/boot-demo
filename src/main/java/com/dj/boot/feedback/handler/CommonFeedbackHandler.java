package com.dj.boot.feedback.handler;


import com.dj.boot.common.base.Request;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.feedback.FeedbackSupport;
import com.dj.boot.feedback.constant.FeedbackInterfaceNameEnum;
import com.dj.boot.feedback.constant.FeedbackModeEnum;
import com.dj.boot.feedback.domain.FeedbackData;
import com.dj.boot.feedback.domain.FeedbackSupportMain;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一回传Handler
 * <p>
 * 新增回传业务
 * 1：在FeedbackInterfaceNameEnum里面增加一个回传枚举
 * 2：新写一个biz support实现，组装回传参数
 * 3：spring-feedback.xml配置上新业务路由
 * 4：在各业务需要回传的地方addTask
 * 5：按照商家事业部信息配置需要回传的业务类别
 *
    @Author: wangJia
  * @Date: 2022-02-16-16-33
 **/
@Component("commonFeedbackHandler")
public class CommonFeedbackHandler implements BeanFactoryAware {

    private static Logger logger = LogManager.getLogger(CommonFeedbackHandler.class);

    @Resource(name = "feedbackInterfaceNameEnumMap")
    private Map<FeedbackInterfaceNameEnum, String> feedbackInterfaceNameEnumMap;

    @Resource(name = "deptTargetSystemMap")
    private HashMap<String, String> deptTargetSystemMap;

    private BeanFactory beanFactory;


    public void handler(Request request) throws Exception {
        long tip = System.nanoTime();
        String msg = (String) request.getData(); //"1,CSL00000002,CXS0000000002"
        String[] split = msg.split(",");
        FeedbackSupportMain feedbackSupportMain = new FeedbackSupportMain(split[0], split[1], split[2]);
        //根据单据类型获取指定的处理类
        FeedbackSupport feedbackSupport = getFeedbackSupport(feedbackSupportMain.getBizType());

        if (feedbackSupport.isFeedback(feedbackSupportMain)) {
            //不同的处理类组装回传报文
            FeedbackData feedbackData = feedbackSupport.getFeedbackData(feedbackSupportMain);
            feedbackData.setTargetSystemSign(deptTargetSystemMap.get(feedbackSupportMain.getDeptNo()));
            //根据配置 设置回传模式

            logger.error(tip + "============回传报文======={}", JsonUtil.toJson(feedbackData));
            try {
                if (feedbackData.getFeedbackMode().equals(FeedbackModeEnum.WG.getCode())) {
                    feedbackFromChannel(feedbackData);
                } else if (feedbackData.getFeedbackMode().equals(FeedbackModeEnum.MQ.getCode())) {
                    feedbackFromMq(feedbackData);
                }
            } catch (Exception e) {
                logger.error(tip + "调用下发接口发生异常",  e);
                throw new RuntimeException("调用transport下发接口发生异常", e);
            }
        }
    }

    private void feedbackFromMq(FeedbackData feedbackData) throws Exception {
        //异步MQ
        String topic = feedbackData.getTopic();
        logger.error("{},回传,topic:{},发送jmq成功!", FeedbackInterfaceNameEnum.getEnumByCode(feedbackData.getBizType()).getDesc(), feedbackData.getTopic());
    }

    private void feedbackFromChannel(FeedbackData feedbackData) throws Exception {
        //调用网关接口回传
    }

    /*----------------------------分割线--------------------------------------------*/

    /**
     * 获取回传支撑类
     *
     * @param bizType
     * @return
     * @throws IllegalArgumentException
     * @throws ClassCastException
     * @throws UnsupportedOperationException
     */
    private FeedbackSupport getFeedbackSupport(String bizType) throws IllegalArgumentException, ClassCastException, UnsupportedOperationException {
        if (bizType == null) {
            throw new IllegalArgumentException("单据类型为空");
        }
        FeedbackInterfaceNameEnum feedbackInterfaceNameEnum = FeedbackInterfaceNameEnum.getEnumByCode(Integer.valueOf(bizType));
        logger.error("feedbackInterfaceNameEnum=" + feedbackInterfaceNameEnum.getName());
        if (isSupport(feedbackInterfaceNameEnum)) {
            Object bean = this.beanFactory.getBean(feedbackInterfaceNameEnumMap.get(feedbackInterfaceNameEnum));
            if (bean instanceof FeedbackSupport) {
                return (FeedbackSupport) this.beanFactory.getBean(feedbackInterfaceNameEnumMap.get(feedbackInterfaceNameEnum));
            } else {
                throw new ClassCastException("同步类型转换异常");
            }
        } else {
            throw new UnsupportedOperationException("不支持改类型的操作");
        }
    }

    /**
     * 判断是否支持该单据类型
     * <p>
     * <p>扩展：1、请在spring/spring-feedback.xml中配置改枚举对应的bean名字的映射关系
     * </br>   2、在FeedbackInterfaceNameEnum中增加该业务类型的枚举值</p>
     *
     * @param feedbackInterfaceNameEnum 单据类型
     * @return true:支持该类型 false:不支持
     * @see FeedbackInterfaceNameEnum
     */
    private boolean isSupport(FeedbackInterfaceNameEnum feedbackInterfaceNameEnum) {
        if (feedbackInterfaceNameEnum == null) {
            return false;
        }
        String beanName = feedbackInterfaceNameEnumMap.get(feedbackInterfaceNameEnum);
        logger.error("beanName=" + beanName);
        if (StringUtils.isBlank(beanName)) {
            return false;
        }
        if (!beanFactory.containsBean(beanName)) {
            return false;
        }
        return true;
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link .InitializingBean #afterPropertiesSet()} or a custom init-method.
     *
     * @param beanFactory owning BeanFactory (never {@code null}).
     *                    The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see .BeanInitializationException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
