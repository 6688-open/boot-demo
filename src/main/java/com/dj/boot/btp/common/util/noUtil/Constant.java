package com.dj.boot.btp.common.util.noUtil;

public interface Constant {

    String SUCCESS_MESSAGE = "操作成功！";
    String ERROR_MESSAGE = "操作失败！";
    int SUCCESS_CODE = 1;
    int ERROR_CODE = 0;
    int WAIT = 2;
    String PAGE = "page";
    String PAGE_STARTINDEX = "iDisplayStart";
    String PAGE_SIZE = "iDisplayLength";
    String PAGE_SORTCol = "iSortCol_0";
    String PAGE_SSORTDIR = "sSortDir_0";
    String PAGE_PROP_PRE = "mDataProp_";

    /**
     * @author 业务类型
     */
    public interface BizType {
        // 事件单前缀
        public static final String CWO_SERVICE = "CWO";
        //赔付单前缀
        public static final String CPO_SERVICE = "CPO";
        // 事业部前缀
        public static final String CBU_SERVICE = "CBU";
        // 订单
        public static final String CSL_SERVICE = "CSL";
    }


}
