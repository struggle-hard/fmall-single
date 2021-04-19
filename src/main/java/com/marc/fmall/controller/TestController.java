package com.marc.fmall.controller;

import io.swagger.annotations.Api;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: marc
 * @Date: 2021/3/18 9:52
 */
@Api(value = "测试接口",tags = "测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/aaa",method = {RequestMethod.POST,RequestMethod.GET})
    public  String test(){
        String s = RandomStringUtils.randomAlphanumeric(32);

        return s;
    }
}
