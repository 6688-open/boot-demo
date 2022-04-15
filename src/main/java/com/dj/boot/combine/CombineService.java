package com.dj.boot.combine;

import com.dj.boot.combine.dto.Command;
import com.dj.boot.combine.dto.Result;

/**
 * @Author: wangJia
 * @Date: 2022-04-13-10-09
 */
public interface CombineService {

    Result<String> execute(Command<String> command);
}
