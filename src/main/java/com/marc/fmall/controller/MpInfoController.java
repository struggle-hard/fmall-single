package com.marc.fmall.controller;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marc
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/fmall/mp-info")
public class MpInfoController {
    @RequestMapping("/test")
    public  String test(){
        String s = RandomStringUtils.randomAlphanumeric(32);

        return s;
    }
}
