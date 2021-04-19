package com.marc.fmall.util;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: marc
 * @Date: 2021/3/18 11:13
 */
@Aspect
public class WechatPayUtil {

    @Value("${appid}")
    private String appid;

    @Value("${mchId}")
    private String mchId;

    @Value("${mchSerialNo}")
    private String mchSerialNo;

    @Value("${apiV3Key}")
    private String apiV3Key;

    public void setup() throws IOException {
        // 加载商户私钥（privateKey：私钥字符串）
//        PrivateKey merchantPrivateKey = PemUtil
//                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
//
//        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3秘钥）
//        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
//                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),apiV3Key.getBytes("utf-8"));
//
//        // 初始化httpClient
//        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
//                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
//                .withValidator(new WechatPay2Validator(verifier)).build();
    }

//    @After
//    public void after() throws IOException {
//        httpClient.close();
//    }

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }
}
