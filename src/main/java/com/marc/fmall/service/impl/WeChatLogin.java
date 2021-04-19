package com.marc.fmall.service.impl;



import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.marc.fmall.common.AppletTypeEnum;
import com.marc.fmall.common.exception.ValidDataException;
import com.marc.fmall.dto.LoginParam;
import com.marc.fmall.entity.MpInfo;
import com.marc.fmall.service.IMpInfoService;
import com.marc.fmall.util.AESUtil;
import com.marc.fmall.util.HttpClientUtils;
import com.marc.fmall.util.WechatDecryptDataUtil;
import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class WeChatLogin extends AppletsAbstract{

    @Autowired
    IMpInfoService mpInfoService;



    @Override
    public String getType() {
        return AppletTypeEnum.WECHAT_APPLET.getName();
    }

    @Override
    public void validteLoginParam(LoginParam loginParam) {
        log.info("begin WechatLogin.validteLoginParam:loginParam"+loginParam);
        if(StringUtils.isBlank(loginParam.getSiteName())||StringUtils.isBlank(loginParam.getMiniprogramType())||StringUtils.isBlank(loginParam.getCode())){
            throw new ValidDataException("小程序名或小程序类型或code不能为空");
        }
    }

    @Override
    public void validteSensitiveData(SensitiveDataParam sensitiveDataParam) {
        log.info("begin WechatLogin.validteSensitiveData:sensitiveDataParam"+sensitiveDataParam);
        if (StringUtils.isBlank(sensitiveDataParam.getSiteName())||StringUtils.isBlank(sensitiveDataParam.getMiniprogramType())){
            throw new ValidDataException("小程序名称或者小程序类型不能为空");
        }
        if (StringUtils.isBlank(sensitiveDataParam.getEncryptedData())||StringUtils.isBlank(sensitiveDataParam.getIv())||
                StringUtils.isBlank(sensitiveDataParam.getSessionKey())){
            throw new ValidDataException("解密数据encryptedData或者偏移量iv或者sessionKey不能为空");
        }
    }

    @Override
    public String doProcess(LoginParam loginParam) {
        log.info("begin WeChatController.doProcess:"+loginParam);
        MpInfo microProgramBasicInfo = mpInfoService.getMicroProgramBasicInfo(loginParam.getSiteName(), loginParam.getMiniprogramType());
        String appid = microProgramBasicInfo.getAppid();
        String secret = microProgramBasicInfo.getSecert();
        String code=loginParam.getCode();
        //授权（必填）
        String grant_type = "authorization_code";
        String params = "appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grant_type;
        //调用微信接口
        String sr = HttpClientUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session?" + params, null);
        log.info("微信返回的结果："+sr);
        if(Optional.ofNullable(sr).isPresent()){
            return sr;
        }
        return "微信返回的结果 sr 为空";
    }

    @Override
    public String decryptProcess(SensitiveDataParam sensitiveDataParam) {
        log.info("begin WechatLogin.decryptProcess:sensitiveDataParam"+sensitiveDataParam);
        String session_key = AESUtil.decrypt(sensitiveDataParam.getSessionKey(), "123456");
        String result = WechatDecryptDataUtil.decryptData(sensitiveDataParam.getEncryptedData(), session_key, sensitiveDataParam.getIv());
        log.info("微信数据解密结果result："+result);
        return result;
    }

    @Override
    public Map<String, String> handleResult(Object obj) {
        //处理返回参数
        log.info("begin WechatLogin.handleResult.obj:"+obj);
        String str=(String) obj;
        HashMap<String, String> resultMap = new HashMap<>();
        if(Optional.ofNullable(str).isPresent()){
            JSONObject jsonObject = JSONObject.fromObject(str);
            if(Optional.ofNullable(jsonObject.get("openid")).isPresent()){
                String openid = jsonObject.get("openid").toString();
                resultMap.put("openid", openid);
            }

            if(Optional.ofNullable(jsonObject.get("session_key")).isPresent()){
                String session_key = jsonObject.get("session_key").toString();
                String sessionKey = AESUtil.encrypt(session_key, "123456");
                resultMap.put("session_key", sessionKey);
            }
            if(Optional.ofNullable(jsonObject.get("errcode")).isPresent()){
                String errcode = jsonObject.get("errcode").toString();
                resultMap.put("errcode",errcode);
            }
            if(Optional.ofNullable(jsonObject.get("errmsg")).isPresent()){
                String errmsg = jsonObject.get("errmsg").toString();
                resultMap.put("errmsg",errmsg);
            }
            return resultMap;
        }
        resultMap.put("msg","处理返回参数出错");
        return resultMap;
    }

    @Override
    public Map<String, String> handleDecryptResult(String result) {
        log.info("begin WechatLogin.handleDecryptResult:"+result);
        HashMap<String, String> map = new HashMap<String, String>();
        com.alibaba.fastjson.JSONObject userInfo= com.alibaba.fastjson.JSONObject.parseObject(result);
        if(Optional.ofNullable(userInfo).isPresent()){
            if(Optional.ofNullable(userInfo.get("phoneNumber")).isPresent()){
                String phoneNumber = userInfo.get("phoneNumber").toString();
                map.put("phoneNumber", phoneNumber);
            }
            if(Optional.ofNullable(userInfo.get("purePhoneNumber")).isPresent()){
                String purePhoneNumber = userInfo.get("purePhoneNumber").toString();
                map.put("purePhoneNumber", purePhoneNumber);
            }
            if(Optional.ofNullable(userInfo.get("countryCode")).isPresent()){
                String  countryCode = userInfo.get("countryCode").toString();
                map.put("countryCode", countryCode);
            }
            return map;
        }
        map.put("code","500");
        map.put("errmsg","解密失败");
        return map;
    }
}
