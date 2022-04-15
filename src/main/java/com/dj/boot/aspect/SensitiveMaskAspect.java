package com.dj.boot.aspect;

import com.dj.boot.annotation.sensitive.SensitiveMask;
import com.dj.boot.btp.common.util.sensitive.sensitiveutil.SensitiveMaskUtil;
import com.dj.boot.btp.common.util.sensitive.sensitiveutil.SensitiveMaskViewUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * AnnotationAwareAspectJAutoProxyCreator
 */
@Aspect
@Component
public class SensitiveMaskAspect {

    private static final Logger log = LoggerFactory.getLogger(SensitiveMaskAspect.class);

    @Resource
    public SensitiveMaskUtil sensitiveMaskUtil;
    @Resource
    public SensitiveMaskViewUtil sensitiveMaskViewUtil;


    /**  拦截 所有使用sensitiveMask的方法  **/
    // successful
    @AfterReturning(value="@annotation(com.dj.boot.annotation.sensitive.SensitiveMask) && @annotation(sensitiveMask)" ,returning="retVal")
    public void mask4(SensitiveMask sensitiveMask, Object retVal) {
        switch (sensitiveMask.value()) {
            case PERCOLATE_LIST:
                sensitiveMaskUtil.maskByAnnotation((List)retVal);
                break;
            case PERCOLATE_EXPORT:
                //List<T> retVal = new ArrayList<>();
                //sensitiveMaskUtil.maskByAnnotatio((List)retVal);
                break;
            case PERCOLATE_VIEW:
                sensitiveMaskViewUtil.maskByAnnotation(retVal);
                break;
            case PERCOLATE_VIEWS:
                //sensitiveMaskViewUtil.maskByAnnotatio4Views(((Page) retVal).getAaData());
                break;
            default:
                log.error("脱敏处理的数据有误！");
        }
    }



}

