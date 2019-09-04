package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.domain.Employee;
import com.zhang.hrm.domain.Tenant;
import com.zhang.hrm.mapper.EmployeeMapper;
import com.zhang.hrm.mapper.TenantMapper;
import com.zhang.hrm.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-02
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public boolean insert(Tenant tenant) {
        //添加机构类型
        System.out.println(tenant.getTenantType());
        tenant.setTenantTypeId(tenant.getTenantType().getId());
        //添加租户本身基本信息
        tenantMapper.insert(tenant);
        System.out.println("添加的时候有没有返回主键:" + tenant.getId());
        //添加租户管理员信息
        Employee adminUser = tenant.getAdminUser();
        adminUser.setTenantId(tenant.getId());//把租户的id赋值给管理员的所属租户id
        employeeMapper.insert(adminUser);
        //添加中间表的信息
        tenantMapper.saveTenantMeal(tenant.getTenantMeal());
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        tenantMapper.deleteById(id);
        //Wrapper根据条件删除管理员
        Wrapper<Employee> wrapper = new EntityWrapper<>();
        wrapper.eq("tenant_id", id);
        employeeMapper.delete(wrapper);
        tenantMapper.removeTenantMeal(id);
        return true;
    }

    @Override
    public boolean updateById(Tenant tenant) {
        tenantMapper.updateById(tenant);
        employeeMapper.updateById(tenant.getAdminUser());
        //中间表先删除再添加
        tenantMapper.removeTenantMeal(tenant.getId());
        tenantMapper.saveTenantMeal(tenant.getTenantMeal());
        return true;
    }
}
