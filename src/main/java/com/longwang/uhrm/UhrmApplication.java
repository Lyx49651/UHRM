package com.longwang.uhrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.longwang.uhrm.Entity.mapper")
@SpringBootApplication//排除自动配置
public class UhrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(UhrmApplication.class, args);
    }

}
