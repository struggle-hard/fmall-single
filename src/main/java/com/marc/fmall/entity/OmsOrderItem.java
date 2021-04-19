package com.marc.fmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单中所包含的商品
 * </p>
 *
 * @author marc
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmsOrderItem对象", description="订单中所包含的商品")
public class OmsOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    private Long productId;

    private String productPic;

    private String productName;

    private String productBrand;

    private String productSn;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer productQuantity;

    @ApiModelProperty(value = "商品sku编号")
    private Long productSkuId;

    @ApiModelProperty(value = "商品sku条码")
    private String productSkuCode;

    @ApiModelProperty(value = "商品分类id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品促销名称")
    private String promotionName;

    @ApiModelProperty(value = "商品促销分解金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "积分优惠分解金额")
    private BigDecimal integrationAmount;

    @ApiModelProperty(value = "该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    private Integer giftIntegration;

    private Integer giftGrowth;

    @ApiModelProperty
    private String productAttr;


}
