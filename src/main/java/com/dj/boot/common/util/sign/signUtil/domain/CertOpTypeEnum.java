package com.dj.boot.common.util.sign.signUtil.domain;

import com.dj.boot.common.enums.LockType;
import com.dj.boot.common.enums.Option;

import java.util.*;

public enum CertOpTypeEnum {

    IS_CON(102, "实名信息是否一致");

    private Integer code;
    private String name;

    CertOpTypeEnum() {
    }

    CertOpTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public static Map<Integer, String> ENUM_MAP = new HashMap<>();


    public static Map<Integer, CertOpTypeEnum> MAP = new HashMap<>();

    public static final List<Option> OPTIONS = new ArrayList<Option>();


    public static String parse(Integer code) {
        CertOpTypeEnum[] array = CertOpTypeEnum.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getCode().equals(code)) {
                return array[i].getName();
            }
        }
        return "";
    }

    public static Integer parse(String name) {
        LockType[] array = LockType.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getName().equals(name)) {
                return array[i].getCode();
            }
        }
        return null;
    }

    static {
        for (CertOpTypeEnum e : EnumSet.allOf(CertOpTypeEnum.class)) {
            Option op = new Option();
            op.setCode(String.valueOf(e.code));
            op.setName(e.getName());
            OPTIONS.add(op);
            ENUM_MAP.put(e.code, e.name);
        }
    }


    static {
        Iterator i$ = EnumSet.allOf(CertOpTypeEnum.class).iterator();

        while(i$.hasNext()) {
            CertOpTypeEnum e = (CertOpTypeEnum)i$.next();
            MAP.put(e.code, e);
        }

    }
}
