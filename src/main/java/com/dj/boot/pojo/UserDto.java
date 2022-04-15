package com.dj.boot.pojo;

import com.dj.boot.common.csv.annotation.CSVColumn;
import com.dj.boot.common.util.StringUtils;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mapstruct.Qualifier;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserDto  {

    private Integer id;
    private Integer sex;
    private Integer userName;
    private LocalDateTime createTime;
    private String salt;
    private String password;
    private String createTimeStr;
    private Date created;
    private String token;
    private String email;
    private String pin;
    private String eoType;
    private String createTimeStart;
    private String createTimeEnd;
    private Byte isMultiTenant;
    private String deptIds;//"1,2,3,4"



    private List<String> passwordList;
    private List<String> userNameList;//前端传多个单号 以英文,分割的字符串  后台接收参数时 直接映射到数组/集合


    private List<Integer> ids;


    private boolean returnFlag;


    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface StringToInteger {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface IntegerToString{
    }


    @IntegerToString
    public static String IntegerToString(Integer i)  {
        if (null == i) {
            return i.toString();
        } else {
            return null;
        }
    }

    @StringToInteger
    public static Integer StringToInteger(String s)  {
        if (!StringUtils.isBlank(s)) {
            return Integer.valueOf(s);
        } else {
            return null;
        }
    }



}
