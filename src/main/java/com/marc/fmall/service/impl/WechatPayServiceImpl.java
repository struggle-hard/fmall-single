package com.marc.fmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marc.fmall.dto.WechatPayParam;
import com.marc.fmall.entity.MpInfo;
import com.marc.fmall.service.IMpInfoService;
import com.marc.fmall.service.IWechatPayService;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

/**
 * @Author: marc
 * @Date: 2021/3/16 14:53
 */
@Service
public class WechatPayServiceImpl implements IWechatPayService {
    @Value("${appid}")
    private String appid;

    @Value("${mchId}")
    private String mchId;

    @Value("${mchSerialNo}")
    private String mchSerialNo;

    @Value("${apiV3Key}")
    private String apiV3Key;

    @Override
    public String prePay() throws IOException {

        WechatPayParam wechatPayParam = new WechatPayParam();
        wechatPayParam.setAppid(appid);
        wechatPayParam.setMchid(mchId);
        wechatPayParam.setDescription("商品描述");
        wechatPayParam.setOutTradeNo("订单号");
        wechatPayParam.setNotifyUrl("通知地址");
        WechatPayParam.Amount amount = new WechatPayParam.Amount();
        amount.setTotal(0);
        amount.setCurrency("CNY");
        wechatPayParam.setAmount(amount);
        WechatPayParam.Payer payer = new WechatPayParam.Payer();
        payer.setOpenid("openid");
        wechatPayParam.setPayer(payer);
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream(new File("G:\\自己的GitHub项目\\那女孩果园\\证书\\apiclient_key.pem")));
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));
        X509Certificate wechatpayCertificate  = verifier.getValidCertificate();
        ArrayList<X509Certificate> wechatpayCertificates = new ArrayList<>();
        wechatpayCertificates.add(wechatpayCertificate);
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create().withMerchant(mchId, mchSerialNo, merchantPrivateKey).withWechatpay(wechatpayCertificates);
        HttpClient httpClient = builder.build();
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid",mchId)
                .put("appid", appid)
                .put("description", "Image形象店-深圳腾大-QQ公仔")
                .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
                .put("out_trade_no", "1217752501201407033233368018");
        rootNode.putObject("amount")
                .put("total", 1);
        rootNode.putObject("payer")
                .put("openid", "ooZNcv-dzwxcS7bHUh7Nv0Nx3uPw");
        objectMapper.writeValue(bos, rootNode);
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8")));
       HttpResponse response = httpClient.execute(httpPost);
        String bodyAsString = EntityUtils.toString(response.getEntity());
        JSONObject json = (JSONObject) JSONObject.parse(bodyAsString);
        String prepay_id = json.get("prepay_id").toString();
        System.out.println(prepay_id);
        return prepay_id;
    }

}
