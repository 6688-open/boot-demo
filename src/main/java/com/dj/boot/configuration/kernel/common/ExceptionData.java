package com.dj.boot.configuration.kernel.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-10-51
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionData {
    private Exception e;
}
