package com.marc.fmall.common;

/**
 * @Author: marc
 * @Date: 2020/12/22 10:41
 */
public enum WechatIErrorCode implements IErrorCode {

    USERPAYING(202,"用户支付中，需要输入密码"),
    TRADE_ERROR(403,"交易错误"),
    SYSTEMERROR(500,"系统错误"),
    SIGN_ERROR(401,"签名错误"),
    RULELIMIT(403,"业务规则限制"),
    PARAM_ERROR(400,"参数错误"),
    OUT_TRADE_NO_USED(403,"商户订单号重复"),
    ORDERNOTEXIST(404,"订单不存在"),
    ORDER_CLOSED(400,"订单已关闭"),
    OPENID_MISMATCH(500,"openid和appid不匹配"),
    NOTENOUGH(403,"余额不足"),
    NOAUTH(403,"商户无权限"),
    MCH_NOT_EXISTS(400,"商户号不存在"),
    INVALID_TRANSACTIONID(500,"订单号非法"),
    INVALID_REQUEST(400,"无效请求"),
    FREQUENCY_LIMITED(429,"频率超限"),
    BANKERROR(500,"银行系统异常"),
    APPID_MCHID_NOT_MATCH(400,"appid和mch_id不匹配"),
    ACCOUNTERROR(403,"账号异常");

    private Integer code;
    private String message;
    private WechatIErrorCode(Integer code, String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
