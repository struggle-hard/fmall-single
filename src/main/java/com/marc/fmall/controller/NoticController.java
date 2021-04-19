package com.marc.fmall.controller;


import com.marc.fmall.common.CommonResult;
import com.marc.fmall.entity.Notic;
import com.marc.fmall.service.INoticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/fmall/notic")
public class NoticController {


    @Autowired
    private INoticService noticService;

    @ApiOperation("通知列表")
    @GetMapping(value = "/list")
    public CommonResult list(){
        List<Notic> list = noticService.list();
        return CommonResult.success(list);
    }

    @ApiOperation("新增通告")
    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody Notic notic){
        int flag = noticService.insertNotic(notic);
        if(flag==1){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }
    @ApiOperation("删除通告")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam Integer id){
        int flag = noticService.deleteNotic(id);
        if(flag==1){
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }

}
