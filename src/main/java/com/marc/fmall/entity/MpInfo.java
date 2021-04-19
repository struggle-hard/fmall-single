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
 * 
 * </p>
 *
 * @author marc
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MpInfo对象", description="")
public class MpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "应用ID")
    private String appid;

    @ApiModelProperty(value = "秘钥")
    private String secert;

    @ApiModelProperty(value = "商户ID")
    private String mchid;

    @ApiModelProperty(value = "站点名称")
    private String siteName;

    @ApiModelProperty(value = "小程序类型")
    private String miniProgramType;

    @ApiModelProperty(value = "支付宝小程序会员手机号")
    private String mobile;

    @ApiModelProperty(value = "应用私钥")
    private String privateKey;

    @ApiModelProperty(value = "支付宝公钥")
    private String alipayPublicKey;

    @ApiModelProperty(value = "加解密密钥")
    private String decryptKey;

    @ApiModelProperty(value = "百度密钥")
    private String appKey;


}
