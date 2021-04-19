package com.marc.fmall.service;

import com.marc.fmall.dto.OrderParam;
import com.marc.fmall.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marc.fmall.vo.ConfirmOrderResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
public interface IOmsOrderService extends IService<OmsOrder> {

    /**
     * 根据用户购物车信息生成确认单信息
     */
    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds, String openId);

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    Map<String, Object> generateOrder(OrderParam orderParam);
}
