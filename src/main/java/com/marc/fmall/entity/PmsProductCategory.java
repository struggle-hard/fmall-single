package com.marc.fmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 产品分类
 * </p>
 *
 * @author marc
 * @since 2021-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PmsProductCategory对象", description="产品分类")
public class PmsProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @ApiModelProperty(value = "是否显示在导航栏：0->不显示；1->显示")
    private Integer navStatus;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer showStatus;

    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "图标")
    private String icon;

    private String keywords;

    @ApiModelProperty(value = "描述")
    private String description;


}
