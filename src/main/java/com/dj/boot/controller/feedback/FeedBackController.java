package com.dj.boot.controller.feedback;

import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.feedback.handler.CommonFeedbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * http://localhost:8082/feedback/commonBack
 *
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-17-40
 */
@RestController
@RequestMapping("/feedback/")
public class FeedBackController extends BaseController {

    @Autowired
    private CommonFeedbackHandler commonFeedbackHandler;

    @PostMapping("commonBack")
    public Response<Object> commonBack () throws Exception {
        Response<Object> response = Response.success();
        Request request = new Request();
        request.setData("1,CSL00000002,CXS0000000002");
        commonFeedbackHandler.handler(request);
        return response;
    }
}
