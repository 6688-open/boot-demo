package com.dj.boot.configuration.okhttp3.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.btp.exception.BizException;
import com.dj.boot.combine.handler.enums.PddTemplateConstants;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonHttpExecutionService;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonExtraMsg;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import com.dj.boot.configuration.okhttp3.domain.TransportWarehouse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;


@Component("commonSynchronousService")
public class CommonSynchronousServiceImpl implements CommonSynchronousService {

    private static final Logger log = LogManager.getLogger(CommonSynchronousServiceImpl.class);

    private static final String dtPrefix="dt://";

    private static final String BIZ_NO="bizNoSign";

    /**
     * UMP监控Key参数名称
     */
    private static final String MONITOR_KEY = "monitorKey";

    @Autowired
    private CommonHttpExecutionService commonHttpExecutionService;


    @Override
    public Object handler(CommonTransport commonTransport) throws Exception {
        log.error("CommonSynchronousServiceImpl==>handler info :{}", JsonUtil.toJson(commonTransport));
        //校验参数
        verify(commonTransport);

        //step 2:获取数据库配置信息
        TransportWarehouse tw = new TransportWarehouse();// getConfig(commonTransport);
        tw.setServiceUri("22222");

        //step 3:根据模板和业务数据转换参数信息
        Object bodyObj = new Object();//getBodyObject(commonTransport, tw);

        //step 4:构造下发配置并返回拓展报文
        Map<String, Object> headers = buildCommonTransport(commonTransport, tw);


        Map<String, Object> header = commonTransport.getHeader();
        //parseKdn(header);
        //parsePdd(header);
        parsePddJSON(header);

        try {
            log.info("invoke , param body is {} and header is {}", JSONObject.toJSONString(commonTransport), header);
            //返回的是元接口属性的key:value
            String msg = commonHttpExecutionService.executeHttp(JSONObject.toJSONString(commonTransport), JSONObject.toJSONString(header));
            log.info("invoke result is {}", msg);
            return msg;
        } catch (Exception e) {
            log.error("invoke exception,params:{}", commonTransport.toString(), e);
            throw new BizException(600,"invoke exception,params:{" + commonTransport.toString() + "}", e);
        }
    }

    private void parseKdn(Map<String, Object> header) {
        header.put("executor", "POST_JSON");
        header.put("exec", "kdn");
        header.put("url", "http://localhost:8082/okhttp3/echo");
        header.put("method", "a.b.c.d");
        header.put("secret_key", "XXXXXXXXXXXXXXXXX");
        header.put("uri", "/user/service/getById");
        header.put("customerId", "test00000000001");
        header.put("appKey", "XXXXXXXXXXXXXXXXX");
    }

    private void parsePddJSON(Map<String, Object> header) {
        header.put("executor", "POST_JSON");
        header.put("exec", "pdd");
        header.put("url", "http://localhost:8082/okhttp3/echo");
        header.put("uri", "/user/service/getById");
        header.put("type", PddTemplateConstants.TYPE_WAY_BILL);
        header.put("version", "1");
    }

    private void parsePdd(Map<String, Object> header) {
        header.put("executor", "POST_FORM");
        header.put("exec", "pdd");
        header.put("url", "http://localhost:8082/okhttp3/word");
        header.put("uri", "/user/service/getById");
        header.put("type", PddTemplateConstants.TYPE_WAY_BILL);
        header.put("version", "1");
        header.put("Content-Type", "application/x-www-form-urlencoded"); // form表单需要指定
    }


    private void verify(CommonTransport commonTransport) {
        if (commonTransport == null) {
            log.error("下发任务对象为空");
            throw new BizException(600,"下发任务对象为空");
        }

        if (commonTransport.getWarehouseType() == null) {
            log.error("下发任务库房类型为空");
            throw new BizException(600,"下发任务库房类型为空");
        }

    }


    private Map<String, Object> buildCommonTransport(CommonTransport commonTransport, TransportWarehouse tw) {

        Map<String, Object> extraMsg = new HashedMap();
        String extraMsg2 = tw.getExtraMsg();
        try {
            if (commonTransport.getHeader() != null) {
                extraMsg.putAll(commonTransport.getHeader());
            }
            CommonExtraMsg ctExtraMsg = commonTransport.getExtraMsg();
            if (ctExtraMsg != null) {

                Map<String, String> ctMap = BeanUtils.describe(ctExtraMsg);

                extraMsg.putAll(ctMap);
            }
            if (StringUtils.isNotBlank(extraMsg2)) {
                Map<String, Object> twMap = JsonUtil.fromJson(extraMsg2, new TypeReference<Map>() {
                });
                extraMsg.putAll(twMap);
            }
            extraMsg.put(BIZ_NO, StringUtils.defaultString(commonTransport.getBizNo(),ctExtraMsg != null?ctExtraMsg.getBizNo():"null")); //记录业务编号

            // ump 监控key
            String bizName = null;
            if(tw.getBizType()!=null){
                bizName = tw.getBizType().toString();
            }else {
                bizName = "unknow";
            }

            String performSystemName = null;
            if (tw.getWarehouseType()!=null) {
                performSystemName = tw.getWarehouseType().toString();
            } else {
                performSystemName = "unknow";
            }

            extraMsg.put(MONITOR_KEY,bizName + "." + performSystemName); //记录监控的业务编号

            String jsonMap = JsonUtil.toJson(extraMsg);
            extraMsg = new HashMap();
            extraMsg.put("heard",jsonMap);
            return extraMsg;
        } catch (Exception e) {
            log.error(String.format("发生错误,ctExtraMsg:%s,extraMsg2:%s", extraMsg2), e);
            throw new BizException(600, String.format("发生错误,ctExtraMsg:%s,extraMsg2:%s", commonTransport.getHeader(), extraMsg2), e);
        }

    }
}
