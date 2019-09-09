package com.zhang.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RabbitMQ9007Application {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQ9007Application.class);
    }
}
