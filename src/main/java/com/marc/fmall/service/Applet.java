package com.marc.fmall.service;

import com.marc.fmall.common.CommonResult;
import com.marc.fmall.dto.LoginParam;
import com.marc.fmall.service.impl.SensitiveDataParam;

public interface Applet {

    CommonResult loginRecore(LoginParam loginParam);
    CommonResult dataDecrypt(SensitiveDataParam sensitiveDataParam);
}
