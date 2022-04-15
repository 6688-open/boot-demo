package com.dj.boot.service.master.covertor;

import com.dj.boot.pojo.master.Dept;
import com.dj.boot.pojo.master.DeptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeptConverter {
    DeptConverter INS = Mappers.getMapper(DeptConverter.class);
    DeptEntity toDomain(Dept dept);
}
