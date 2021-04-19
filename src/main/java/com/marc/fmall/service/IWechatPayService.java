package com.marc.fmall.service;

import com.alibaba.fastjson.JSONObject;
import com.marc.fmall.dto.WechatPayParam;

import java.io.IOException;

/**
 * 微信支付接口
 * @Author: marc
 * @Date: 2021/3/16 14:52
 */

public interface IWechatPayService {

    /**
     * 微信统一下单  预支付接口
     * @param
     * @return
     */
    String prePay() throws IOException;
}
