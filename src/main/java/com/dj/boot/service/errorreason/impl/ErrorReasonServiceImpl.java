package com.dj.boot.service.errorreason.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.controller.base.BaseDataController;
import com.dj.boot.mapper.base.BaseDataMapper;
import com.dj.boot.mapper.errorreason.ErrorReasonMapper;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.pojo.errorreason.ErrorReason;
import com.dj.boot.service.errorreason.ErrorReasonService;
import com.dj.boot.service.pk.SequenceService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ErrorReasonServiceImpl extends ServiceImpl<ErrorReasonMapper, ErrorReason> implements ErrorReasonService {
    private static final Logger logger = LoggerFactory.getLogger(ErrorReasonServiceImpl.class);
    @Autowired
    private ErrorReasonMapper errorReasonMapper;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public ErrorReason getErrorReason(Long id) {
        return errorReasonMapper.getErrorReason(id);
    }

    @Override
    public List<ErrorReason> findErrorReasonListByCondition(ErrorReason condition) {
        return errorReasonMapper.findErrorReasonListByCondition(condition);
    }

    @Override
    public void insertErrorReason(ErrorReason errorReason) {
        try {
            List<ErrorReason> errorReasonList = errorReasonMapper.findErrorReasonListByCondition(errorReason);
            if (CollectionUtils.isNotEmpty(errorReasonList)) {
               throw new RuntimeException("原因名称已存在");
            }
            long id = sequenceService.insertAndGetSequence("error_reason");
            errorReason.setId(id);
            errorReasonMapper.insertErrorReason(errorReason);
        }catch (RuntimeException e){
            logger.error("调用新增服务异常",e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateErrorReason(ErrorReason errorReason) {
        try {
            ErrorReason dbErrorReason = errorReasonMapper.getErrorReason(errorReason.getId());
            //如果原因名称没有变化则无需校验
            if(errorReason.getReportName() != dbErrorReason.getReportName()){
                ErrorReason condition = new ErrorReason();
                if(errorReason.getParentCode() != null){
                    condition.setParentCode(errorReason.getParentCode());
                }
                if(errorReason.getErrorLevel() != null){
                    condition.setErrorLevel(errorReason.getErrorLevel());
                }
                condition.setReportName(errorReason.getReportName());
                List<ErrorReason> errorReasonList = errorReasonMapper.findErrorReasonListByCondition(condition);
                if(CollectionUtils.isNotEmpty(errorReasonList)){
                    if(errorReasonList.size()>1||errorReasonList.get(0).getId().longValue() != errorReason.getId()){
                        throw new RuntimeException("原因名称已存在");
                    }
                }
            }
            errorReasonMapper.updateErrorReason(errorReason);
        }catch (RuntimeException e){
            logger.error("updateErrorDeviceReason error",e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteErrorReason(Long id) {
        errorReasonMapper.deleteErrorReason(id);
    }
}
