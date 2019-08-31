package com.zhang.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy//启用网关
public class ZuulGateway9527Application {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGateway9527Application.class, args);
    }
}
