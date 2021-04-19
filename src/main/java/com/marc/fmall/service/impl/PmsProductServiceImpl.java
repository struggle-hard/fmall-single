package com.marc.fmall.service.impl;

import com.marc.fmall.entity.PmsProduct;
import com.marc.fmall.mapper.PmsProductMapper;
import com.marc.fmall.service.IPmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-12
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

}
