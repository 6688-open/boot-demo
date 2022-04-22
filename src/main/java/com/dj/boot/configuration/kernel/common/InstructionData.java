package com.dj.boot.configuration.kernel.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-10-52
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class InstructionData {
    private String instructions;
    private int type;
    private String nextStep;
}
