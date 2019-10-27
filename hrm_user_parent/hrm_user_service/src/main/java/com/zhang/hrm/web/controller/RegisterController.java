package com.zhang.hrm.web.controller;


import com.zhang.hrm.service.ISsoService;
import com.zhang.hrm.service.ImageValidateCodeService;
import com.zhang.hrm.service.ValidatePhoneCode;
import com.zhang.hrm.util.AjaxResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ImageValidateCodeService imageValidateCodeService;
    @Autowired
    private ISsoService ssoService;
    @Autowired
    private ValidatePhoneCode phoneCode;


    @PostMapping("/sendMsg")
    public AjaxResult register(@RequestBody Map<String,Object> map){
        String codeUuid = (String) map.get("codeUuid");
        String mobile = (String) map.get("mobile");
        String graphValidateCode = (String) map.get("graphValidateCode");

        //全局验证
        if (StringUtils.isBlank(codeUuid)||StringUtils.isBlank(mobile)||StringUtils.isBlank(graphValidateCode)){
            return AjaxResult.me().setSuccess(false).setMessage("系统错误!请完整输入");
        }
        //验证图形验证码
        AjaxResult ajaxResult = imageValidateCodeService.validateCode(codeUuid,graphValidateCode);
        if (!ajaxResult.isSuccess())
            return ajaxResult;
        //验证用户信息
        ajaxResult = ssoService.validateSso(mobile);
        if (!ajaxResult.isSuccess())
            return ajaxResult;
        //发送手机验证码
        return phoneCode.validatePhoneCode(mobile);
    }
}
