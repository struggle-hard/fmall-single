package com.marc.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.entity.PmsProductCategory;
import com.marc.fmall.service.IPmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-12
 */
@Slf4j
@Api(value = "商品分类接口",tags = "商品分类接口")
@RestController
@RequestMapping("/fmall/pms-product-category")
public class PmsProductCategoryController {


    @Autowired
    private IPmsProductCategoryService pmsProductCategoryService;

    @EagleEye
    @ApiOperation("新增分类")
    @RequestMapping(value = "/addCategory",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult addCategory(@RequestBody PmsProductCategory pmsProductCategory){
        pmsProductCategory.setShowStatus(1);
        //显示在导航栏
        pmsProductCategory.setNavStatus(1);
        pmsProductCategory.setCreateTime(LocalDateTime.now());
        boolean flag = pmsProductCategoryService.save(pmsProductCategory);
        if(flag==true){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.success("操作失败");
        }
    }

    @EagleEye
    @ApiOperation("删除分类")
    @RequestMapping(value = "/delCategory",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult delCategory(@RequestParam Integer id){
        QueryWrapper<PmsProductCategory> pmsProductCategoryQueryWrapper = new QueryWrapper<>();
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(id);
        boolean flag = pmsProductCategoryService.removeById(id);
        if(flag=true){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }

    @EagleEye
    @ApiOperation("分类列表")
    @RequestMapping(value = "/categoryList",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult categoryList(){
        List<PmsProductCategory> list = pmsProductCategoryService.list();
        return CommonResult.success(list);
    }

    @EagleEye
    @ApiOperation("分类详情")
    @RequestMapping(value = "/categoryDetail/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult categoryDetail(@PathVariable("id") Integer id){

        PmsProductCategory pmsProductCategory = pmsProductCategoryService.getById(id);
        return CommonResult.success(pmsProductCategory);
    }

    @EagleEye
    @ApiOperation("分类详情")
    @RequestMapping(value = "/modifyCategory",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult categoryDetail(@RequestBody PmsProductCategory pmsProductCategory){
        QueryWrapper<PmsProductCategory> pmsProductCategoryQueryWrapper = new QueryWrapper<>();
        pmsProductCategoryQueryWrapper.setEntity(pmsProductCategory);
        boolean flag = pmsProductCategoryService.update(pmsProductCategoryQueryWrapper);
        if(flag=true){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }

    }
}
