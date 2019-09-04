package com.zhang.hrm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhang.hrm.domain.Tenant;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-02
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    //添加中间表
    void saveTenantMeal(List<Map<String, Long>> tenantMeal);

    //删除中间表
    void removeTenantMeal(Serializable id);

}
