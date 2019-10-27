package com.zhang.hrm.service;

import com.zhang.hrm.util.AjaxResult;

public interface ImageValidateCodeService {
    String getCode(String uuid);

    AjaxResult validateCode(String codeUuid,String graphValidateCode);
}
