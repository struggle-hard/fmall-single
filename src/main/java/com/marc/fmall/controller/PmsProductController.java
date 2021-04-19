package com.marc.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.entity.PmsProduct;
import com.marc.fmall.mapper.PmsProductMapper;
import com.marc.fmall.service.IPmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-12
 */
@Slf4j
@Api(value = "商品首页推荐",tags = "商品首页推荐")
@RestController
@RequestMapping("/fmall/pms-product")
public class PmsProductController {

    @Autowired
    private IPmsProductService pmsProductService;

    @EagleEye
    @ApiOperation("首页推荐商品列表")
    @RequestMapping(value = "/recommendList",method = {RequestMethod.GET,RequestMethod.POST})
    public Map recommendList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){
        HashMap<String, Object> resultMap = new HashMap<>();
        Page<PmsProduct> pmsProductPage = new Page<>(pageNum,pageSize);
        QueryWrapper<PmsProduct> pmsProductQueryWrapper = new QueryWrapper<>();
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setRecommandStatus(1);
        pmsProductQueryWrapper.setEntity(pmsProduct);
        Page<PmsProduct> page = pmsProductService.page(pmsProductPage, pmsProductQueryWrapper);
        resultMap.put("total",page.getTotal());
        resultMap.put("pages", page.getPages());
        resultMap.put("current", page.getCurrent());
        resultMap.put("size", page.getSize());
        List<PmsProduct> records = page.getRecords();
        if(records.size()<pageSize){
            resultMap.put("isEnd",true);
        }else {
            resultMap.put("isEnd",false);
        }
        resultMap.put("data",records);
        return resultMap;
    }

    @EagleEye
    @ApiOperation("商品详情")
    @RequestMapping(value = "/productDetail",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult productDetail(@RequestParam String productSn){
        QueryWrapper<PmsProduct> pmsProductQueryWrapper = new QueryWrapper<>();
        PmsProduct queryParam = new PmsProduct();
        queryParam.setProductSn(productSn);
        pmsProductQueryWrapper.setEntity(queryParam);
        PmsProduct product = pmsProductService.getOne(pmsProductQueryWrapper);
        return CommonResult.success(product);
    }

    @EagleEye
    @ApiOperation("查询分类下的商品列表")
    @RequestMapping(value = "productList",method = {RequestMethod.GET,RequestMethod.POST})
    public Map  productList(@RequestParam Integer categoryId,
                            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){
        HashMap<String, Object> resultMap = new HashMap<>();
        Page<PmsProduct> pmsProductPage = new Page<>(pageNum,pageSize);
        QueryWrapper<PmsProduct> pmsProductQueryWrapper = new QueryWrapper<>();
        PmsProduct product = new PmsProduct();
        product.setProductCategoryId(categoryId);
        Page<PmsProduct> page = pmsProductService.page(pmsProductPage, pmsProductQueryWrapper);
        resultMap.put("total",page.getTotal());
        resultMap.put("pages", page.getPages());
        resultMap.put("current", page.getCurrent());
        resultMap.put("size", page.getSize());
        List<PmsProduct> records = page.getRecords();
        if(records.size()<pageSize){
            resultMap.put("isEnd",true);
        }else {
            resultMap.put("isEnd",false);
        }
        resultMap.put("data",records);
        return resultMap;
    }
}
