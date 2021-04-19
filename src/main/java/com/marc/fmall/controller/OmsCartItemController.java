package com.marc.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.entity.OmsCartItem;
import com.marc.fmall.service.IOmsCartItemService;
import com.marc.fmall.util.EmojiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.parameters.P;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Slf4j
@Api(value = "购物车接口",tags = "购物车接口")
@RestController
@RequestMapping("/fmall/oms-cart-item")
public class OmsCartItemController {

    @Autowired
    private IOmsCartItemService omsCartItemService;

    @EagleEye
    @ApiOperation("添加商品至购物车")
    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult add(@RequestBody OmsCartItem omsCartItem){
        omsCartItem.setMemberNickname(EmojiUtil.resolveToByteFromEmoji(omsCartItem.getMemberNickname()));
        omsCartItem.setCreateDate(LocalDateTime.now());
        boolean flag = omsCartItemService.save(omsCartItem);
        if(flag=true){
            return CommonResult.success("添加成功");
        }
        return CommonResult.failed("添加失败");
    }

    @EagleEye
    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult list(@RequestParam("openId") String openId){
        QueryWrapper<OmsCartItem> omsCartItemQueryWrapper = new QueryWrapper<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setOpenId(openId);
        List<OmsCartItem> list = omsCartItemService.list(omsCartItemQueryWrapper);
        return CommonResult.success(list);
    }

    @EagleEye
    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity",method = {RequestMethod.POST})
    public CommonResult updateQuantity(@RequestParam Long id, @RequestParam Integer quantity){
        OmsCartItem oldCart = omsCartItemService.getById(id);
        QueryWrapper<OmsCartItem> omsCartItemQueryWrapper = new QueryWrapper<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        BeanUtils.copyProperties(omsCartItem,oldCart);
        omsCartItem.setQuantity(quantity);
        omsCartItem.setModifyDate(LocalDateTime.now());
        boolean flag = omsCartItemService.update(omsCartItem, omsCartItemQueryWrapper);
        if(flag=true){
            return CommonResult.success("修改成功");
        }
        return CommonResult.failed("修改失败");
    }
    
    @EagleEye
    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult delete(@RequestParam("ids") List<Long> ids,@RequestParam("openId")String openId){
        if(openId==null||openId.equals("")){
            return CommonResult.failed("必须先登录");
        }
        boolean flag = omsCartItemService.removeByIds(ids);
        if(flag=true) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.failed("删除失败");
    }

    @EagleEye
    @ApiOperation("清除购物车")
    @RequestMapping(value = "/clear",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult clear(@RequestParam("openId") String openId){
        QueryWrapper<OmsCartItem> omsCartItemQueryWrapper = new QueryWrapper<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setOpenId(openId);
        omsCartItemQueryWrapper.setEntity(omsCartItem);
        boolean flag = omsCartItemService.remove(omsCartItemQueryWrapper);
        if(flag=true){
            return CommonResult.success("清空购物车成功");
        }
        return CommonResult.failed("清空购物车失败");
    }


}
