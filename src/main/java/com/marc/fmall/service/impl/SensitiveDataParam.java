package com.marc.fmall.service.impl;

import com.marc.fmall.common.exception.ValidDataException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotNull;

@Data
public class SensitiveDataParam {
    @ApiModelProperty(value = "小程序名称")
    @NotNull(message = "小程序名称不能为空")
    private String siteName;

    @ApiModelProperty(value = "小程序类型")
    @NotNull(message = "小程序类型不能为空")
    private String miniprogramType;

    @ApiModelProperty(value = "支付宝加密的敏感参数")
    private String response;

    @ApiModelProperty(value = "支付宝前端发过来的参数")
    private String sign;

    @ApiModelProperty(value = "加密之后的敏感数据（微信，百度，字节）")
    private String encryptedData;

    @ApiModelProperty(value = "调用服务端获取登录凭证时候返回的openid")
    private String sessionKey;

    @ApiModelProperty(value = "偏移量")
    private String iv;

    public void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                stringBuilder.append(allError.getDefaultMessage() + "\n");
                throw new ValidDataException(stringBuilder.toString());
            }
        }
    }
}
