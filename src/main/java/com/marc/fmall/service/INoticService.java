package com.marc.fmall.service;

import com.marc.fmall.entity.Notic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author marc
 * @since 2021-04-19
 */
public interface INoticService extends IService<Notic> {
    /**
     * 通告列表
     * @return
     */
    List<Notic> noticlist();

    /**
     * 新增通告
     * @param notic
     * @return
     */
    int insertNotic(Notic notic);

    /**
     * 删除通告
     * @return
     */
    int deleteNotic(Integer id);
}
