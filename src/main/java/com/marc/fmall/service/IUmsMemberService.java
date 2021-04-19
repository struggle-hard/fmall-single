package com.marc.fmall.service;

import com.marc.fmall.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

import java.lang.reflect.Member;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
public interface IUmsMemberService extends IService<UmsMember> {

    /**
     * 根据openid获取当前登录用户
     * @param openid
     * @return
     */
    UmsMember getCurrentMember(String openid);
}
