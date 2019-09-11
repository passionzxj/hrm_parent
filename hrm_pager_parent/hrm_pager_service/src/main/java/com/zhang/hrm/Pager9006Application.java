package com.zhang.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zhang.hrm.mapper")
@EnableFeignClients
public class Pager9006Application {
    public static void main(String[] args) {
        SpringApplication.run(Pager9006Application.class);
    }
}
