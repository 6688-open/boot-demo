package com.dj.boot.feedback;

import com.dj.boot.feedback.constant.FeedbackInterfaceNameEnum;
import com.dj.boot.feedback.domain.FeedbackData;
import com.dj.boot.feedback.domain.FeedbackSupportMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * 回传接口名
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
public abstract class FeedbackSupport {

    private static Logger logger = LogManager.getLogger(FeedbackSupport.class);

    public abstract FeedbackData getFeedbackData(FeedbackSupportMain feedbackSupportMain) throws IOException;


    /**
     * 是否支持回传 如果有特殊子类重写该方法
     * @param feedbackSupportMain
     * @return
     */
    public boolean isFeedback(FeedbackSupportMain feedbackSupportMain) {
        FeedbackInterfaceNameEnum feedbackInterfaceNameEnum = FeedbackInterfaceNameEnum.getEnumByCode(Integer.valueOf(feedbackSupportMain.getBizType()));
        if (null == feedbackInterfaceNameEnum) {
            return false;
        }
        return true;
    }
}
