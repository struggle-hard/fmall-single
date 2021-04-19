package com.marc.fmall.controller;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import com.marc.fmall.common.CommonResult;
import com.marc.fmall.service.IWechatPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: marc
 * @Date: 2021/3/16 14:19
 */
@Api(value = "微信支付接口",tags = "微信支付接口")
@Slf4j
@RestController
@RequestMapping(value = "/fmall/wechat-pay")
public class WechatPayController {

    @Autowired
    private IWechatPayService wechatPayService;

    @ApiOperation("统一下单")
    @RequestMapping("/transactions")
    public CommonResult pre_pay() {
        String prepay_id = null;
        try {
            prepay_id = wechatPayService.prePay();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(prepay_id);
    }

    @ApiOperation("支付成功回调地址")
    @RequestMapping("/paySuccess")
    public CommonResult paySuccess(HttpServletRequest request){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String read = IoUtil.read(bufferedReader);
            JSONObject jsonObject =(JSONObject) JSONObject.parse(read);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success("操作成功");
    }


}
