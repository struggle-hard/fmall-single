package com.marc.fmall.service;

import com.marc.fmall.entity.SmsCouponProductRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券和产品的关系表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
public interface ISmsCouponProductRelationService extends IService<SmsCouponProductRelation> {

    List<SmsCouponProductRelation> getListByCouponId(Long couponId);
}
