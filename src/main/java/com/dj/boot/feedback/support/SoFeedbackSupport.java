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

@Component("soFeedbackSupport")
public class SoFeedbackSupport extends FeedbackSupport implements EmbeddedValueResolverAware {

    private static Logger logger = LogManager.getLogger(SoFeedbackSupport.class);

    private StringValueResolver stringValueResolver;

    @Override
    public FeedbackData getFeedbackData(FeedbackSupportMain feedbackSupportMain) throws IOException {
        logger.error("stockAdjustFeedbackSupport接收到消息：{}", JsonUtil.toJson(feedbackSupportMain));
        //根据单号获取数据
        String bizNo = feedbackSupportMain.getBizNo();
        User user = new User().setSalt("pwd").setUserName("wj");

        FeedbackData feedbackData = new FeedbackData();
        feedbackData.setBodyMsg(JsonUtil.toJson(user));
        feedbackData.setPin(user.getUserName());
        feedbackData.setBizNo(feedbackSupportMain.getBizNo());
        feedbackData.setBizType(FeedbackInterfaceNameEnum.SO_ORDER_FEEDBACK.getCode());
        feedbackData.setFeedbackMode(FeedbackModeEnum.WG.getCode());
        logger.error("soFeedbackSupport发送消息：{}",JsonUtil.toJson(feedbackData));
        return feedbackData;
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
}
