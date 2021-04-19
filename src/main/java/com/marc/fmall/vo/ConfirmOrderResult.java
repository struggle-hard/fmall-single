package com.marc.fmall.vo;

import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.entity.OmsCartItem;
import com.marc.fmall.entity.UmsIntegrationConsumeSetting;
import com.marc.fmall.entity.UmsMemberReceiveAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: marc
 * @Date: 2021/3/15 14:34
 */
@Data
public class ConfirmOrderResult {
    //购物车信息
    private List<CartPromotionItem> cartPromotionItemList;
    //用户可用优惠券列表
    private List<SmsCouponHistoryDetail> couponHistoryDetailList;
    //用户收货地址列表
    private List<UmsMemberReceiveAddress> memberReceiveAddressList;
    //计算的金额
    private CalcAmount calcaAmount;
    //会员持有的积分
    private Integer memberIntegration;
    //积分使用规则
    private UmsIntegrationConsumeSetting integrationConsumeSetting;


    @Data
    public static class CalcAmount{
        //订单商品总金额
        private BigDecimal totalAmount;
        //运费
        private BigDecimal freightAmount;
        //优惠价格
        private BigDecimal PromotionAmount;
        //应付金额
        private BigDecimal payAmount;


    }

}
