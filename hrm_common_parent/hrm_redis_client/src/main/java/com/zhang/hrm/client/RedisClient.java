package com.zhang.hrm.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "HRM-REDIS", configuration = FeignClientsConfiguration.class,
        fallbackFactory = RedisClientFallBackFactory.class)
@RequestMapping("/cache")
public interface RedisClient {

    @PostMapping
    void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @PostMapping("/randomCode")
    void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("seconds") int seconds);

    @GetMapping
    String get(@RequestParam("key") String key);
}
