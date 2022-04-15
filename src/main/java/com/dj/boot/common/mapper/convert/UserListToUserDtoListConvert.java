package com.dj.boot.common.mapper.convert;

import com.dj.boot.common.excel.exc.UserDto;
import com.dj.boot.common.mapper.base.ConvertVoDtoList;
import com.dj.boot.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper
public interface UserListToUserDtoListConvert extends ConvertVoDtoList<User, UserDto> {

    UserListToUserDtoListConvert INSTANCE = Mappers.getMapper(UserListToUserDtoListConvert.class);


    @Override
    @Mapping(source = "username", target = "userName")
    User dtoToVo(UserDto userDto);

    @Override
    @Mapping(source = "userName", target = "username")
    UserDto voToDto(User user);
}
