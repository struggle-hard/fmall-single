package com.marc.fmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2021-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Carousel对象", description="")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "图片地址")
    private String picUrl;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;


}
