package com.marc.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marc.fmall.entity.UmsMember;
import com.marc.fmall.mapper.UmsMemberMapper;
import com.marc.fmall.service.IUmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Override
    public UmsMember getCurrentMember(String openid) {
        QueryWrapper<UmsMember> umsMemberQueryWrapper = new QueryWrapper<>();
        UmsMember queryParam = new UmsMember();
        queryParam.setOpenId(openid);
        umsMemberQueryWrapper.setEntity(queryParam);
        UmsMember umsMember = umsMemberMapper.selectOne(umsMemberQueryWrapper);
        return umsMember;
    }
}
