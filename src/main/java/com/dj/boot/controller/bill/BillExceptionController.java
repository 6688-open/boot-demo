package com.dj.boot.controller.bill;

import com.dj.boot.common.base.Response;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.bill.constant.BillExceptionEnum;
import com.dj.boot.controller.bill.constant.BillTypeEnum;
import com.dj.boot.controller.bill.domain.BillExceptionDto;
import com.dj.boot.controller.bill.domain.BillExceptionHandler;
import com.dj.boot.controller.bill.domain.BillExceptionRequest;
import com.dj.boot.controller.bill.domain.BillExceptionWorker;
import com.dj.boot.service.bill.ExceptionService;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-15-16-59
 */
@RestController
@RequestMapping("/bill/")
public class BillExceptionController extends BaseController {

    private static final int THREAD_POOL_SIZE = 5;

    private CompletionService<List<String>> completionService =
            new ExecutorCompletionService<List<String>>(Executors.newFixedThreadPool(THREAD_POOL_SIZE));

    @Autowired
    private ExceptionService exceptionService;

    @PostMapping("insertBillException")
    public Response insertBillException(String idAndbillTypes,  String resumeReason) {
        List<BillExceptionRequest> requests = translateParam(idAndbillTypes, resumeReason);
        exceptionService.execute(requests.get(0));
        return Response.success();
    }


    @PostMapping("batchResumeException")
    public Response batchResumeException(String idAndbillTypes,  String resumeReason) {
        Response<Object> response = Response.success();
        List<String> fails = new ArrayList<String>();

        List<BillExceptionRequest> requests = translateParam(idAndbillTypes, resumeReason);

        BillExceptionHandler exceptionCentreHandler = new BillExceptionHandler() {
            @Override
            public Response tryResume(BillExceptionRequest request) {
                return exceptionService.retryException(request);
            }
        };
        List<String> batchFailures = doTryResumeException(requests, exceptionCentreHandler);
        fails.addAll(batchFailures);
        if(CollectionUtils.isNotEmpty(fails)){
            response.setMsg("以下单据恢复出错，请稍后再试。"+ Joiner.on(",").join(fails));
        }else{
            response.setMsg("操作成功");
        }
        return response;
    }

    private List<BillExceptionRequest> translateParam(String idAndbillTypes, String resumeReason) {
        //异常单据ID-billCode-exceptionCode
        //12144_3_4000
        String[] idAndbillTypeList = idAndbillTypes.split(",");
        Integer exceptionCode = null;
        List<BillExceptionRequest> requests = new ArrayList<>();
        for(String idAndbillType:idAndbillTypeList){
            long id = Long.valueOf(idAndbillType.split("_")[0]);
            String billType =idAndbillType.split("_")[1];
            exceptionCode = Integer.valueOf(idAndbillType.split("_")[2]);

            BillExceptionRequest request = new BillExceptionRequest();
            request.setBillType(BillTypeEnum.getBusinessTypeByCode(Integer.valueOf(billType)));
            BillExceptionDto bill = new BillExceptionDto();
            bill.setId(id);
            bill.setRenewReason(resumeReason);
            bill.setExceptionCode(exceptionCode);
            bill.setUpdateUser("wj");
            request.setData(bill);
            requests.add(request);
        }
        return requests;
    }

    private  List<String> doTryResumeException(List<BillExceptionRequest>  works, BillExceptionHandler exceptionCentreHandler ) {
        List<String> failResult = new ArrayList<String>(50);
        try {
            int slice = 1;
            int perSize = works.size() / THREAD_POOL_SIZE;
            if (works.size() <= THREAD_POOL_SIZE) {
                completionService.submit(new BillExceptionWorker(works, exceptionCentreHandler));
            } else {
                for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                    if (i != THREAD_POOL_SIZE - 1) {
                        completionService.submit(new BillExceptionWorker(works.subList(perSize * i, perSize * (i + 1)), exceptionCentreHandler));
                    } else {
                        completionService.submit(new BillExceptionWorker(works.subList(perSize * i, works.size()), exceptionCentreHandler));
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
            logger.error("batchResumeException",e);
            throw new RuntimeException("系统异常");
        }
        return failResult;
    }

    @ResponseBody
    @RequestMapping(value = "getExceptionTypeByBillType")
    public List<Map<String,String>> getExceptionTypeByBillType(@RequestParam("billType") Integer billType){
        if (billType == null ) {
            return null;
        }
        List<Map<String,String>> resultList = new ArrayList<>();
        for(Map.Entry<String, BillTypeEnum> entry : BillTypeEnum.BILL_TYPE_ENUM_DETAIL_MAP.entrySet()){

            String key = entry.getKey();
            Integer exceptionCode = entry.getValue().getExceptionCode();
            if (key.split("_")[0].equals(billType.toString())) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("code", exceptionCode.toString());
                resultMap.put("name", BillExceptionEnum.getDescByCode(exceptionCode));
                resultList.add(resultMap);
            }
        }
        return resultList;
    }
}
