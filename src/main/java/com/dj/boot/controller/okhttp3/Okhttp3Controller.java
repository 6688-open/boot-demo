package com.dj.boot.controller.okhttp3;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.combine.CombineService;
import com.dj.boot.combine.dto.BusinessTypeEnum;
import com.dj.boot.combine.dto.Command;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import com.dj.boot.controller.okhttp3.support.Result;
import com.dj.boot.pojo.KdnTrackReqDto;
import com.dj.boot.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author wangjia
 */
@RestController
@RequestMapping("/okhttp3/")
public class Okhttp3Controller {

    private static final Logger logger = LoggerFactory.getLogger(Okhttp3Controller.class);

    @Resource
    private CommonSynchronousService commonSynchronousService;


    @Resource
    private CombineService combineService;

    /**
     * 测试
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("fetchKdnTraces")
    public Result<String> fetchKdnTraces(KdnTrackReqDto req){

        final Result<String> result = new Result();

        if(StringUtils.isBlank(req.getLogisticCode())){
            result.setCode(Result.BAD_REQ);
            result.setDesc("缺失参数【logisticCode】");
            return result;
        }

        final String json = transformUpperCase(JsonUtil.toJson(req));
        try {
            final CommonTransport transport = new CommonTransport();
            transport.setBizNo(req.getLogisticCode());
            Map<String,Object> header = new HashMap<String, Object>();
            header.put("supplyArgs",true);
            header.put("APIKey","9462ab5a-e73e-4a3b-80cf-55a9b7ffc1c0");
            header.put("EBusinessID","1696923");
            header.put("RequestType","8002");
            header.put("DataType","2");
            transport.setHeader(header);
            transport.setBizType(389);
            transport.setWarehouseType(4);
            transport.setBodyMsg(json);

            logger.error("QueryKdnTrackServiceImpl-fetchKdnTraces 入参{}", JSONObject.toJSONString(transport));
            final String data = (String) commonSynchronousService.handler(transport);
            logger.error("QueryKdnTrackServiceImpl-fetchKdnTraces 响应结果{}", data);
            final ResultData resultData = JsonUtil.fromJson(data, ResultData.class);
            if(resultData.code==100){
                result.setCode(Result.SUCCESS);
                result.setData(resultData.result);
                result.setDesc(resultData.msg);
            }else {
                result.setCode(Result.ERROR);
                result.setDesc(resultData.msg);
            }
        } catch (Exception ex) {
            logger.error("【"+json+"】拉取轨迹异常", ex);
            result.setCode(Result.ERROR);
            result.setDesc("拉取轨迹异常");
        }
        return result;
    }


    /**
     * POST json提交
     * https://blog.csdn.net/zhuocailing3390/article/details/123214433 服务间传递header
     * @param user
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "测试")
    @PostMapping("echo")
    //public Response<User> echo(@RequestHeader("client_secret") String client_secret, @RequestBody User user) throws Exception {
    public Response<User> echo(@RequestHeader Map<String, String> headers, @RequestBody User user) throws Exception {
        logger.error("header信息:{}", JSONObject.toJSONString(headers));
        logger.error("user信息:{}", JSONObject.toJSONString(user));
        Response<User>  response = Response.success();
        response.setData(user);
        return response;
    }

    /**
     * POST form表单，多附件提交
     * action：url 地址，服务器接收表单数据的地址
     * method：提交服务器的http方法，通常为post和get
     * name：最好好吃name属性的惟一性
     * enctype: 表单数据提交时使用的编码类型，默认使用"pplication/x-www-form-urlencoded"，
     * 若是是使用POST请求，则请求头中的content-type指定值就是该值。
     * 若是表单中有上传文件，编码类型须要使用"multipart/form-data"，类型，才能完成传递文件数据。
     * @param
     * @param request
     * @return
     */
    //@RequestMapping(value = "word",method = RequestMethod.POST,produces = "application/json;charset=UTF-8",consumes = "application/x-www-form-urlencoded")
    @RequestMapping(value = "word",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
    public Response<User> word(@RequestHeader Map<String, String> headers, User user,HttpServletRequest request){
        Response<User>  response = Response.success();
        logger.error("header信息:{}", JSONObject.toJSONString(headers));
        logger.error("user信息:{}", JSONObject.toJSONString(user));
        User user1 = new User();
        user1.setUserName("wjwjwjwjjw");
        user1.setPassword("12345678987654");
        response.setData(user1);
        return response;
    }

    /**
     * http://localhost:8082/okhttp3/execute
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "测试")
    @PostMapping("execute")
    public com.dj.boot.combine.dto.Result<String> execute() throws Exception {
        Command<String> command = new Command<>();
        command.setBusinessType(BusinessTypeEnum.PDD_V2_GET_CUSTOM.businessType());
        command.setOperateType(BusinessTypeEnum.PDD_V2_GET_TOKEN.operateType());
        command.setData("{\"param_request\":\"{\\\"code\\\":\\\"3\\\",\\\"grant_type\\\":\\\"authorization_code\\\"}\"}");
        command.setVendorCode("1111111111111111");
        command.setVendorName("VendorName");
        Map<String,Object> businessMap = new HashMap<>();
        businessMap.put("token", "1234567890");
        businessMap.put("orderId", "12349");
        command.setBusinessMap(businessMap);
        com.dj.boot.combine.dto.Result<String> result = null;
        try {
            result = combineService.execute(command);
        } catch (Exception e) {
            logger.error("execute执行调用失败");
            return com.dj.boot.combine.dto.Result.error("execute执行调用失败");
        }
        return result;
    }








    // 将map的Key全部转换为大写
    public  String transformUpperCase(String json) {
        Map<String,Object> orgMap = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            resultMap.put(Character.toUpperCase(key.charAt(0)) + key.substring(1), orgMap.get(key));
        }
        return JSONObject.toJSONString(resultMap);
    }

    static class ResultData {
        private Integer code;
        private String msg;
        private String result;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }






}
