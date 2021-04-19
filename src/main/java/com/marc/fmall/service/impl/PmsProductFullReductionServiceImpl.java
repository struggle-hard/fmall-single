package com.marc.fmall.service.impl;

import com.marc.fmall.entity.PmsProductFullReduction;
import com.marc.fmall.mapper.PmsProductFullReductionMapper;
import com.marc.fmall.service.IPmsProductFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-19
 */
@Service
public class PmsProductFullReductionServiceImpl extends ServiceImpl<PmsProductFullReductionMapper, PmsProductFullReduction> implements IPmsProductFullReductionService {

}
