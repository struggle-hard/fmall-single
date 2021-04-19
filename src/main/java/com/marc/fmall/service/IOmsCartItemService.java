package com.marc.fmall.service;

import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.entity.OmsCartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
public interface IOmsCartItemService extends IService<OmsCartItem> {

    /**
     * 购物车信息
     * @param openId
     * @return
     */
    List<OmsCartItem> cartlist(String openId);

    /**
     *获取包含促销活动信息的购物车列表
     */
    List<CartPromotionItem> listPromotion(String openId,List<Long> cartIds);
}
