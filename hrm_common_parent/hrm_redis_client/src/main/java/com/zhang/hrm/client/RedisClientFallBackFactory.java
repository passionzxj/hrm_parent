package com.zhang.hrm.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisClientFallBackFactory implements FallbackFactory<RedisClient> {
    @Override
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            @Override
            public void set(String key, String value) {

            }

            @Override
            public void set(String key, String value, int seconds) {

            }

            @Override
            public String get(String key) {
                return null;
            }
        };
    }
}
