package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.Notic;
import com.marc.fmall.mapper.NoticMapper;
import com.marc.fmall.service.INoticService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-04-19
 */
@Service
public class NoticServiceImpl extends ServiceImpl<NoticMapper, Notic> implements INoticService {

    @Autowired
    private NoticMapper noticMapper;
    @Override
    public List<Notic> noticlist() {
        QueryWrapper<Notic> noticQueryWrapper = new QueryWrapper<>();
        Notic querParam = new Notic();
        querParam.setIsShow(1);
        noticQueryWrapper.setEntity(querParam);
        List<Notic> notics = noticMapper.selectList(noticQueryWrapper);
        return notics;
    }

    @Override
    public int insertNotic(Notic notic) {
        notic.setIsShow(1);
        notic.setCreateTime(LocalDateTime.now());
        return noticMapper.insert(notic);
    }

    @Override
    public int deleteNotic(Integer id) {

        return noticMapper.deleteById(id);
    }
}
