package com.longwang.uhrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.longwang.uhrm.mapper")
@SpringBootApplication//排除自动配置
public class UhrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(UhrmApplication.class, args);
    }

}
