package com.dj.boot.common.excel.exc;


import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "序号",index = 0)
    private String id;
    @Excel(name = "用户名")
    @CSVColumn(header = "userName",order = 2)
    @ExcelProperty(value = "用户名",index = 1)
    private String username;
    @Excel(name = "密码")
    @CSVColumn(header = "password",order = 3)
    @ExcelProperty(value = "密码",index = 2)
    private String password;

}
