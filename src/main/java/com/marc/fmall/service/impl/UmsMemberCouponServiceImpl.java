package com.marc.fmall.service.impl;

import cn.hutool.core.collection.CollUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.common.exception.Asserts;
import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.entity.*;
import com.marc.fmall.mapper.*;
import com.marc.fmall.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 会员优惠券管理Service实现类
 * Created by macro on 2018/8/29.
 */
@Slf4j
@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {
    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private SmsCouponMapper couponMapper;
    @Autowired
    private SmsCouponHistoryMapper couponHistoryMapper;
    @Autowired
    private ISmsCouponHistoryService smsCouponHistoryService;
    @Autowired
    private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private ISmsCouponProductRelationService smsCouponProductRelationService;
    @Autowired
    private ISmsCouponService smsCouponService;

    @Override
    public void add(Long couponId,String openid) {
        UmsMember currentMember = memberService.getCurrentMember(openid);
        //获取优惠券信息，判断数量
        SmsCoupon coupon = couponMapper.selectById(couponId);
        if(coupon==null){
            Asserts.fail("优惠券不存在");
        }
        if(coupon.getCount()<=0){
            Asserts.fail("优惠券已经领完了");
        }
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(coupon.getEnableTime())){
            Asserts.fail("优惠券还没到领取时间");
        }
        //判断用户领取的优惠券数量是否超过限制
        QueryWrapper<SmsCouponHistory> smsCouponHistoryQueryWrapper = new QueryWrapper<>();
        SmsCouponHistory queryParam = new SmsCouponHistory();
        queryParam.setCouponId(couponId);
        queryParam.setOpenid(currentMember.getOpenId());
        List<SmsCouponHistory> smsCouponHistories = couponHistoryMapper.selectList(smsCouponHistoryQueryWrapper);
        if(smsCouponHistories.size()>coupon.getPerLimit()){
            Asserts.fail("您已经领取过该优惠券");
        }

//        SmsCouponHistoryExample couponHistoryExample = new SmsCouponHistoryExample();
//        couponHistoryExample.createCriteria().andCouponIdEqualTo(couponId).andMemberIdEqualTo(currentMember.getId());
//        long count = couponHistoryMapper.countByExample(couponHistoryExample);
//        if(count>=coupon.getPerLimit()){
//            Asserts.fail("您已经领取过该优惠券");
//        }
        //生成领取优惠券历史
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setCouponId(couponId);
        couponHistory.setCouponCode(generateCouponCode(currentMember.getOpenId()));
        couponHistory.setCreateTime(now);
        couponHistory.setOpenid(currentMember.getOpenId());
        couponHistory.setMemberNickname(currentMember.getNickname());
        //主动领取
        couponHistory.setGetType(1);
        //未使用
        couponHistory.setUseStatus(0);
        couponHistoryMapper.insert(couponHistory);
        //修改优惠券表的数量、领取数量
        coupon.setCount(coupon.getCount()-1);
        coupon.setReceiveCount(coupon.getReceiveCount()==null?1:coupon.getReceiveCount()+1);
        QueryWrapper<SmsCoupon> smsCouponQueryWrapper = new QueryWrapper<>();
        SmsCoupon modifyParam = new SmsCoupon();
        modifyParam.setId(couponId);
        try{
            int update = couponMapper.update(coupon, smsCouponQueryWrapper);
        }catch (Exception e){
            log.error("更新优惠券数量失败");
        }
    }

    /**
     * 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位
     */
    private String generateCouponCode(String openid) {
        StringBuilder sb = new StringBuilder();
        Long currentTimeMillis = System.currentTimeMillis();
        String timeMillisStr = currentTimeMillis.toString();
        sb.append(timeMillisStr.substring(timeMillisStr.length() - 8));
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        if (openid.length() <= 4) {
            sb.append(String.format("%04d", openid));
        } else {
            sb.append(openid.substring(openid.length()-4));
        }
        return sb.toString();
    }

    @Override
    public List<SmsCouponHistory> listHistory(Integer useStatus,String openid) {
        UmsMember currentMember = memberService.getCurrentMember(openid);
        QueryWrapper<SmsCouponHistory> smsCouponHistoryQueryWrapper = new QueryWrapper<>();
        SmsCouponHistory queryParam = new SmsCouponHistory();
        queryParam.setOpenid(openid);
        queryParam.setUseStatus(useStatus);
        List<SmsCouponHistory> smsCouponHistories = couponHistoryMapper.selectList(smsCouponHistoryQueryWrapper);
        return smsCouponHistories;
    }

    @Override
    public List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type,String openid) {
        UmsMember currentMember = memberService.getCurrentMember(openid);
        LocalDateTime now = LocalDateTime.now();
        //获取该用户所有优惠券
        List<SmsCouponHistoryDetail> allList = smsCouponHistoryService.getDetailList(openid);
        //根据优惠券使用类型来判断优惠券是否可用
        List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
        List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
        for (SmsCouponHistoryDetail couponHistoryDetail : allList) {
            Integer useType = couponHistoryDetail.getCoupon().getUseType();
            BigDecimal minPoint = couponHistoryDetail.getCoupon().getMinPoint();
            LocalDateTime endTime = couponHistoryDetail.getCoupon().getEndTime();
            if(useType.equals(0)){
                //0->全场通用
                //判断是否满足优惠起点
                //计算购物车商品的总价
                BigDecimal totalAmount = calcTotalAmount(cartItemList);
                if(now.isBefore(endTime)&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(1)){
                //1->指定分类
                //计算指定分类商品的总价
                List<Long> productCategoryIds = new ArrayList<>();
                for (SmsCouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                    productCategoryIds.add(categoryRelation.getProductCategoryId());
                }
                BigDecimal totalAmount = calcTotalAmountByproductCategoryId(cartItemList,productCategoryIds);
                if(now.isBefore(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(2)){
                //2->指定商品
                //计算指定商品的总价
                List<Long> productIds = new ArrayList<>();
                for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                    productIds.add(productRelation.getProductId());
                }
                BigDecimal totalAmount = calcTotalAmountByProductId(cartItemList,productIds);
                if(now.isBefore(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }
        }
        if(type.equals(1)){
            return enableList;
        }else{
            return disableList;
        }
    }

    @Override
    public List<SmsCoupon> listByProduct(Long productId) {
        List<Long> allCouponIds = new ArrayList<>();
        //获取指定商品优惠券
        List<SmsCouponProductRelation> cprList = smsCouponProductRelationService.getListByCouponId(productId);
        if(CollUtil.isNotEmpty(cprList)){
            List<Long> couponIds = cprList.stream().map(SmsCouponProductRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //获取指定分类优惠券
        PmsProduct product = productMapper.selectById(productId);
        QueryWrapper<SmsCouponProductCategoryRelation> smsCouponProductCategoryRelationQueryWrapper = new QueryWrapper<>();
        SmsCouponProductCategoryRelation queryParam = new SmsCouponProductCategoryRelation();
        queryParam.setProductCategoryId((long)product.getProductCategoryId());
        List<SmsCouponProductCategoryRelation> cpcrList = couponProductCategoryRelationMapper.selectList(smsCouponProductCategoryRelationQueryWrapper);
        if(CollUtil.isNotEmpty(cpcrList)){
            List<Long> couponIds = cpcrList.stream().map(SmsCouponProductCategoryRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        if(CollUtil.isEmpty(allCouponIds)){
            return new ArrayList<>();
        }
        //所有优惠券
        //TODO 这部分还未完成暂时注释
        QueryWrapper<SmsCoupon> smsCouponQueryWrapper = new QueryWrapper<>();
        SmsCoupon param = new SmsCoupon();
        param.setUseType(0);
        smsCouponQueryWrapper.gt("end_time",LocalDateTime.now());
        smsCouponQueryWrapper.lt("start_time",LocalDateTime.now());

        return couponMapper.selectList(smsCouponQueryWrapper);
//
//
//
//
//
//
//
//        SmsCouponExample couponExample = new SmsCouponExample();
//        couponExample.createCriteria().andEndTimeGreaterThan(new Date())
//                .andStartTimeLessThan(new Date())
//                .andUseTypeEqualTo(0);
//        couponExample.or(couponExample.createCriteria()
//                .andEndTimeGreaterThan(new Date())
//                .andStartTimeLessThan(new Date())
//                .andUseTypeNotEqualTo(0)
//                .andIdIn(allCouponIds));
//        return couponMapper.selectByExample(couponExample);
    }

    @Override
    public List<SmsCoupon> list(Integer useStatus,String openid) {

        ArrayList<SmsCoupon> smsCoupons = new ArrayList<>();
        List<SmsCouponHistory> historyList = smsCouponHistoryService.getHistoryList(openid, useStatus);
        for (SmsCouponHistory smsCouponHistory : historyList) {
            SmsCoupon coupon = smsCouponService.getById(smsCouponHistory.getCouponId());
            smsCoupons.add(coupon);
        }
        return smsCoupons;
    }

    private BigDecimal calcTotalAmount(List<CartPromotionItem> cartItemList) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
            BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
            total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }

    private BigDecimal calcTotalAmountByproductCategoryId(List<CartPromotionItem> cartItemList,List<Long> productCategoryIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
            if(productCategoryIds.contains(item.getProductCategoryId())){
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    private BigDecimal calcTotalAmountByProductId(List<CartPromotionItem> cartItemList,List<Long> productIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
            if(productIds.contains(item.getProductId())){
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

}
