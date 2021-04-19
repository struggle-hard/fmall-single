package com.marc.fmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marc.fmall.entity.MpInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-16
 */
public interface IMpInfoService extends IService<MpInfo> {
    MpInfo getMicroProgramBasicInfo(String siteName, String miniprogramType);
}
