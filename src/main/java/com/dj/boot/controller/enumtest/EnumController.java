package com.dj.boot.controller.enumtest;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.enums.Option;
import com.dj.boot.common.enums.base.SelectTypeEnum;
import com.dj.boot.common.util.collection.CollectionUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "enum 根据类型 返回指定下拉框 操作接口")
@RestController
@RequestMapping("/enum/")
public class EnumController {

    /**
     * 如果为空 则去 查询  然后缓存下来  下次直接获取
     * 不为空 从缓存获取
     */
    private static Map<String, List<Option>> selectTypeMap;

    static {
        selectTypeMap = new HashMap<>();
    }

    @PostMapping("queryEnum")
    //@PostMapping(value = { "queryEnum" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public Response queryEnum(@Valid  @RequestBody EnumQueryParam enumQueryParam){
        if (!SelectTypeEnum.SelectTypeEnum_MAP.containsKey(enumQueryParam.getEnumType())) {
            return Response.success(BaseResponse.ERROR_PARAM, BaseResponse.PERMIT_EXCEPTION_MESSAGE);
        }
        List<Option> optionList = doGetEnumOption(enumQueryParam.getEnumType());
        return Response.success(BaseResponse.SUCCESS_CODE, BaseResponse.SUCCESS_MESSAGE, optionList);
    }


    private List<Option> doGetEnumOption(String dictType) {
        List<Option> optionList = selectTypeMap.get(dictType);
        if (CollectionUtils.isNullOrEmpty(optionList)) {
            SelectTypeEnum type = SelectTypeEnum.getByCode(dictType);
            optionList = type.getOptionList(type.getOptionEnum());
            if (CollectionUtils.isNotEmpty(optionList)) {
                selectTypeMap.put(dictType, optionList);
            }
        }
        return optionList;
    }

}
