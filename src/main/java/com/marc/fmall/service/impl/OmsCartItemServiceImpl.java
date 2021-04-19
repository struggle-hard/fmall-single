package com.marc.fmall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.entity.OmsCartItem;
import com.marc.fmall.mapper.OmsCartItemMapper;
import com.marc.fmall.service.IOmsCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marc.fmall.service.OmsPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements IOmsCartItemService {

    @Autowired
    private OmsCartItemMapper omsCartItemMapper;
    @Autowired
    private OmsPromotionService omsPromotionService;
    @Override
    public List<OmsCartItem> cartlist(String openId) {
        QueryWrapper<OmsCartItem> omsCartItemQueryWrapper = new QueryWrapper<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setOpenId(openId);
        omsCartItemQueryWrapper.setEntity(omsCartItem);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectList(omsCartItemQueryWrapper);
        return omsCartItems;
    }

    @Override
    public List<CartPromotionItem> listPromotion(String openId, List<Long> cartIds) {
        List<OmsCartItem> cartlist = cartlist(openId);
        if(CollUtil.isNotEmpty(cartIds)){
            cartlist= cartlist.stream().filter(item -> cartIds.contains(item.getId())).collect(Collectors.toList());
        }
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(cartlist)){
            cartPromotionItemList= omsPromotionService.calcCartPromotion(cartlist);
        }
        return cartPromotionItemList;
    }
}
