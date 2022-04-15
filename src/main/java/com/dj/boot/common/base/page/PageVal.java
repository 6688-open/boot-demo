package com.dj.boot.common.base.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.base.page
 * @Author: wangJia
 * @Date: 2021-05-25-15-43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageVal {
    private String name;
    private String value;

}
