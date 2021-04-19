package com.marc.fmall.common;

/**
 * @Author: marc
 * @Date: 2020/12/22 10:24
 */
public interface WechatApiConstant {

    /**
     * TODO 微信支付API
     * 请求方式： POST
     * 微信统一下单接口API
     */
   String WECHAT_PRE_PAY="https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**
     * 请求方式： GET
     * 微信支付订单号查询订单API
     */
    String WECHAT_PAY_ORDERQUERY="https://api.mch.weixin.qq.com/v3/pay/transactions/id/";

    String WECHAT_MCH_ORDERQUERY="https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}";
    /**
     *关闭订单
     * 请求方式： POST
     */
    String WECHAT_PAY_CLOSEORDER=" https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}/close";

}
