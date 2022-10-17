package com.dj.boot.controller.excel.listener;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 解析文件附件
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-10-11-17-26
 */
@Slf4j
public class ExcelCompareListener<T> extends AnalysisEventListener<Map<Integer, String>> {

    private List<T> cachedDataList = new ArrayList<>();
    private Map<Integer, String> headMap = new HashMap<>();
    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {

        Map<String, String> dataTmp = new HashMap<>(16);
        BillDifferData billDifferData = new BillDifferData();
        //判断是否有合并单元格
        data.forEach((key, value)->{
            if(!StringUtils.isEmpty(value)){
                if((headMap.containsKey(key) && StringUtils.isEmpty(headMap.get(key))) || (!headMap.containsKey(key))){
                    log.error("数据有合并单元格或数据没有列头");
                }
            }
        });

        //拼装数据到map集合中
        headMap.forEach((key,value)->{
            if(!StringUtils.isEmpty(value)){
                verifyValue(key, data.get(key), billDifferData);
                dataTmp.put(value, StringUtils.isEmpty(data.get(key))?"":data.get(key));
            }
        });

        billDifferData.setJsonData(JSONObject.toJSONString(sortedMap(dataTmp)));

        cachedDataList.add((T) billDifferData);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<T> getDatas() {
        return cachedDataList;
    }

    /**
     * Returns the header as a map.Override the current method to receive header data.
     *
     * @param data
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> data, AnalysisContext context) {
        atomicInteger.getAndIncrement();
        this.headMap = data;

        if(atomicInteger.get() == 4) {
            verifyHead();
        }

    }

    private void verifyHead() {
        Set<String> head = new HashSet<>();
        List<String> repetitiveColumn = new ArrayList<>();
        //判断是否重复列头
        headMap.forEach((key,value)->{
            if(!StringUtils.isEmpty(value)){
                if(head.contains(value)){
                    repetitiveColumn.add(value);
                }else {
                    head.add(value);
                }
            }
        });

        if(!CollectionUtils.isEmpty(repetitiveColumn)){
            log.error("附件不合法,存在重复列"+String.join(",", repetitiveColumn));
        }

    }


    private void verifyValue(int j, String c, BillDifferData billDifferData) {
        if (j == 0 || j == 1|| j == 2) {
            if (StringUtils.isEmpty(c)) {
                log.error("附件 业务年月/证件类型/证件号码不允许为空");
            }
            switch (j) {
                case 0:
                    verifyBusiYm(c);
                    billDifferData.setBusiYm(c);
                    break;
                case 1:
                    billDifferData.setCertificateType(c);
                    break;
                case 2:
                    billDifferData.setCertificateNum(c);
                    break;
                default:
                    break;
            }

        }

    }

    private Map<String, String> sortedMap(Map<String, String> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> newVal, LinkedHashMap::new));
    }

    private void verifyBusiYm(String ym) {
        // 校验YYYYMM格式
        String regex = "^(20|19){1}[0-9]{2}(((0){1}[1-9]{1})|((1){1}(0|1|2){1}))$";
        boolean matches = Pattern.matches(regex, ym);
        if (!matches) {
            log.error("业务年月格式不合法");
        }

    }

    public static void main(String[] args) {
        String str = "213309";
        //boolean b = verifyBusiYm(str);
        boolean validCard18 = IdcardUtil.isValidCard18("31022219480915");
        //IdcardUtil.
        //System.out.println(b);
    }
}
