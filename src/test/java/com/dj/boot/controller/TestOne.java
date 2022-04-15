package com.dj.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.controller.bill.domain.BillExceptionDto;
import com.dj.boot.controller.user.vo.SourcingResponseDto;
import com.dj.boot.controller.user.vo.WaybillSourcingDto;
import com.dj.boot.pojo.useritem.ExceptionConf;
import com.dj.boot.service.useritem.UserItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @ClassName TestOne
 * @Description TODO
 * @Author wj
 * @Date 2019/12/23 16:37
 * @Version 1.0
 **/
public class TestOne  extends BootDemoApplicationTests {

    @Autowired
    private UserItemService userItemService;

    @Override
    public void run() {

        buildExceptionConfig();


        SourcingResponseDto sourcingResponseDto = new SourcingResponseDto();
        WaybillSourcingDto waybillSourcingDto = new WaybillSourcingDto();
        waybillSourcingDto.setDeptName("setDeptName");
        waybillSourcingDto.setPartnerName("setPartnerName");

        List<WaybillSourcingDto> waybillSourcingDtoList = sourcingResponseDto.getWaybillSourcingList();
        if (waybillSourcingDtoList == null) {
            waybillSourcingDtoList = new ArrayList<>();
            sourcingResponseDto.setWaybillSourcingList(waybillSourcingDtoList);
        }
        waybillSourcingDtoList.add(waybillSourcingDto);

        //long l = Long.parseLong((String) ("Y000000615"));
        String str = "Y000000615";
        //Pattern pattern = Pattern.compile("^-?[0-9]+");
        if(!Pattern.compile("^-?[0-9]+").matcher(str).matches()){
            //数字
            System.out.println(333);
        } else {
            //非数字
            System.out.println(222);
        }
        System.out.println(111);
    }

    private void buildExceptionConfig() {
        List<ExceptionConf> exceptionConfDtoList = userItemService.queryExceptionConf();
        //描述转原因 转换关系map
        Map<String, String> desc2reasonMap = new HashMap<>();
        //key : 异常原因 , value : 处理方案。
        Map<String, String> reason2handlerAction = new HashMap<>();
        //key: 异常原因, value : 处理方
        Map<String, Integer> reason2handlerGroup = new HashMap<>();
        //key : 描述 , value : 处理方案。
        Map<String, String> desc2handlerAction = new HashMap<>();
        //key: 描述, value : 处理方
        Map<String, Integer> desc2handlerGroup = new HashMap<>();

        //原因转描述 转换关系map  查询条件要用
        Map<String, HashSet<String>> reason2descMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(exceptionConfDtoList)) {
            for (ExceptionConf exceptionConfDto : exceptionConfDtoList) {
                if (StringUtils.isBlank(exceptionConfDto.getExceptionReason())) {
                    desc2reasonMap.put(exceptionConfDto.getExceptionDesc(), exceptionConfDto.getExceptionDesc());
                    desc2handlerAction.put(exceptionConfDto.getExceptionDesc(), exceptionConfDto.getHandlerAction());
                    desc2handlerGroup.put(exceptionConfDto.getExceptionDesc(), exceptionConfDto.getHandlerGroup());
                } else {
                    desc2reasonMap.put(exceptionConfDto.getExceptionDesc(), exceptionConfDto.getExceptionReason());
                    reason2handlerAction.put(exceptionConfDto.getExceptionReason(), exceptionConfDto.getHandlerAction());
                    reason2handlerGroup.put(exceptionConfDto.getExceptionReason(), exceptionConfDto.getHandlerGroup());
                    if (reason2descMap.containsKey(exceptionConfDto.getExceptionReason())) {
                        reason2descMap.get(exceptionConfDto.getExceptionReason()).add(exceptionConfDto.getExceptionDesc());
                    } else {
                        HashSet<String> descStrSet = new HashSet<>();
                        descStrSet.add(exceptionConfDto.getExceptionDesc());
                        reason2descMap.put(exceptionConfDto.getExceptionReason(), descStrSet);
                    }
                }

            }
        }
        List<BillExceptionDto> billExceptionDtos = Lists.newArrayList();
        BillExceptionDto billExceptionDto = new BillExceptionDto();
        billExceptionDto.setExceptionDesc("包裹号不合法");
        BillExceptionDto billExceptionDto1 = new BillExceptionDto();
        billExceptionDto1.setExceptionDesc("");

        BillExceptionDto billExceptionDto2 = new BillExceptionDto();
        billExceptionDto2.setExceptionDesc("");

        //billExceptionDtos.add(billExceptionDto);
        //billExceptionDtos.add(billExceptionDto1);
        billExceptionDtos.add(billExceptionDto2);

        buildReasonAndHandlerInfo(billExceptionDtos, desc2reasonMap, reason2handlerAction,reason2handlerGroup,
                desc2handlerAction, desc2handlerGroup);

        logger.error("处理后数据:{}", JSONObject.toJSONString(billExceptionDtos));


    }
    private static final String EXCEPTION_SPLIT_TAG = "XXXXXXXX";
    private void buildReasonAndHandlerInfo(List<BillExceptionDto> billExceptionDtos, Map<String, String> desc2reasonMap,
                                           Map<String, String> reason2handlerAction, Map<String, Integer> reason2handlerGroup,
                                           Map<String, String> desc2handlerAction, Map<String, Integer> desc2handlerGroup) {
        if (CollectionUtils.isNotEmpty(billExceptionDtos)) {
            for (BillExceptionDto billExceptionDto: billExceptionDtos) {
                if (StringUtils.isNotBlank(billExceptionDto.getExceptionDesc())) {
                    for (String next : desc2reasonMap.keySet()) {
                        //描述关键字含变量特殊处理
                        if (next.equals(desc2reasonMap.get(next))) {
                            if (next.contains(EXCEPTION_SPLIT_TAG)) {
                                String[] splits = next.split(EXCEPTION_SPLIT_TAG);
                                boolean isMatch = true;
                                System.out.println(billExceptionDto.getExceptionDesc());
                                for (String split : splits) {
                                    System.out.println(split);
                                    if (StringUtils.isNotBlank(split) && !billExceptionDto.getExceptionDesc().contains(split)) {
                                        isMatch = false;
                                        break;
                                    }
                                }
                                if (isMatch && desc2handlerAction.containsKey(next)) {
                                    billExceptionDto.setHandlerAction(desc2handlerAction.get(next));
                                    billExceptionDto.setHandlerGroup(desc2handlerGroup.get(next));
                                    break;
                                }
                            }
                        } else {
                            if (billExceptionDto.getExceptionDesc().contains(next)) {
                                if(desc2reasonMap.get(next) !=null){
                                    billExceptionDto.setExceptionDesc(desc2reasonMap.get(next));
                                    break;
                                }
                            }
                        }

                    }
                    if (reason2handlerAction.containsKey(billExceptionDto.getExceptionDesc())) {
                        billExceptionDto.setHandlerAction(reason2handlerAction.get(billExceptionDto.getExceptionDesc()));
                        billExceptionDto.setHandlerGroup(reason2handlerGroup.get(billExceptionDto.getExceptionDesc()));
                    }
                }

            }
        }

    }
}
