package com.dj.boot.configuration.codegenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @author wangjia@fescotech.com
 * @date 2022-05-06 11:42
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/boot-demo/src/main";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/1804?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "123456")
                // 全局配置
                .globalConfig((scanner, builder) -> {
                    builder.author(scanner.apply("请输入作者名:")) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(path + "/java"); // 指定输出目录
                })

                // 包配置
                .packageConfig((scanner, builder) -> {
                    builder.parent("com.dj.boot") // 设置父包名
                            .moduleName(scanner.apply("请输入模块名:")) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, path + "/resources/mapper")); // 设置mapperXml生成路径
                })

                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名:")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
                .execute();
    }
    private static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
