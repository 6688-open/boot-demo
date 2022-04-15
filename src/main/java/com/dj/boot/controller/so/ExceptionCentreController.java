package com.dj.boot.controller.so;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.btp.common.util.noUtil.OrderNoUtil;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.page.PageRequestParam;
import com.dj.boot.common.util.es.EsmUtils;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.so.constant.SoErrResume;
import com.dj.boot.controller.so.constant.SoIssuedErrorEnum;
import com.dj.boot.controller.so.domain.*;
import com.dj.boot.pojo.User;
import com.dj.boot.service.proxytest.A;
import com.dj.boot.service.so.SoBizService;
import com.dj.boot.service.user.UserService;
import com.google.common.base.Joiner;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-14-16
 */
@Controller
@RequestMapping("/exceptionCentre/")
public class ExceptionCentreController extends BaseController {

    private static final int THREAD_POOL_SIZE = 5;

    @Autowired
    private SoBizService soBizService;

    @Autowired
    private UserService userService;

    private CompletionService<List<String>> completionService =
            new ExecutorCompletionService<List<String>>(Executors.newFixedThreadPool(THREAD_POOL_SIZE));


    /**
     * 跳转到订单异常详情查询页面
     *
     * @return
     */
    @RequestMapping(value = "gotoSoExceptionDetail", method = RequestMethod.GET)
    public String gotoSoExceptionDetail(Model model, String id) {
        User user = userService.getById(id);
        user.setPassword("跳转到订单异常详情查询页面");
        model.addAttribute("user", user);
        return "exception/so/exceptionDetails";
    }

    @ResponseBody
    @RequestMapping(value = "getExceptionListBySoId", method = RequestMethod.GET)
    public PageRequestParam getExceptionListBySoId(String id) {
        PageRequestParam<SoExceptionCondition> pageParams = new PageRequestParam<>();
        pageParams.setiTotalRecords(1);
        pageParams.setiDisplayLength(10);
        pageParams.setiDisplayStart(0);
        try {
            List<SoExceptionCondition> sList = new LinkedList<SoExceptionCondition>();
            SoExceptionCondition e = new SoExceptionCondition();
            e.setEType("302");
            e.setErrReason("下发失败,原因：尊敬的客户您好！受防疫举措变化影响，当地暂缓提供收派服务，感谢理解");
            e.setErrStatus("1");
            e.setErrStatusDesc("异常");
            e.setId("200000");
            e.setPauseTime("2022-02-23 18:19:06");
            e.setErrDuration("1");
            e.setSoId(id);
            e.setOperateUser("wj");
            e.setErrType(SoIssuedErrorEnum.getDescByCode(Integer.valueOf(e.getEType())));
            //获取操作按钮
            String errResumeBtn = SoErrResume.getProcessBtn(Integer.valueOf(e.getEType()));
            HashMap butMap = JSON.parseObject(errResumeBtn,new TypeReference<HashMap>() {});
            JSONArray butArray = (JSONArray)butMap.get("errDisposes");
            for(int i=butArray.size()-1;i>=0;i--){
                //JSONObject jsonObject = butArray.getJSONObject(i);
                /*if(){//校验是否可以操作 删除操作按钮
                    butArray.remove(jsonObject);
                    continue;
                }*/
            }
            e.setProcessBtn(JSON.toJSONString(butMap));
            sList.add(e);
            pageParams.setAaData(sList);
        } catch (Exception e) {
            logger.error("数据转换异常", e);
        }
        return pageParams;
    }

    @RequestMapping(value = "/getProcessBtn", method = RequestMethod.POST)
    @ResponseBody
    public Response getProcessBtn(String  errType) {
        Response<Object> response = Response.success();
        if (StringUtils.isBlank(errType)||!StringUtils.isNumeric(errType)) {
            return Response.error(BaseResponse.ERROR_PARAM, BaseResponse.PERMIT_EXCEPTION_MESSAGE);
        }
        response.setData(SoErrResume.getProcessBtn(Integer.valueOf(errType)));
        return response;
    }


