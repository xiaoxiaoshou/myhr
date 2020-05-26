package com.dpf.myvhr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.dpf.myvhr.mapper")
@EnableScheduling
public class MyvhrApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyvhrApplication.class, args);
    }

}
