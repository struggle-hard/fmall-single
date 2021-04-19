package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.SmsCouponProductRelation;
import com.marc.fmall.mapper.SmsCouponProductRelationMapper;
import com.marc.fmall.service.ISmsCouponProductRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券和产品的关系表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
@Service
public class SmsCouponProductRelationServiceImpl extends ServiceImpl<SmsCouponProductRelationMapper, SmsCouponProductRelation> implements ISmsCouponProductRelationService {

    @Autowired
    private SmsCouponProductRelationMapper smsCouponProductRelationMapper;
    @Override
    public List<SmsCouponProductRelation> getListByCouponId(Long couponId) {
        QueryWrapper<SmsCouponProductRelation> smsCouponProductRelationQueryWrapper = new QueryWrapper<>();
        SmsCouponProductRelation queryParam = new SmsCouponProductRelation();
        queryParam.setCouponId(couponId);
        List<SmsCouponProductRelation> smsCouponProductRelations = smsCouponProductRelationMapper.selectList(smsCouponProductRelationQueryWrapper);
        return smsCouponProductRelations;
    }
}
