package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.MpInfo;
import com.marc.fmall.mapper.MpInfoMapper;
import com.marc.fmall.service.IMpInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-16
 */
@Service
public class MpInfoServiceImpl extends ServiceImpl<MpInfoMapper, MpInfo> implements IMpInfoService {

    @Autowired
    private MpInfoMapper mpInfoMapper;
    @Override
    public MpInfo getMicroProgramBasicInfo(String siteName, String miniprogramType) {
        QueryWrapper<MpInfo> mpInfoQueryWrapper = new QueryWrapper<>();
        MpInfo mpInfoParam = new MpInfo();
        mpInfoParam.setSiteName(siteName);
        mpInfoParam.setMiniProgramType(miniprogramType);
        mpInfoQueryWrapper.setEntity(mpInfoParam);
        MpInfo mpInfo= mpInfoMapper.selectOne(mpInfoQueryWrapper);
        return mpInfo;
    }
}
