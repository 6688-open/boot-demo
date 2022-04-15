package com.dj.boot.controller.so.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.google.common.collect.Lists;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单恢复常量
 * User: wj
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class SoErrResume {


    /**
     * 根据异常编码获取处理方式
     *
     * @return
     */
    public static String getProcessBtn(int errCode) {
        return ErrProcessEnum.getProcessBtn(errCode);
    }

    /**
     * 判断是否有权限操作
     *
     * @param errCode     异常编码
     * @param disposeType 操作类型
     * @param source      来源 admin  partner seller 为空则不校验
     * @return
     */
    public static boolean isAuthority(int errCode, int disposeType, Integer source) {
        return ErrProcessEnum.isAuthority(errCode, disposeType, source);
    }

    /**
     * 枚举值
     */
    public static final int ADMIN = 1;
    public static final int PARTNER = 2;
    public static final int SELLER = 3;


    /**
     * 异常对应的处理方式
     */
    private enum ErrProcessEnum {

        _R_20_2(SoIssuedErrorEnum.R_20_2.getCode(), ErrOperateEnum.MANUAL, Lists.newArrayList(ErrDisposeEnum.OCCUPYSOSTOCK), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE),//库存不足
        _R_30_2(SoIssuedErrorEnum.R_30_2.getCode(), ErrOperateEnum.MANUAL, Lists.newArrayList(ErrDisposeEnum.RESETTASK, ErrDisposeEnum.EDITSHIP, ErrDisposeEnum.EDITSO, ErrDisposeEnum.EDITBDADDR), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),//订单拉回
        //_R_30_2(SoIssuedErrorEnum.R_30_2.getCode(), ErrOperateEnum.MANUAL, Lists.newArrayList(ErrDisposeEnum.RESETWMS, ErrDisposeEnum.EDITSO), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),//订单拉回
        //_R_30_2(SoIssuedErrorEnum.R_30_2.getCode(), ErrOperateEnum.MANUAL, Lists.newArrayList(ErrDisposeEnum.REDEVELIVER), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),//订单拉回
        ;
        private int errCode;//异常编码
        private ErrOperateEnum errOperate;
        private List<ErrDisposeEnum> errDisposes; //异常处理方式
        private boolean admin; //是否支持商家端
        private boolean partner; //是否支持合作伙伴端
        private boolean seller; //是否支持商家端

        ErrProcessEnum(int errCode, ErrOperateEnum errOperate, List errDisposes, boolean admin, boolean partner, boolean seller) {
            this.errCode = errCode;
            this.errOperate = errOperate;
            this.errDisposes = errDisposes;
            this.admin = admin;
            this.partner = partner;
            this.seller = seller;
        }

        public String toJson() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errCode", errCode);
            jsonObject.put("errOperate", errOperate.desc);
            jsonObject.put("admin", admin);
            jsonObject.put("partner", partner);
            jsonObject.put("seller", seller);
            JSONArray jsonArray = new JSONArray();
            if (CollectionUtils.isNotEmpty(errDisposes)) {
                for (ErrDisposeEnum errDisposeEnum : errDisposes) {
                    jsonArray.add(errDisposeEnum.toJson());
                }
            }
            jsonObject.put("errDisposes", jsonArray);
            return jsonObject.toString();
        }

        private static Map<Integer, String> processBtnMap = new HashMap<Integer, String>(); //异常编码对应的处理方式
        private static Map<Integer, ErrProcessEnum> errProcessEnumMap = new HashMap<Integer, ErrProcessEnum>(); //异常编码对应的处理方式

        static {
            for (ErrProcessEnum enumItem : EnumSet.allOf(ErrProcessEnum.class)) {
                processBtnMap.put(enumItem.errCode, enumItem.toJson());
                errProcessEnumMap.put(enumItem.errCode, enumItem);
            }
        }

        private static String getProcessBtn(int errCode) {
            return processBtnMap.get(errCode);
        }

        private static boolean isAuthority(int errCode, int disposeType, Integer source) {
            ErrProcessEnum errProcessEnum = errProcessEnumMap.get(errCode);
            ErrDisposeEnum errDisposeEnum = ErrDisposeEnum.getErrDisposeEnumByType(disposeType);
            if (errProcessEnum == null || errDisposeEnum == null) {
                return false;
            } else if (source != null) {
                switch (source) {
                    case ADMIN:
                        if (!errProcessEnum.admin) {
                            return false;
                        }
                        break;
                    case PARTNER:
                        if (!errProcessEnum.partner) {
                            return false;
                        }
                        break;
                    case SELLER:
                        if (!errProcessEnum.seller) {
                            return false;
                        }
                        break;
                }
            }
            return errProcessEnum.errDisposes.contains(errDisposeEnum);
        }
    }

    /**
     * 异常处理方式
     */
    private enum ErrOperateEnum {
        MANUAL("人工处理"),
        AWAITSYS("等待系统处理"),;
        private String desc;

        ErrOperateEnum(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 异常恢复操作枚举
     */
    public enum ErrDisposeEnum {

        OCCUPYSOSTOCK(1, "预占库存并下发", Boolean.TRUE, "温馨提示：该操作将重新预占，并且将订单下发至仓系统。"),
        RESETTASK(2, "重新执行异常节点", Boolean.TRUE, "温馨提示：该操作将尝试重新执行异常节点。"),
        RESETWMS(3, "下发仓储系统", Boolean.TRUE, "温馨提示：该操作将重新下发到仓储系统。"),
        EDITSO(4, "修改订单信息", Boolean.FALSE, "温馨提示：该操作将修改订单信息。"),
        EDITBDADDR(5, "修改运单地址", Boolean.TRUE, "温馨提示：该操作将修改运单地址。"),
        EDITSHIP(6, "修改承运商", Boolean.TRUE, "温馨提示：该操作将修改承运商。"),
        REDEVELIVER(7, "协商再投", Boolean.FALSE, "温馨提示：该操作将处理协商再投。"),
        RESETTASKECLP(8, "重新下发C L P", Boolean.FALSE, "温馨提示：该操作将重新下发到C L P系统。"),
        REGETZYPRINT(9, "重新获取众打印信息", Boolean.FALSE, "温馨提示：该操作将重新获取众打印信息。"),
        ;
        ;

        private int type; //处理类型编码
        private String name; //异常处理名称
        private boolean batch; //是否支持批量处理
        private String tipMsg; //处理提示语


        ErrDisposeEnum(int type, String name, boolean batch, String tipMsg) {
            this.type = type;
            this.name = name;
            this.batch = batch;
            this.tipMsg = tipMsg;
        }

        private static Map<Integer, ErrDisposeEnum> errDisposeMap = new HashMap<Integer, ErrDisposeEnum>(); //异常编码对应的处理方式

        static {
            for (ErrDisposeEnum enumItem : EnumSet.allOf(ErrDisposeEnum.class)) {
                errDisposeMap.put(enumItem.type, enumItem);
            }
        }

        public int getType() {
            return type;
        }

        public static ErrDisposeEnum getErrDisposeEnumByType(Integer type) {
            return errDisposeMap.get(type);
        }

        public String toJson() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("name", name);
            jsonObject.put("batch", batch);
            jsonObject.put("tipMsg", tipMsg);
            return jsonObject.toString();
        }
    }
}
