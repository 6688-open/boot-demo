package com.dj.boot.common.util.sign.signUtil.domain;

import com.dj.boot.common.util.collection.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppConfig {

    private String appNo;
    private String bizNo;
    private String privateKey;
    private List<String> methodSet;

    public List<Integer> methodSet(){
        List<Integer> re = Lists.newArrayList();
        List<String> methodSet = this.methodSet;
        if (CollectionUtils.isNotEmpty(methodSet)) {
            methodSet.forEach(s -> {
                re.add(Integer.valueOf(s));
            });
        }
        return re;

    }

}
