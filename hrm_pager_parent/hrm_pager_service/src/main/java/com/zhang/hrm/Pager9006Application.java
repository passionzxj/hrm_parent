package com.zhang.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zhang.hrm.mapper")
public class Pager9006Application {
    public static void main(String[] args) {
        SpringApplication.run(Pager9006Application.class);
    }
}
