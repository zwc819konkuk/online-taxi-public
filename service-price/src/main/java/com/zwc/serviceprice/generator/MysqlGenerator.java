package com.zwc.serviceprice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder -> {
                    builder.author("zwc").fileOverride().outputDir("/Users/venn/online-taxi-public/service-price/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.zwc.serviceprice").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                           "/Users/venn/online-taxi-public/service-price/src/main/java/com/zwc/serviceprice/mapper" ));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
