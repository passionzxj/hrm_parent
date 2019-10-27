package com.zhang.hrm.service.impl;

import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.service.ImageValidateCodeService;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.VerifyCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageValidateCodeServiceImpl implements ImageValidateCodeService {

    @Autowired
    private RedisClient redisClient;

    /**
     * 以前台传过来的UUID作为key,前台把uuid存入到localStroyge中
     * 能保证只要是浏览器不变那么uuid就不会变,redis中的key就不会变,不会导致redis中存了大量的数据
     * @param uuid
     * @return
     */
    @Override
    public String getCode(String uuid) {
        //随机生成6位验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(6).toLowerCase();
        //把数据存入到redis
        redisClient.set(uuid,verifyCode,60);
        //输出到图片中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            VerifyCodeUtils.outputImage(100,30,outputStream,verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());
    }

    /**
     * 做图形验证码的验证
     * @param graphValidateCode
     * @return
     */
    @Override
    public AjaxResult validateCode(String codeUuid,String graphValidateCode) {
        String code = redisClient.get("uuid");
        if (StringUtils.isBlank(code)){
            return AjaxResult.me().setSuccess(false).setMessage("请输入验证码");
        }
        if (!code.equals(codeUuid)){
            return AjaxResult.me().setSuccess(false).setMessage("验证码输入错误");
        }
        return AjaxResult.me();
    }
}
