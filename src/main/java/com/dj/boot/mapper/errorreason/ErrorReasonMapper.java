package com.dj.boot.mapper.errorreason;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.pojo.errorreason.ErrorReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BaseDataMapper继承基类
 */
/*@Mapper
@Repository*/
public interface ErrorReasonMapper extends BaseMapper<ErrorReason> {


    /**
     * 获取异常报备实体
     * @param id
     * @return the ErrorReason
     */
    public ErrorReason getErrorReason(Long id);

    /**
     * @Description: 按条件获取事件单实体列表
     * @param errorReason
     * @return
     */
     List<ErrorReason> findErrorReasonListByCondition(@Param("po") ErrorReason errorReason);

    /**
     * 插入异常报备实体
     * @param errorReason
     */
     void insertErrorReason(ErrorReason errorReason);

    /**
     * 更新异常报备实体
     * @param errorReason
     */
     void updateErrorReason(ErrorReason errorReason);

    /**
     * 删除事件单实体
     * @param id
     */
     void deleteErrorReason(Long id);
}