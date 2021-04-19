package com.marc.fmall.domain;



import com.marc.fmall.entity.OmsCartItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by macro on 2018/8/27.
 * 购物车中促销信息的封装
 */
@Data
public class CartPromotionItem extends OmsCartItem {
    //促销活动信息
    private String promotionMessage;
    //促销活动减去的金额，针对每个商品
    private BigDecimal reduceAmount;
    //商品的真实库存（剩余库存-锁定库存）
    private Integer realStock;
    //购买商品赠送积分
    private Integer integration;
    //购买商品赠送成长值
    private Integer growth;

    public String getPromotionMessage() {
        return promotionMessage;
    }

    public void setPromotionMessage(String promotionMessage) {
        this.promotionMessage = promotionMessage;
    }
}
