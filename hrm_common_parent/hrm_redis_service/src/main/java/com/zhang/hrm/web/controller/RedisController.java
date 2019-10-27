package com.zhang.hrm.web.controller;

import com.zhang.hrm.util.RedisUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/cache")
public class RedisController {

    @PostMapping
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {
        //判断传入的value如果是[],则设置过期时间
        if (value.equals("[]"))
            RedisUtils.INSTANCE.getSource().setex(key, 5 * 60, value);
        else
            RedisUtils.INSTANCE.set(key, value);
    }

    //设置有过期时间的redis
    @PostMapping("/randomCode")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("seconds") int seconds) {
        Jedis jedis = RedisUtils.INSTANCE.getSource();
        jedis.setex(key, seconds, value);
        jedis.close();
    }

    @GetMapping
    public String get(@RequestParam("key") String key) {
        return RedisUtils.INSTANCE.get(key);
    }
}
