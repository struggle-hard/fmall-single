package com.marc.fmall.service;

import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.entity.SmsCoupon;
import com.marc.fmall.entity.SmsCouponHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户优惠券管理Service
 * Created by macro on 2018/8/29.
 */
public interface UmsMemberCouponService {
    /**
     * 会员添加优惠券
     */
    @Transactional
    void add(Long couponId,String openid);

    /**
     * 获取优惠券历史列表
     */
    List<SmsCouponHistory> listHistory(Integer useStatus,String openid);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type,String openid);

    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);

    /**
     * 获取用户优惠券列表
     */
    List<SmsCoupon> list(Integer useStatus,String openid);
}
