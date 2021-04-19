package com.marc.fmall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: marc
 * @Date: 2021/3/15 15:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderParam {
    @ApiModelProperty("当前登录用户openId")
    private String openId;
    @ApiModelProperty("优惠券ID")
    private Long couponId;
    @ApiModelProperty("收货地址ID")
    private Long memberReceiveAddressId;
    @ApiModelProperty("使用的积分数")
    private Integer useIntegration;
    @ApiModelProperty("支付方式")
    private Integer payType;
    @ApiModelProperty("被选中的购物车商品ID")
    private List<Long> cartIds;
}
