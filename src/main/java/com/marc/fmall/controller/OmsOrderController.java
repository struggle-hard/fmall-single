package com.marc.fmall.controller;


import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.dto.OrderParam;
import com.marc.fmall.service.IOmsOrderService;
import com.marc.fmall.vo.ConfirmOrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.AuthorizationScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Api(value = "订单接口",tags = "订单接口")
@Slf4j
@RestController
@RequestMapping("/fmall/oms-order")
public class OmsOrderController {


    @Autowired
    private IOmsOrderService omsOrderService;

    @EagleEye
    @ApiOperation("根据购物车信息生成确认单信息")
    @RequestMapping(value = "/generateConfirmOrder",method = {RequestMethod.POST,RequestMethod.GET})
    public CommonResult generateConfirmOrder(@RequestParam("cartIds") List<Long> cartIds,@RequestParam("openId")String openId){
        ConfirmOrderResult result = omsOrderService.generateConfirmOrder(cartIds, openId);
        return CommonResult.success(result);
    }

    @EagleEye
    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder",method = {RequestMethod.POST})
    public CommonResult generateOrder(@RequestBody OrderParam orderParam){

     return  null;
    }

}
