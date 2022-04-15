package com.dj.boot.common.mapper;

import com.dj.boot.common.excel.exc.UserDto;
import com.dj.boot.common.mapper.convert.*;
import com.dj.boot.pojo.User;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        //voToDtoTest();
        dtoToVoTest();
    }

    private static void voToDtoTest() {
        DogAndCarVo dogAndCarVo = new DogAndCarVo();
        List<DogVo> dogVoList = Lists.newArrayList();


        CarVo carVo = new CarVo();
        carVo.setUsername("wj");
        carVo.setCreateTime(new Date());
        carVo.setFlag(true);
        carVo.setUserPassVo("UserPassVo");
        carVo.setUserSexVo("userSexVo");
        carVo.setReceiptsPerformTypeName("调拨入库");


        DogVo dogVo = new DogVo();
        dogVo.setDogName("旺旺");
        dogVo.setDogSex(1);
        DogVo dogVo1 = new DogVo();
        dogVo1.setDogName("狗子");
        dogVo1.setDogSex(2);

        dogVoList.add(dogVo);
        dogVoList.add(dogVo1);


        dogAndCarVo.setCarVo(carVo);
        dogAndCarVo.setDogVoList(dogVoList);


        CarDto carDto = CarConvert.INSTANCE.voToDto(carVo);
        DogDto dogDto = DogConvert.INSTANCE.voToDto(dogVo);
        DogAndCarDto dogAndCarDto = DogAndCarConvert.INSTANCE.voToDto(dogAndCarVo);
        System.out.println(carDto);
        System.out.println(dogDto);
        System.out.println(dogAndCarDto);
    }

    private static void dtoToVoTest() {
        DogAndCarDto dogAndCarDto = new DogAndCarDto();
        List<DogDto> dogDtoList = Lists.newArrayList();


        CarDto carDto = new CarDto();
        carDto.setUsername("wj");
        carDto.setCreateTime("2019-10-13");
        carDto.setFlag(true);
        carDto.setUserPassDto("UserPassDto");
        carDto.setUserSexDto("1");
        carDto.setReceiptsPerformTypeCode(1);


        DogDto dogDto = new DogDto();
        dogDto.setDogName("旺旺");
        dogDto.setDogSex(1);
        DogDto dogDto1 = new DogDto();
        dogDto1.setDogName("狗子");
        dogDto1.setDogSex(2);

        dogDtoList.add(dogDto);
        dogDtoList.add(dogDto1);


        dogAndCarDto.setCarDto(carDto);
        dogAndCarDto.setDogDtoList(dogDtoList);


        CarVo carVo = CarConvert.INSTANCE.dtoToVo(carDto);
        DogVo dogVo = DogConvert.INSTANCE.dtoToVo(dogDto);
        DogAndCarVo dogAndCarVo = DogAndCarConvert.INSTANCE.dtoToVo(dogAndCarDto);
        System.out.println(carVo);
        System.out.println(dogVo);
        System.out.println(dogAndCarVo);
    }

    /**
     * 转化 List<> 集合时必须有 实体转化，因为在实现中，List 转换是 for循环调用 实体转化的。
     * 所以当属性名不对应时，应该在 实体转化进行 @Mappings 的属性名映射配置，然后list的转换也会继承这和属性的映射。
     */
    @org.junit.Test
    public void voListToDtoListTest() {
        List<CarVo> carVoList = Lists.newArrayList();

        CarVo carVo = new CarVo();
        carVo.setUsername("wj");
        carVo.setCreateTime(new Date());
        carVo.setFlag(true);
        carVo.setUserPassVo("UserPassVo");
        carVo.setUserSexVo("userSexVo");
        carVo.setReceiptsPerformTypeName("调拨入库");

        CarVo carVo1 = new CarVo();
        carVo1.setUsername("wj");
        carVo1.setCreateTime(new Date());
        carVo1.setFlag(true);
        carVo1.setUserPassVo("UserPassVo");
        carVo1.setUserSexVo("userSexVo");
        carVo1.setReceiptsPerformTypeName("调拨入库");

        carVoList.add(carVo);
        carVoList.add(carVo1);

        CarDto carDto = CarConvert.INSTANCE.voToDto(carVo);
        List<CarDto> carDtoList = CarConvert.INSTANCE.voListToDtoList(carVoList);

    }

    @org.junit.Test
    public void dtoListToVoListTest() {
        List<CarDto> carDtoList = Lists.newArrayList();

        CarDto carDto = new CarDto();
        carDto.setUsername("wj");
        carDto.setCreateTime("2019-10-13");
        carDto.setFlag(true);
        carDto.setUserPassDto("UserPassDto");
        carDto.setUserSexDto("userSexDto");
        carDto.setReceiptsPerformTypeCode(1);

        CarDto carDto1 = new CarDto();
        carDto1.setUsername("wj");
        carDto1.setCreateTime("2019-10-13");
        carDto1.setFlag(true);
        carDto1.setUserPassDto("UserPassDto");
        carDto1.setUserSexDto("userSexDto");
        carDto1.setReceiptsPerformTypeCode(1);

        carDtoList.add(carDto);
        carDtoList.add(carDto1);

        CarVo carVo = CarConvert.INSTANCE.dtoToVo(carDto);
        List<CarVo> carVoList = CarConvert.INSTANCE.dtoListToVoList(carDtoList);

    }



    @org.junit.Test
    public void dtoListToVoListTest01() {
        List<UserDto>  userDtos = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setUsername("111111111111");
        userDto.setPassword("111111111111");
        userDto.setId("1");
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("22222222222");
        userDto1.setPassword("22222222222222");
        userDto1.setId("2");
        userDtos.add(userDto);
        userDtos.add(userDto1);
        List<User> userList = UserListToUserDtoListConvert.INSTANCE.dtoToVo(userDtos);
        User user = UserListToUserDtoListConvert.INSTANCE.dtoToVo(userDto);

        List<User> userList1 = UserToUserDtoConvert.INSTANCE.dtoToUserList(userDtos);
        User user1 = UserToUserDtoConvert.INSTANCE.dtoToVo(userDto);
        System.out.println(111);

    }


}
