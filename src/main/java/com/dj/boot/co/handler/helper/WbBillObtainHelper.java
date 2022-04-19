package com.dj.boot.co.handler.helper;

import com.dj.boot.btp.exception.BizException;
import com.dj.boot.co.handler.dto.GetWaybillDto;
import com.dj.boot.co.handler.dto.ObtainWaybillParam;
import com.dj.boot.co.handler.dto.ObtainWaybillResponse;
import com.dj.boot.combine.handler.enums.PddTemplateConstants;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.co.handler.helper
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2022-04-19-14-41
 */
@Component("wbBillObtainHelper")
public class WbBillObtainHelper {

    private static Logger logger = LogManager.getLogger(WbBillObtainHelper.class);

    @Resource
    private CommonSynchronousService commonSynchronousService;


    public GetWaybillDto getWaybill(ObtainWaybillParam waybillParam, ObtainWaybillResponse response) {
        try {
            //必填校验
            GetWaybillDto getWaybillDto = doGetWaybill(waybillParam,response);
            return getWaybillDto;
        } catch (Exception wbEx) {
            String msg = wbEx.getMessage();
            throw new BizException("单号："+waybillParam.getWbNo()+",doGetWaybill 获取外部运单号失败，原因" + msg);
        }
    }

    private GetWaybillDto doGetWaybill(ObtainWaybillParam waybillParam, ObtainWaybillResponse response) {
        try {
            String param = "{\"param_request\":{\"need_encrypt\":true,\"sender\":{\"address\":{\"city\":\"宁市\",\"country\":\"CN\",\"detail\":\"路五方\",\"district\":\"曙区\",\"province\":\"浙省\",\"town\":\"\"},\"mobile\":\"18351867657\",\"name\":\"TTTTT\",\"phone\":\"18351867657\"},\"order_info_dtos\":[{\"object_id\":\"416906705\",\"order_info\":{\"channels_type\":\"PDD\",\"order_list\":[\"CD0044\"]},\"package_info\":{\"items\":[{\"count\":1,\"name\":\"纸尿裤\"},{\"count\":1,\"name\":\"卫生巾\"}],\"packages_count\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"北京市\",\"country\":\"CN\",\"detail\":\"欢口镇还口村\",\"district\":\"大兴区\",\"province\":\"北京\"},\"mobile\":\"10086\",\"name\":\"小三\",\"phone\":\"10086\"},\"template_url\":\"https://baxitv.com\",\"user_id\":2986}],\"wp_code\":\"SFJT\"}}";
            Map<String, Object> header = buildPddV2Header("deptNo", null);
            header.put(PddTemplateConstants.ORIGIN_REQUEST_KEY, PddTemplateConstants.ORIGIN_REQUEST_KEY_1);
            header.put(PddTemplateConstants.ORIGIN_REQUEST_BODY, param);
            CommonTransport commonTransport = new CommonTransport();
            commonTransport.setWarehouseType(31);
            commonTransport.setBizType(1);
            commonTransport.setBizNo("bizNo");
            commonTransport.setHeader(header);
            commonTransport.setBodyMsg(param);
            String tokenResult = (String) commonSynchronousService.handler(commonTransport);
            logger.error("=======buildPddV2Token返回结果为【" + tokenResult + "】=======");
            GetWaybillDto getWaybillDto = new GetWaybillDto();
            return getWaybillDto;
        } catch (Exception e) {
            logger.error("buildPddV2Token 请求系统异常", e);
        }
        return null;
    }

    private Map<String, Object> buildPddV2Header(String deptNo, String token) {
        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put(PddTemplateConstants.CLIENT_ID, "1234567898765432");
        header.put(PddTemplateConstants.CLIENT_SECRET, "sdghjkjhgfdsa23456789yfds");
        header.put(PddTemplateConstants.ACCESS_TOKEN, token);
        header.put(PddTemplateConstants.ORIGIN_RESP, PddTemplateConstants.ORIGIN_RESP_1);
        return header;
    }
}
