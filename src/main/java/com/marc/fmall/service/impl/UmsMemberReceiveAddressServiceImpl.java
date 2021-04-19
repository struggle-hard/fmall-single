package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.UmsMemberReceiveAddress;
import com.marc.fmall.mapper.UmsMemberReceiveAddressMapper;
import com.marc.fmall.service.IUmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements IUmsMemberReceiveAddressService {

    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Override
    public List<UmsMemberReceiveAddress> listDelivery(String openId) {
        QueryWrapper<UmsMemberReceiveAddress> umsMemberReceiveAddressQueryWrapper = new QueryWrapper<>();
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setOpenId(openId);
        umsMemberReceiveAddressQueryWrapper.setEntity(umsMemberReceiveAddress);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectList(umsMemberReceiveAddressQueryWrapper);
        return umsMemberReceiveAddresses;
    }
}
