package com.marc.fmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 积分消费设置
 * </p>
 *
 * @author marc
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsIntegrationConsumeSetting对象", description="积分消费设置")
public class UmsIntegrationConsumeSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "每一元需要抵扣的积分数量")
    private Integer deductionPerAmount;

    @ApiModelProperty(value = "每笔订单最高抵用百分比")
    private Integer maxPercentPerOrder;

    @ApiModelProperty(value = "每次使用积分最小单位100")
    private Integer useUnit;

    @ApiModelProperty(value = "是否可以和优惠券同用；0->不可以；1->可以")
    private Integer couponStatus;


}
