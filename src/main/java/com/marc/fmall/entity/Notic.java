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
 * 
 * </p>
 *
 * @author marc
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Notic对象", description="")
public class Notic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "通告创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否展示")
    private Integer isShow;


}
