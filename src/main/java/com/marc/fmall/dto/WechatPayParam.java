package com.marc.fmall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * @Author: marc
 * @Date: 2021/3/16 14:26
 */
@Data
public class WechatPayParam {

    @ApiModelProperty(value = "直连商户申请的公众号或移动应用appid。")
    private String appid;
    @ApiModelProperty(value = "直连商户的商户号，由微信支付生成并下发")
    private String mchid;
    @ApiModelProperty(value = "商品描述")
    private String description;
    @ApiModelProperty(value = "商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一")
    private String outTradeNo;
    @ApiModelProperty(value = "订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日")
    private String timeExpire;
    @ApiModelProperty(value = "附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用")
    private String attach;
    @ApiModelProperty(value = "通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址。")
    private String notifyUrl;
    @ApiModelProperty(value = "订单优惠标记")
    private String goodsTag;

    private Amount amount;

    private Payer payer;

    private Detail detail;

    private GoodsDetail goodsDetail;

    private SceneInfo sceneInfo;

    private SettleInfo settleInfo;

    @Data
    public static class  Amount{
        @ApiModelProperty(value = "订单总金额，单位为分")
        private Integer total;
        @ApiModelProperty(value = "CNY：人民币，境内商户号仅支持人民币")
        private String currency;

    }
    @Data
    public static  class  Payer{
        @ApiModelProperty(value = "用户在直连商户appid下的唯一标识")
        private String openid;
    }

    @Data
    public static class Detail{
        @ApiModelProperty(value = "商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。\n" +
                "2、当订单原价与支付金额不相等，则不享受优惠。\n" +
                "3、该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数")
        private Integer costPrice;
        @ApiModelProperty(value = "商家小票ID")
        private String invoiceId;
    }

    @Data
    public static  class GoodsDetail{
        @ApiModelProperty(value = "商品编码")
        private String merchantGoodsId;
        @ApiModelProperty(value = "微信支付定义的统一商品编号")
        private String wechatpayGoodsId;
        @ApiModelProperty(value = "商品的实际名称")
        private String goodsName;
        @ApiModelProperty(value = "用户购买的数量")
        private Integer quantity;
        @ApiModelProperty(value = "商品单价，单位为分")
        private Integer unitPrice;
    }

    @Data
    public static  class  SceneInfo{
        @ApiModelProperty(value = "用户的客户端IP，支持IPv4和IPv6两种格式的IP地址")
        private  String payerClientIp;
        @ApiModelProperty(value = "商户端设备号（门店号或收银设备ID）")
        private  String deviceId;

        private StoreInfo storeInfo;
        @Data
        public static class StoreInfo{
            @ApiModelProperty(value = "商户侧门店编号")
            private String id;
            @ApiModelProperty(value = "商户侧门店名称")
            private String name;
            @ApiModelProperty(value = "地区编码")
            private String areaCode;
            @ApiModelProperty(value = "详细的商户门店地址")
            private String address;
        }
    }
    @Data
    public static class SettleInfo{
        @ApiModelProperty(value = "是否指定分账")
        private boolean profitSharing;
    }

}
