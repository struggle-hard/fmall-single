package com.marc.fmall.service;

import com.marc.fmall.entity.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
public interface IUmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {

    /**
     * 查询当前用户的地址列表
     * @param openId
     * @return
     */
    List<UmsMemberReceiveAddress> listDelivery(String openId);
}
