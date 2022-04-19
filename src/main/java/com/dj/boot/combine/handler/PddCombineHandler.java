package com.dj.boot.combine.handler;


import com.alibaba.fastjson.JSONObject;
import com.dj.boot.combine.CombineService;
import com.dj.boot.combine.dto.BusinessTypeEnum;
import com.dj.boot.combine.dto.Command;
import com.dj.boot.combine.dto.Result;
import com.dj.boot.combine.handler.enums.HandlerType;
import com.dj.boot.combine.handler.enums.PddTemplateConstants;
import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangJia
 * @Date: 2022-04-13-10-41
 */
@HandlerType(businessType = "PDDV2")
public class PddCombineHandler implements CombineService {

    private static Logger logger = LogManager.getLogger(PddCombineHandler.class);
    private static final String TOKEN = "token";
    private static final String CODE = "code";

    @Resource
    private CommonSynchronousService commonSynchronousService;


    public Result<String> execute(Command<String> command) {
        if (isCheckedToken(command)) {
            return Result.fail("token 不允许为空");
        }
        BusinessTypeEnum def = BusinessTypeEnum.parseOf(command.getBusinessType(), command.getOperateType());
        try {
            switch (def) {
                case PDD_V2_GET_TOKEN:
                    return getToken(command);
                case PDD_V2_REFRESH_TOKEN:
                    return null;
            }
        } catch (Exception e) {
            logger.error("系统异常command{}", JsonUtil.toJson(command),e);
            return Result.error("系统异常");
        }
        return Result.fail("未知command[businessType=" + command.getBusinessType() + ",operateType=" + command.getOperateType() + "]");
    }

    private boolean isCheckedToken(Command<String> command) {
        String operateType = command.getOperateType();
        Map<String, Object> businessMap = command.getBusinessMap();
        return !BusinessTypeEnum.PDD_V2_GET_TOKEN.operateType().equals(operateType)
                && !BusinessTypeEnum.PDD_V2_REFRESH_TOKEN.operateType().equals(operateType)
                && (MapUtils.isEmpty(businessMap) || !businessMap.containsKey(TOKEN) || null == businessMap.get(TOKEN) || StringUtils.isBlank(String.valueOf(businessMap.get(TOKEN))));
    }

    private Map<String, Object> buildPddV2Header(String deptNo, String token) {
        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put(PddTemplateConstants.CLIENT_ID, "1234567898765432");
        header.put(PddTemplateConstants.CLIENT_SECRET, "sdghjkjhgfdsa23456789yfds");
        header.put(PddTemplateConstants.ACCESS_TOKEN, token);
        header.put(PddTemplateConstants.ORIGIN_RESP, PddTemplateConstants.ORIGIN_RESP_1);
        return header;
    }

    private Result<String> getToken(Command<String> command) {
        Map map = JSONObject.parseObject(command.getData(), Map.class);
        //String code = map.get(CODE).toString();
        String code = "3";
        return buildPddV2Token(command.getData(), code, command.getVendorCode(), 1);
    }

    private Result<String> buildPddV2Token(String param, String bizNo, String deptNo, Integer bizType) {
        try {
            param = "{\"param_request\":{\"need_encrypt\":true,\"sender\":{\"address\":{\"city\":\"市\",\"country\":\"CN\",\"detail\":\"路五方\",\"district\":\"曙区\",\"province\":\"江省\",\"town\":\"\"},\"mobile\":\"18351867657\",\"name\":\"TTTTT\",\"phone\":\"18351867657\"},\"order_info_dtos\":[{\"object_id\":\"416906705\",\"order_info\":{\"channels_type\":\"PDD\",\"order_list\":[\"CD0044\"]},\"package_info\":{\"items\":[{\"count\":1,\"name\":\"纸尿裤\"},{\"count\":1,\"name\":\"卫生巾\"}],\"packages_count\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"北京市\",\"country\":\"CN\",\"detail\":\"欢口镇还口村\",\"district\":\"大兴区\",\"province\":\"北京\"},\"mobile\":\"10086\",\"name\":\"小三\",\"phone\":\"10086\"},\"template_url\":\"https://baxitv.com\",\"user_id\":2986}],\"wp_code\":\"SFJT\"}}";
            Map<String, Object> header = buildPddV2Header(deptNo, null);
            header.put(PddTemplateConstants.ORIGIN_REQUEST_KEY, PddTemplateConstants.ORIGIN_REQUEST_KEY_1);
            header.put(PddTemplateConstants.ORIGIN_REQUEST_BODY, param);
            CommonTransport commonTransport = new CommonTransport();
            commonTransport.setWarehouseType(31);
            commonTransport.setBizType(bizType);
            commonTransport.setBizNo(bizNo);
            commonTransport.setHeader(header);
            commonTransport.setBodyMsg(param);
            String tokenResult = (String) commonSynchronousService.handler(commonTransport);
            logger.error("=======buildPddV2Token返回结果为【" + tokenResult + "】=======");
            return Result.success(tokenResult, "成功");
        } catch (Exception e) {
            logger.error("buildPddV2Token 请求系统异常", e);
            return Result.error("失败");
        }
    }
}
