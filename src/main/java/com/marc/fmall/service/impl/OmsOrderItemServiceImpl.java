package com.marc.fmall.service.impl;

import com.marc.fmall.entity.OmsOrderItem;
import com.marc.fmall.mapper.OmsOrderItemMapper;
import com.marc.fmall.service.IOmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-23
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements IOmsOrderItemService {

}
