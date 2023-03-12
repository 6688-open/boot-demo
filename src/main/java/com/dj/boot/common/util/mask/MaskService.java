package com.dj.boot.common.util.mask;

public interface MaskService {

    Object maskFor(Object maskObj);
    String maskName(String name);
    String maskCertNo(String certNo);
    String maskTelephone(String telephone);
}
