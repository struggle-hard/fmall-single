package com.marc.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.common.CommonResult;
import com.marc.fmall.common.log.EagleEye;
import com.marc.fmall.entity.Carousel;
import com.marc.fmall.service.ICarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-12
 */
@Slf4j
@Api(value = "轮播图接口",tags = "首页轮播图接口")
@RestController
@RequestMapping("/fmall/carousel")
public class CarouselController {

    @Autowired
    private ICarouselService carouselService;

    @EagleEye
    @ApiOperation("轮播图列表")
    @RequestMapping(value = "/carouselList",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult carouselList(){
        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        carouselQueryWrapper.orderByAsc("sort");
        List<Carousel> list = carouselService.list(carouselQueryWrapper);
        return CommonResult.success(list);
    }

    @EagleEye
    @ApiOperation(value = "新增轮播图")
    @RequestMapping(value = "/addCarouse",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult addCarouse(@RequestBody Carousel carousel){
        carousel.setCreateTime(LocalDateTime.now());
        boolean flag = carouselService.save(carousel);
        if(flag=true){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }

    @EagleEye
    @ApiOperation(value = "删除轮播图")
    @RequestMapping(value = "/delCarouse/{id}",method = {RequestMethod.GET})
    public CommonResult delCarouse(@PathVariable("id") Integer id){
        boolean flag = carouselService.removeById(id);
        if(flag=true){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }

    }

}
