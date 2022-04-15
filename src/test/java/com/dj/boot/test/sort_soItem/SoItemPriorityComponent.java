package com.dj.boot.test.sort_soItem;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.BootDemoApplicationTests;
import org.apache.commons.compress.utils.Lists;

import java.math.BigDecimal;
import java.util.*;

/**
 *  优先级规则
 *    先按价格高低设置出库优先次序； 价高优先
 *    当价格一致（或缺失的情况）时，按数量大小做出库优先级； 量大的优先
 *    当价格，数量均一致的情况下，按行编号大小做出库优先级； 小的优先
 *
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-01-28-16-19
 */
public class SoItemPriorityComponent extends BootDemoApplicationTests {

    @Override
    public void run() throws Exception {

        List<SoItemParam> soItemList = getSoItemParamList();

        final Map<String/*SKU*/, List<SoItemParam>/*相同SKU,SoItem列表*/> skuGroupListMap = new HashMap<>();

        final Set<String/*SKU*/> containNonPriceSkuSet = prepareSkuGroup(soItemList, skuGroupListMap);

        calculateAndAssignPriority(skuGroupListMap, containNonPriceSkuSet);

        logger.error(JSONObject.toJSONString(soItemList));
    }

    private List<SoItemParam> getSoItemParamList() {
        List<SoItemParam> soItemParamList = Lists.newArrayList();
        SoItemParam soItemParam1 = new SoItemParam();
        soItemParam1.setGoodsNo("sku1");
        soItemParam1.setApplyOutstoreQty(new BigDecimal(1));
        soItemParam1.setOrderLine("4");

        SoItemParam soItemParam2 = new SoItemParam();
        soItemParam2.setGoodsNo("sku1");
        soItemParam2.setApplyOutstoreQty(new BigDecimal(1));
        soItemParam2.setPrice(1.22);
        soItemParam2.setOrderLine("1");

        SoItemParam soItemParam3 = new SoItemParam();
        soItemParam3.setGoodsNo("sku1");
        soItemParam3.setApplyOutstoreQty(new BigDecimal(1));
        soItemParam3.setPrice(4.1);
        soItemParam3.setOrderLine("2");

        SoItemParam soItemParam4= new SoItemParam();
        soItemParam4.setGoodsNo("sku1");
        soItemParam4.setApplyOutstoreQty(new BigDecimal(3));
        soItemParam4.setPrice(4.1);
        soItemParam4.setOrderLine("5");

        SoItemParam soItemParam5= new SoItemParam();
        soItemParam5.setGoodsNo("sku1");
        soItemParam5.setApplyOutstoreQty(new BigDecimal(3));
        soItemParam5.setPrice(4.1);
        soItemParam5.setOrderLine("3");



        soItemParamList.add(soItemParam1);
        soItemParamList.add(soItemParam2);
        soItemParamList.add(soItemParam3);
        soItemParamList.add(soItemParam4);
        soItemParamList.add(soItemParam5);
        return soItemParamList;
    }

    /**
     * 以SKU分组并查找分组中包含Null价格SKU
     * @param soItemParams 待分组明细
     * @param skuGroupListMap 待填充的SKU分组
     * @return 分组中包含Null价格SKU
     */
    private Set<String> prepareSkuGroup(List<SoItemParam> soItemParams,
                                        Map<String/*SKU*/, List<SoItemParam>/*相同SKU,SoItem列表*/> skuGroupListMap) {
        //分组中包含Null价格SKU
        final Set<String/*SKU*/> containNonPriceSkuSet = new HashSet<String>();
        List<SoItemParam> groupList;
        String sku;
        for (SoItemParam soItemParam:soItemParams) {
            sku=soItemParam.getGoodsNo();
            if(skuGroupListMap.containsKey(sku)){
                groupList=skuGroupListMap.get(sku);
            }else {
                groupList=new ArrayList<SoItemParam>();
                skuGroupListMap.put(sku,groupList);
            }
            groupList.add(soItemParam);
            if(soItemParam.getPrice()==null){
                containNonPriceSkuSet.add(sku);
            }
        }
        return containNonPriceSkuSet;
    }

    /**
     * 计算优先级并设置优先级
     * @param skuGroupListMap sku分组Map
     * @param containNonPriceSkuSet 分组中包含Null价格SKU
     */
    private void calculateAndAssignPriority(Map<String/*SKU*/, List<SoItemParam>/*相同SKU,SoItem列表*/> skuGroupListMap,
                                            Set<String> containNonPriceSkuSet) {
        final Set<Map.Entry<String/*SKU*/,List<SoItemParam>/*相同SKU,SoItem列表*/>> groupListEntrySet=skuGroupListMap.entrySet();
        for (Map.Entry<String,List<SoItemParam>> groupListEntry: groupListEntrySet) {
            if(containNonPriceSkuSet.contains(groupListEntry.getKey())){
                groupListEntry.getValue().sort(new NonPricePriorityComparator());
            }else {
                Collections.sort(groupListEntry.getValue(),new PriorityComparator());
            }
        }
        int minPriority;
        for (Map.Entry<String,List<SoItemParam>> groupListEntry: groupListEntrySet) {
            minPriority = groupListEntry.getValue().size();
            for (int idx = 0; idx <= minPriority - 1; idx++) {
                groupListEntry.getValue().get(idx).setPriority(minPriority - idx);
            }
        }

    }


    //排序后 优先级低的在前
    private static class PriorityComparator implements Comparator<SoItemParam> {
        //类似 order by price asc,applyOutstoreQty asc,orderLine desc
        @Override
        public int compare(SoItemParam itemA, SoItemParam itemB) {
            int sort = sortByPrice(itemA.getPrice(), itemB.getPrice());
            if (sort == 0) {
                sort = sortByApplyOutstoreQty(itemA.getApplyOutstoreQty(), itemB.getApplyOutstoreQty());
                if (sort == 0) {
                    sort = sortByOrderLine(itemA.getOrderLine(), itemB.getOrderLine());
                }
            }
            return sort;
        }
    }


    private static class NonPricePriorityComparator implements Comparator<SoItemParam>{
        @Override
        public int compare(SoItemParam itemA, SoItemParam itemB) {
            int sort = sortByApplyOutstoreQty(itemA.getApplyOutstoreQty(), itemB.getApplyOutstoreQty());
            if (sort == 0) {
                sort = sortByOrderLine(itemA.getOrderLine(), itemB.getOrderLine());
            }
            return sort;
        }
    }

    //asc
    private static int sortByPrice(Double priceA, Double priceB) {
        return priceA.compareTo(priceB);
    }

    //asc
    private static int sortByApplyOutstoreQty(BigDecimal qtyA, BigDecimal qtyB) {
        return qtyA.compareTo(qtyB);
    }

    //desc
    private static int sortByOrderLine(String lA, String lB) {
        return lB.compareTo(lA);
    }

}
