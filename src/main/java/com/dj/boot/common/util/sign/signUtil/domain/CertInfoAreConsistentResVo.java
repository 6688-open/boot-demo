package com.dj.boot.common.util.sign.signUtil.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertInfoAreConsistentResVo {

    private String appNo;
    private String bizNo;
    private String token;
    private String userId;
}
