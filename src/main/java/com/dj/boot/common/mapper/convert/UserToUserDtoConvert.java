package com.dj.boot.common.mapper.convert;

import com.dj.boot.common.excel.exc.UserDto;
import com.dj.boot.common.mapper.base.ConvertVoDto;
import com.dj.boot.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface UserToUserDtoConvert extends ConvertVoDto<User, UserDto> {

    UserToUserDtoConvert INSTANCE = Mappers.getMapper( UserToUserDtoConvert.class);



    @Override
    @Mapping(source = "username", target = "userName")
    User dtoToVo(UserDto userDto);

    @Override
    @Mapping(source = "userName", target = "username")
    UserDto voToDto(User user);

    List<UserDto> userToDtoList(List<User> userList);

    List<User> dtoToUserList(List<UserDto> userDtoList);
}
