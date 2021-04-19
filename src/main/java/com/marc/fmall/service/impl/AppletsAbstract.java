package com.marc.fmall.service.impl;

import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.ResultCode;
import com.marc.fmall.dto.LoginParam;
import com.marc.fmall.service.Applet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 各类小程序平台登录，敏感数据解密
 */
@Slf4j
public  abstract class AppletsAbstract implements Applet {

    public static ConcurrentHashMap<String,AppletsAbstract> appletMap= new ConcurrentHashMap<String, AppletsAbstract>();

    @PostConstruct
    public void init(){
        appletMap.put(getType(),this);
    }

    /**
     * 各小程序平台登录接口
     * @param loginParam
     * @return
     */
    @Override
    public CommonResult loginRecore(LoginParam loginParam) {
        log.info("begin AppletAbstract.loginRecore:"+loginParam);
        //校验参数
        validteLoginParam(loginParam);
        //调用Api接口
        Object result = doProcess(loginParam);
        Map<String, String> resultMap = handleResult(result);
        if(Optional.ofNullable(resultMap.get("errcode")).isPresent()){
            return  CommonResult.failed(Integer.parseInt(resultMap.get("errcode")),resultMap.get("errmsg"));
        }
        if(Optional.ofNullable(resultMap.get("errno")).isPresent()){
            return  CommonResult.failed(Integer.parseInt(resultMap.get("errno")),resultMap.get("error"));
        }
        if(Optional.ofNullable(resultMap.get("subMsg")).isPresent()){
            return CommonResult.failed(Integer.parseInt(resultMap.get("code")),resultMap.get("subMsg"));
        }
        return CommonResult.success(ResultCode.SUCCESS.getCode(),"操作成功",resultMap);
    }

    /**
     * 各小程序平台敏感数据解密
     * @return
     */
    @Override
    public CommonResult dataDecrypt(@RequestBody SensitiveDataParam sensitiveDataParam) {
        log.info("begin AppletAbstract.dataDecrypt:");
        //参数校验
        validteSensitiveData(sensitiveDataParam);
        //进行解密
        String str = decryptProcess(sensitiveDataParam);
        //返回参数处理
        Map<String, String> resultMap = handleDecryptResult(str);
        if(Optional.ofNullable(resultMap.get("code")).isPresent()){
            return CommonResult.failed(Integer.parseInt(resultMap.get("code")),resultMap.get("msg"));
        }
        return CommonResult.success(ResultCode.SUCCESS.getCode(),"解密成功",resultMap);
    }

    public abstract String getType();
    public abstract void validteLoginParam(LoginParam loginParam);
    public abstract void validteSensitiveData(SensitiveDataParam sensitiveDataParam);
    public abstract Object doProcess(LoginParam loginParam);
    public abstract String decryptProcess(SensitiveDataParam sensitiveDataParam);
    public abstract Map<String,String> handleResult(Object obj);
    public abstract Map<String,String> handleDecryptResult(String str);
}
