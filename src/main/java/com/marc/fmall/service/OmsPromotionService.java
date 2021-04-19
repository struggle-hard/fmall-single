package com.marc.fmall.service;

import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.entity.OmsCartItem;

import java.util.List;

/**
 * @Author: marc
 * @Date: 2021/3/18 17:57
 */
public interface OmsPromotionService {

    /**
     * 计算购物车中的促销活动信息
     * @param cartItemList 购物车
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
