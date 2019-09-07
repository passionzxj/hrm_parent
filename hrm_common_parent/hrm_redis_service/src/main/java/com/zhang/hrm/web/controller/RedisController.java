package com.zhang.hrm.web.controller;

import com.zhang.hrm.util.RedisUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class RedisController {

    @PostMapping
    public void set(@RequestParam("key") String key,@RequestParam("value") String value){
        //判断传入的value如果是[],则设置过期时间
        if (value.equals("[]"))
            RedisUtils.INSTANCE.getSource().setex(key,5*60,value);
        else
        RedisUtils.INSTANCE.set(key,value);
    }

    @GetMapping
    public String get(@RequestParam("key") String key){
        return RedisUtils.INSTANCE.get(key);
    }
}
