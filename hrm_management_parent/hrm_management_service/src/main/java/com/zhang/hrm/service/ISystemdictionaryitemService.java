package com.zhang.hrm.service;

import com.zhang.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangxj
 * @since 2019-08-31
 */
public interface ISystemdictionaryitemService extends IService<Systemdictionaryitem> {

    List<Systemdictionaryitem> selectListBySn(String sn);
}
