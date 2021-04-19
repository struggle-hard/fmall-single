package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.entity.SmsCoupon;
import com.marc.fmall.entity.SmsCouponHistory;
import com.marc.fmall.entity.SmsCouponProductCategoryRelation;
import com.marc.fmall.entity.SmsCouponProductRelation;
import com.marc.fmall.mapper.SmsCouponHistoryMapper;
import com.marc.fmall.service.ISmsCouponHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marc.fmall.service.ISmsCouponProductCategoryRelationService;
import com.marc.fmall.service.ISmsCouponProductRelationService;
import com.marc.fmall.service.ISmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
@Service
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryMapper, SmsCouponHistory> implements ISmsCouponHistoryService {

    @Autowired
    private SmsCouponHistoryMapper smsCouponHistoryMapper;
    @Autowired
    private ISmsCouponService smsCouponService;
    @Autowired
    private ISmsCouponProductRelationService smsCouponProductRelation;
    @Autowired
    private ISmsCouponProductCategoryRelationService smsCouponProductCategoryRelationService;
    @Override
    public List<SmsCouponHistoryDetail> getDetailList(String openid) {
        ArrayList<SmsCouponHistoryDetail> smsCouponHistoryDetails = new ArrayList<>();
        List<SmsCouponHistory> list = getList(openid);
        for (SmsCouponHistory smsCouponHistory : list) {
            SmsCouponHistoryDetail smsCouponHistoryDetail = new SmsCouponHistoryDetail();
            SmsCoupon smsCoupon = smsCouponService.getById(smsCouponHistory.getCouponId());
            smsCouponHistoryDetail.setCoupon(smsCoupon);
            List<SmsCouponProductRelation> smsCouponProductRelations = smsCouponProductRelation.getListByCouponId(smsCoupon.getId());
            smsCouponHistoryDetail.setProductRelationList(smsCouponProductRelations);
            List<SmsCouponProductCategoryRelation> smsCouponProductCategoryRelations = smsCouponProductCategoryRelationService.getListByCouponId(smsCoupon.getId());
            smsCouponHistoryDetail.setCategoryRelationList(smsCouponProductCategoryRelations);
            smsCouponHistoryDetails.add(smsCouponHistoryDetail);
        }
        return smsCouponHistoryDetails;
    }

    @Override
    public List<SmsCouponHistory> getList(String openid) {
        QueryWrapper<SmsCouponHistory> smsCouponHistoryQueryWrapper = new QueryWrapper<>();
        SmsCouponHistory queryParam = new SmsCouponHistory();
        queryParam.setOpenid(openid);
        List<SmsCouponHistory> smsCouponHistories= smsCouponHistoryMapper.selectList(smsCouponHistoryQueryWrapper);
        return smsCouponHistories;
    }

    @Override
    public List<SmsCouponHistory> getHistoryList(String openid, Integer status) {
        QueryWrapper<SmsCouponHistory> smsCouponHistoryQueryWrapper = new QueryWrapper<>();
        SmsCouponHistory queryParam = new SmsCouponHistory();
        queryParam.setOpenid(openid);
        queryParam.setUseStatus(status);
        List<SmsCouponHistory> smsCouponHistories= smsCouponHistoryMapper.selectList(smsCouponHistoryQueryWrapper);
        return smsCouponHistories;
    }
}
