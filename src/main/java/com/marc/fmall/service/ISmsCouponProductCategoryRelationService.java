package com.marc.fmall.service;

import com.marc.fmall.entity.SmsCouponProductCategoryRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券和产品分类关系表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
public interface ISmsCouponProductCategoryRelationService extends IService<SmsCouponProductCategoryRelation> {


    List<SmsCouponProductCategoryRelation> getListByCouponId(Long couponId);
}
