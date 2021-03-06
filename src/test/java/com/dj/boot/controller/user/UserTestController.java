package com.dj.boot.controller.user;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.util.Validators;
import com.dj.boot.common.util.security.Sha256Utils;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.service.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName UserTestController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 17:33
 * @Version 1.0
 **/

public class UserTestController extends BootDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private Validators validators;



    @Override
    public void run() throws Exception {

        User user = new User();
        user.setEmail("18351867657@163.com");
        validators.validate(user);

        List<User> userList = userService.findUserListByCondition(new UserDto());
        String str = resolverFlowConditions(userList);
        System.out.println(str);

        UserDto userDto = new UserDto();

        List<String> passwordList = Lists.newArrayList();
        passwordList.add("123-01");
        passwordList.add("123-02");

        List<String> userNameList = Lists.newArrayList();
        userNameList.add("wj-01");
        userNameList.add("wj-02");

        userDto.setPasswordList(passwordList);
        userDto.setUserNameList(userNameList);
        List<User> userListByCondition = userService.findUserListByCondition(userDto);

        System.out.println(userListByCondition.size());


        String idBySnowFlake = userService.getIDBySnowFlake();

        Long count = userService.getCount();
        System.out.println(count);
        List<User> users = userService.list();
        List<Integer> list = users.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(111);
    }

    /**
     * ????????????
     */
    @Test
    public void test(){
        //SHA256?????????????????????????????????256???
        String pwd = "123456";
        String pwd1 = "123456";
        String s = Sha256Utils.encipherToHex(pwd);
        String s1 = Sha256Utils.encipherToHex(pwd1);
        if (s.equals(s1)) {
            System.out.println(s);
            System.out.println(s1);
        }

        byte[] bytes = pwd.getBytes();
        byte[] bytes1 = Sha256Utils.encipher(bytes);
    }


    @Test
    public void testStringBuffer(){
        String token = "token";
        String timestamp = "timestamp";
        String schoolId = "schoolId";
        String companyId = "companyId";
        String mobile = "mobile";
        String loginHost = "http://111.com/interfaces/login";
        String extendUrl = "&url=/sysConfig/list";

        StringBuffer sb = new StringBuffer(loginHost).append("?")
                .append("token=").append(token).append("&")
                .append("mobile=").append(mobile).append("&")
                .append("timestamp=").append(timestamp).append("&")
                .append("schoolId=").append(schoolId).append("&")
                .append("companyId=").append(companyId)
                .append(extendUrl);
    }





    private String resolverFlowConditions(List<User> userList) {
        StringBuilder msg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(userList)) {
            msg.append("????????????:[");
            Integer no = 1;
            for (User user : userList) {
                String sb = "zs,lisi,";
                msg.append(no).append(".{")
                        .append("(").append(user.getId()).append(")-")
                        .append("(").append(user.getUserName()).append(")-")
                        .append("(").append(sb).append(")-")
                        .append("(").append(user.getPassword()).append(")} ");
                no++;
            }
            msg.append("]");
        }
        return msg.toString();
    }


    public static void main(String[] args) {
        String nameAttr = "aaa,bbb,ccc,ddd";
        String[] split = nameAttr.split(",");
        List<String> strings = Arrays.asList(split);
        for (String string : strings) {
            User user = null;
            try {
                user = validateData(string);
            } catch (Exception e) {
                System.out.println("??????"+string);
            }

            System.out.println("string"+string);

        }


    }

    private static User validateData(String string) throws Exception {
        if (string.equals("ccc")) {
            throw new Exception("????????????");
        }
        return new User();
    };

}
