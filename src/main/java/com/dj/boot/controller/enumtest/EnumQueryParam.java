package com.dj.boot.controller.enumtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 枚举查询参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnumQueryParam {
    @NotNull(message = "查询类型不能为空 !")
    private String enumType;
}
