package com.dj.boot.pojo.echars;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo.echars
 * @User: wangjia
 * @Author: wangJia
 * @Date: 2021-03-26-17-46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EcharsDto {

    private String name;
    private Integer num;
}
