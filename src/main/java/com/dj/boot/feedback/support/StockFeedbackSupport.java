package com.dj.boot.feedback.support;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.feedback.FeedbackSupport;
import com.dj.boot.feedback.constant.FeedbackInterfaceNameEnum;
import com.dj.boot.feedback.constant.FeedbackModeEnum;
import com.dj.boot.feedback.domain.FeedbackData;
import com.dj.boot.feedback.domain.FeedbackSupportMain;
import com.dj.boot.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.io.IOException;


/**
 * 库存的消息处理回传
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
@Component("stockFeedbackSupport")
public class StockFeedbackSupport extends FeedbackSupport implements EmbeddedValueResolverAware {

    private static Logger logger = LogManager.getLogger(StockFeedbackSupport.class);

    private StringValueResolver stringValueResolver;


    @Override
    public FeedbackData getFeedbackData(FeedbackSupportMain feedbackSupportMain) throws IOException {
        //根据单号获取数据
        String bizNo = feedbackSupportMain.getBizNo();
        User user = new User().setSalt("pwd").setUserName("wj");

        FeedbackData feedbackData = new FeedbackData();
        feedbackData.setBodyMsg(JsonUtil.toJson(user));
        feedbackData.setPin(user.getUserName());
        feedbackData.setDeptNo("0000000000");
        feedbackData.setBizNo(bizNo);
        feedbackData.setFeedbackMode(FeedbackModeEnum.MQ.getCode());
        feedbackData.setTopic(stringValueResolver.resolveStringValue("${stock_feedback}"));
        feedbackData.setBizType(FeedbackInterfaceNameEnum.STOCK_FEEDBACK.getCode());
        return feedbackData;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
}
