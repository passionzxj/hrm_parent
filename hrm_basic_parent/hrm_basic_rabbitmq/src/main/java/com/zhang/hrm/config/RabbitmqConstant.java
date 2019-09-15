package com.zhang.hrm.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConstant {
    public static final String EXCHANGE_DIRECT_INFORM = "exchange_direct_inform";
    public static final String HRM_COMMON_QUEUE = "hrm_common_queue";
}