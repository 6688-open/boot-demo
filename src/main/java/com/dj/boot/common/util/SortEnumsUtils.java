package com.dj.boot.common.util;

import com.dj.boot.common.enums.Option;
import com.dj.boot.common.enums.base.BaseOptionEnum;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

/**
 * 排序工具
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-09-26-10-22
 */
@Slf4j
public class SortEnumsUtils {

    public static void translateSortEnums(/*SortDto sortDto, SortBusinessTypeEnums sortBusinessTypeEnums, SortTypeEnums sortTypeEnums*/){
        Reflections reflections = new Reflections("com.dj.boot.common.enums");

        Set<Class<? extends BaseOptionEnum>> monitorClasses = reflections.getSubTypesOf(BaseOptionEnum.class);

        List<Option> optionList = Lists.newArrayList();
        for (Class<? extends BaseOptionEnum> m : monitorClasses) {
            BaseOptionEnum anEnum = m.getEnumConstants()[0];
            optionList.addAll(anEnum.getOptionList());
        }

        /*java.util.List<Option> list = optionList.stream().filter(option -> option.getBusinessType().equals(sortBusinessTypeEnums.getType()) && option.getFromCol().equals(sortDto.getSortColumn())).collect(Collectors.toList());
        SortTypeMappingEnums sortTypeMappingEnums = SortTypeMappingEnums.of(sortDto.getSortType(), sortTypeEnums.getType());
        ComSortDto comSortDto = new ComSortDto();
        if (CollectionUtils.isNotEmpty(list) && null != sortBusinessTypeEnums) {
            Option option = list.get(0);
            comSortDto.setSortColumn(option.getToCol());
            comSortDto.setSortType(sortTypeMappingEnums.getToCode());
        }*/
    }


    public static void main(String[] args) {
        /*SortDto sortDto = new SortDto();
        sortDto.setSortColumn("billAmount");
        sortDto.setSortType("asc");*/
        //SortBusinessTypeEnums.HR_BILL_LIST.getType();
        translateSortEnums(/*sortDto, SortBusinessTypeEnums.HR_BILL_LIST, SortTypeEnums.SORT_E*/);
    }
}
