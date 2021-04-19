package com.marc.fmall.service;

import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.entity.SmsCouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
public interface ISmsCouponHistoryService extends IService<SmsCouponHistory> {

    /**
     * 根据用户openid获取优惠券详情列表
     * @param openid
     * @return
     */
    List<SmsCouponHistoryDetail> getDetailList(String openid);

    /**
     * 根据openid获取优惠券历史列表
     * @param openid
     * @return
     */
    List<SmsCouponHistory> getList(String openid);

    /**
     * 查询当前用户对应状态（可用或不可用）优惠券
     * @param openid
     * @param status
     * @return
     */
    List<SmsCouponHistory> getHistoryList(String openid,Integer status);

}
