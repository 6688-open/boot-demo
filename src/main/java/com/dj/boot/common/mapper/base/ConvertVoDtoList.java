package com.dj.boot.common.mapper.base;

import java.util.List;

/**
 * @ClassName ConvertVoDto
 * @Description TODO
 * @Author wj
 * @Date 2020/05/22 19:04
 * @Version 1.0
 **/
public interface ConvertVoDtoList<VO, DTO> extends ConvertVoDto<VO, DTO>{

    List<VO> dtoToVo(List<DTO> var1);

    List<DTO> voToDto(List<VO> var1);
}
