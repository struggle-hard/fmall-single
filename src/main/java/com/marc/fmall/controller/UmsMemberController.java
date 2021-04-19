package com.marc.fmall.controller;


import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.exception.ValidDataException;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.dto.LoginParam;
import com.marc.fmall.entity.UmsMember;
import com.marc.fmall.service.IUmsMemberReceiveAddressService;
import com.marc.fmall.service.IUmsMemberService;
import com.marc.fmall.service.impl.AppletsAbstract;
import com.marc.fmall.util.EmojiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x509.V2AttributeCertificateInfoGenerator;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Api(value = "会员功能接口",tags = "会员功能接口")
@Slf4j
@RestController
@RequestMapping("/fmall/ums-member")
public class UmsMemberController {

    @Autowired
    private IUmsMemberService umsMemberService;

    @EagleEye
    @ApiOperation(value = "会员登录")
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public CommonResult login(@RequestBody @Validated  LoginParam loginParam, BindingResult bindingResult){

        //校验传入的参数
        loginParam.validData(bindingResult);
        AppletsAbstract appletLoginType = AppletsAbstract.appletMap.get(loginParam.getMiniprogramType());
        if(appletLoginType==null){
            log.error("暂不支持该平台小程序");
            throw new ValidDataException("暂不支持该平台小程序");
        }
        CommonResult commonResult = appletLoginType.loginRecore(loginParam);
        return commonResult;
    }

    @EagleEye
    @ApiOperation(value = "会员信息保存")
    @RequestMapping(value = "/saveInfo",method = {RequestMethod.POST})
    public CommonResult saveInfo(@RequestBody UmsMember umsMember){
        umsMember.setCreateTime(LocalDateTime.now());
        umsMember.setMemberLevelId(1L);
        umsMember.setNickname(EmojiUtil.resolveToByteFromEmoji(umsMember.getNickname()));
        umsMember.setStatus(1);
        umsMember.setSourceType(1);
        umsMember.setGrowth(0);
        umsMember.setLuckeyCount(0);
        umsMember.setHistoryIntegration(0);
        boolean flag = umsMemberService.save(umsMember);
        if(flag=true){
            return CommonResult.success("保存成功");
        }
        return CommonResult.failed("操作失败");
    }
}
