package com.marc.fmall.mapper;

import com.marc.fmall.domain.PromotionProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: marc
 * @Date: 2021/3/19 10:02
 */
@Mapper
public interface PortalProductMapper {

    /**
     * 获取促销商品信息列表
     * @param ids
     * @return
     */
    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);
}
