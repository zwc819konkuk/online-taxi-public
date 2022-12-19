package com.zwc.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://123.57.79.37:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder -> {
                    builder.author("zwc").fileOverride().outputDir("E:\\javaprojects\\online-taxi-public\\service-driver-user\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.zwc.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                           "E:\\javaprojects\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\zwc\\servicedriveruser\\mapper" ));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("car");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
