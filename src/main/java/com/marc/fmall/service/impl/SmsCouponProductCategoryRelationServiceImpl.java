package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.SmsCouponProductCategoryRelation;
import com.marc.fmall.mapper.SmsCouponProductCategoryRelationMapper;
import com.marc.fmall.mapper.SmsCouponProductRelationMapper;
import com.marc.fmall.service.ISmsCouponProductCategoryRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券和产品分类关系表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
@Service
public class SmsCouponProductCategoryRelationServiceImpl extends ServiceImpl<SmsCouponProductCategoryRelationMapper, SmsCouponProductCategoryRelation> implements ISmsCouponProductCategoryRelationService {

    @Autowired
    private SmsCouponProductCategoryRelationMapper smsCouponProductCategoryRelationMapper;
    @Override
    public List<SmsCouponProductCategoryRelation> getListByCouponId(Long couponId) {
        QueryWrapper<SmsCouponProductCategoryRelation> smsCouponProductCategoryRelationQueryWrapper = new QueryWrapper<>();
        SmsCouponProductCategoryRelation queryParam = new SmsCouponProductCategoryRelation();
        queryParam.setCouponId(couponId);
        List<SmsCouponProductCategoryRelation> smsCouponProductCategoryRelations = smsCouponProductCategoryRelationMapper.selectList(smsCouponProductCategoryRelationQueryWrapper);
        return smsCouponProductCategoryRelations;
    }
}
