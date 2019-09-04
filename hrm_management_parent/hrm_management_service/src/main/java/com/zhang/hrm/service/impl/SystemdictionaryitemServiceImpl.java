package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.domain.Systemdictionary;
import com.zhang.hrm.domain.Systemdictionaryitem;
import com.zhang.hrm.mapper.SystemdictionaryMapper;
import com.zhang.hrm.mapper.SystemdictionaryitemMapper;
import com.zhang.hrm.service.ISystemdictionaryitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-08-31
 */
@Service
public class SystemdictionaryitemServiceImpl extends ServiceImpl<SystemdictionaryitemMapper, Systemdictionaryitem> implements ISystemdictionaryitemService {

    @Autowired
    private SystemdictionaryitemMapper itemMapper;
    @Autowired
    private SystemdictionaryMapper mapper;

    //根据传入的sn查询出所有的数据字典明细
    @Override
    public List<Systemdictionaryitem> selectListBySn(String sn) {
        Wrapper<Systemdictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("sn",sn);
        List<Systemdictionary> systemdictionaries = mapper.selectList(wrapper);
        Systemdictionary systemdictionary = systemdictionaries.get(0);
        Wrapper<Systemdictionaryitem> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("parent_id",systemdictionary.getId());
        List<Systemdictionaryitem> systemdictionaryitems = itemMapper.selectList(wrapper1);
        return systemdictionaryitems;
    }
}
