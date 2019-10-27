package com.zhang.hrm.service.impl;

import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.service.ValidatePhoneCode;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.StrUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ValidatePhoneCodeImpl implements ValidatePhoneCode {

    @Autowired
    private RedisClient redisClient;

//    @Autowired
//    private SmsHelper smsHelper;

    @Override
    public AjaxResult validatePhoneCode(String mobile) {

        //默认先创建一个验证码
        String newCode = StrUtils.getRandomString(6);
        //从redis中拿短信验证码
        String key = "smsCode-" + mobile;
        String redisCode = redisClient.get(key);
        //如果redis中有验证码
        if (StringUtils.isNotBlank(redisCode)) {
            //intervalTime:间隔时间
            String createTime = redisCode.split(":")[1];
            //如果redis中的验证码合法就使用原来的验证码
            newCode = redisCode.split(":")[0];
            Long intervalTime = new Date().getTime() - Long.valueOf(createTime);
            if (intervalTime < 1 * 60 * 1000) {
                return AjaxResult.me().setSuccess(false).setMessage("请不要频繁操作!");
            }
        }
        //如果redis中没有验证码
        //smsHelper.sendSms("1882346334","1",new String[]{"2222","1"});
        System.out.println("已经向手机号为:" + mobile + "发送了" + newCode + "验证码");
        return AjaxResult.me();
    }
}
