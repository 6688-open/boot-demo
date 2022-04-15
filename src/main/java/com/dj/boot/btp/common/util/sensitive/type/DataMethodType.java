package com.dj.boot.btp.common.util.sensitive.type;

/** 敏感数据类型 */
public enum DataMethodType {

    /** 过滤列表 list 使用*号代替 */
    PERCOLATE_LIST,
    /** 过滤详情 view  点击查看 */
    PERCOLATE_VIEW,
    /** 过滤导出 export 使用*号代替 */
    PERCOLATE_EXPORT,
    /** 过滤详情列表 views 点击查看 */
    PERCOLATE_VIEWS,

    ;

}
