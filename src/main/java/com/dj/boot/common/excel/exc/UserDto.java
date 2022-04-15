package com.dj.boot.common.excel.exc;


import com.dj.boot.common.csv.annotation.CSVColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Excel(name = "ID")
    @CSVColumn(header = "id",order = 1)
    private String id;
    @Excel(name = "用户名")
    @CSVColumn(header = "userName",order = 2)
    private String username;
    @Excel(name = "密码")
    @CSVColumn(header = "password",order = 3)
    private String password;

}
