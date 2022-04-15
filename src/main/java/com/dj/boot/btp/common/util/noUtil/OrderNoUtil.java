package com.dj.boot.btp.common.util.noUtil;

/**
 * 公共方法:单号生成器
 */
public class OrderNoUtil {
    /**
     * 单号生成器
     *
     * @return
     */
    public static String generateNo(final BizTypeEnum bizType, final Long id) {
        String no;
        if (null == bizType || null == id) {
            return null;
        }
        switch (bizType) {
            case CWO_SERVICE:
                no = Constant.BizType.CWO_SERVICE+ String.format("%013d", id);
                break;
            case CPO_SERVICE:
                no = Constant.BizType.CPO_SERVICE+ String.format("%08d", id);
                break;
            case CBU_SERVICE:
                no = Constant.BizType.CBU_SERVICE+ String.format("%013d", id);
                break;
            case CSL_SERVICE:
                no = Constant.BizType.CSL_SERVICE+ String.format("%013d", id);
                break;
            default:
                throw new RuntimeException("未提供有效的业务类型，不能生成平台编号");
        }
        return no;
    }

    /**
     * 编号转化为ID
     *
     * @param no       编号
     * @param noPrefix 编号前缀
     * @return 返回ID
     */
    public static long reverseNo2Id(String no, String noPrefix) {
        if (null == no || no.equals("")) {
            return -1L;
        }
        if (no.startsWith(noPrefix)) {
            try {
                return Long.valueOf(no.substring(noPrefix.length()));
            }catch (Exception e){
                return -1L;
            }
        } else {
            try {
                return Long.valueOf(no);
            } catch (Exception e) {
                return -1L;
            }
        }
    }


    public static void main(String[] args) {
        Long id = 10000000000002L;

        //String no = OrderNoUtil.generateNo(BizTypeEnum.CWO_SERVICE, id);
        String no = OrderNoUtil.generateNo(BizTypeEnum.CPO_SERVICE, id);

    }
}