    @PostMapping("batchResumeException")
    @ResponseBody
    public Response batchResumeException(String soExceptionNos, String errType, Integer disposeType, String resumeReason) {
        logger.error("soExceptionNos:{}, errType:{}, disposeType:{}, resumeReason:{}", soExceptionNos, disposeType, disposeType, resumeReason);
        Response<Object> response = Response.success();
        String[] soExceptionSoNos = soExceptionNos.split(",");
        List<String> fails = new ArrayList<String>();
        List<SoExceptionParam> soExceptionParams = new ArrayList<SoExceptionParam>();
        for(String soNo:soExceptionSoNos){
            SoExceptionParam  soExceptionParam = new SoExceptionParam();
            soExceptionParam.setSoId(OrderNoUtil.reverseNo2Id(soNo, Constant.BizType.CSL_SERVICE));
            soExceptionParam.setErrType(SoIssuedErrorEnum.SoIssuedErrorEnum_MAP.get(Integer.valueOf(errType)));
            soExceptionParam.setRenewReason(resumeReason);
            soExceptionParam.setOperateUser("wj");
            soExceptionParams.add(soExceptionParam);
        }
        final Integer innerDisposeType = disposeType;
        ExceptionCentreHandler exceptionCentreHandler = new ExceptionCentreHandler() {
            @Override
            public Response tryResume(SoExceptionParam soExceptionParam) {
                return soBizService.tryResumeException(soExceptionParam, innerDisposeType);
            }
        };
        List<String> batchFailures = doTryResumeException(soExceptionParams, exceptionCentreHandler);
        fails.addAll(batchFailures);
        if(CollectionUtils.isNotEmpty(fails)){
            response.setMsg("以下单据恢复出错，请稍后再试。"+ Joiner.on(",").join(fails));
        }else{
            response.setMsg("操作成功");
        }
        return response;
    }



    private  List<String> doTryResumeException(List<SoExceptionParam> works, ExceptionCentreHandler exceptionCentreHandler) {
        List<String> failResult = new ArrayList<String>(50);
        try {
            int slice = 1;
            int perSize = works.size() / THREAD_POOL_SIZE;
            if (works.size() <= THREAD_POOL_SIZE) {
                completionService.submit(new ExceptionCentreWorker(works, exceptionCentreHandler));
            } else {
                for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                    if (i != THREAD_POOL_SIZE - 1) {
                        completionService.submit(new ExceptionCentreWorker(works.subList(perSize * i, perSize * (i + 1)), exceptionCentreHandler));
                    } else {
                        completionService.submit(new ExceptionCentreWorker(works.subList(perSize * i, works.size()), exceptionCentreHandler));
                    }
                }
                slice = THREAD_POOL_SIZE;
            }
            for (int i = 0; i < slice; i++) {
                Future<List<String>> takeResult = completionService.take();
                if (CollectionUtils.isNotEmpty(takeResult.get())) {
                    failResult.addAll(takeResult.get());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("系统异常");
        }
        return failResult;
    }



    @ResponseBody
    @RequestMapping(value = "queryShippersBySoNo.do", method = RequestMethod.POST)
    public Response queryShippersBySoNo(String  soNo){
        Response<Object> response = Response.success();
        List<LogisticsServiceShipper> logisticsServiceShippers = Lists.newArrayList();

        LogisticsServiceShipper logisticsServiceShipper = new LogisticsServiceShipper();
        logisticsServiceShipper.setShipperNo("111");
        logisticsServiceShipper.setShipperName("1111");
        logisticsServiceShipper.setPerformSystem("1");

        LogisticsServiceShipper logisticsServiceShipper1 = new LogisticsServiceShipper();
        logisticsServiceShipper1.setShipperNo("222");
        logisticsServiceShipper1.setShipperName("2222");
        logisticsServiceShipper.setPerformSystem("1");
        logisticsServiceShippers.add(logisticsServiceShipper);
        logisticsServiceShippers.add(logisticsServiceShipper1);

        response.setData(logisticsServiceShippers);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "tryResumeException.do", method = RequestMethod.POST)
    public Response tryResumeException(SoExceptionParam soExceptionParam, SoExceptionResumeParam soExceptionResumeParam, Integer disposeType) throws InterruptedException {
        logger.error("soExceptionParam:{}", JSONObject.toJSONString(soExceptionParam));
        logger.error("soExceptionResumeParam:{}", JSONObject.toJSONString(soExceptionResumeParam));
        logger.error("disposeType:{}", disposeType);
        if (disposeType == 9) {
            return new Response(BaseResponse.ERROR_BUSINESS, BaseResponse.PERMIT_EXCEPTION_MESSAGE);
        }
        return Response.success();
    }

    @ResponseBody
    @RequestMapping(value = "batchTryResumeException.do", method = RequestMethod.POST)
    public Response batchTryResumeException(SoExceptionParam soExceptionParam, SoExceptionResumeParam soExceptionResumeParam, Integer[] disposeType, String errAddress) throws InterruptedException {
        logger.error("soExceptionParam:{}", JSONObject.toJSONString(soExceptionParam));
        logger.error("soExceptionResumeParam:{}", JSONObject.toJSONString(soExceptionResumeParam));
        logger.error("disposeType:{}", disposeType);
        Response<Object> response = Response.success();
        Arrays.sort(disposeType);
        for (int k = disposeType.length-1; k >= 0; k--) {
            if (disposeType[k] == 5) {
                soExceptionParam.setRenewReason(errAddress);
            }
            response = tryResumeException(soExceptionParam, soExceptionResumeParam, disposeType[k]);
            if (response.getCode() != 200) {
                return response;
            }
        }
        return Response.success();
    }








}
