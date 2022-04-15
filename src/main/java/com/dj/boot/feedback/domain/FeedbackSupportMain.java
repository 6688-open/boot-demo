package com.dj.boot.feedback.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSupportMain {

    private String bizType;

    private String bizNo;

    private String deptNo;
}
