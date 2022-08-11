package com.dj.boot.common.util.httpclient.gw.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 描述信息
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-08-11-09-44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Integer id;
    private Integer sex;
    private String userName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private String salt;
    private String password;
    private BigDecimal userNum;
    private BigDecimal totalNum;
    private Byte eoType;
    private String eoMark;
}
