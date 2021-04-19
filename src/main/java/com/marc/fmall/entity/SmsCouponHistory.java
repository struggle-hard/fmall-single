package com.marc.fmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠券使用、领取历史表
 * </p>
 *
 * @author marc
 * @since 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmsCouponHistory对象", description="优惠券使用、领取历史表")
public class SmsCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long couponId;

    private String openid;

    private String couponCode;

    @ApiModelProperty(value = "领取人昵称")
    private String memberNickname;

    @ApiModelProperty(value = "获取类型：0->后台赠送；1->主动获取")
    private Integer getType;

    private LocalDateTime createTime;

    @ApiModelProperty(value = "使用状态：0->未使用；1->已使用；2->已过期")
    private Integer useStatus;

    @ApiModelProperty(value = "使用时间")
    private LocalDateTime useTime;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "订单号码")
    private String orderSn;


}
