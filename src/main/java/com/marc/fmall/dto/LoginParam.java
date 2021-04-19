package com.marc.fmall.dto;

import com.marc.fmall.common.exception.ValidDataException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginParam implements Serializable {
    @ApiModelProperty(value = "小程序名称")
    @NotNull(message = "小程序名称不能为空")
    private String siteName;

    @ApiModelProperty(value = "小程序类型")
    @NotNull(message = "小程序类型不能为空")
    private String miniprogramType;


    @ApiModelProperty(value = "登录凭证code")
    @NotNull(message = "登录凭证code不能为空")
    private String code;

    public void validData(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                stringBuilder.append(allError.getDefaultMessage()+"\n");
                throw new ValidDataException(stringBuilder.toString());
            }
        }

    }

}
