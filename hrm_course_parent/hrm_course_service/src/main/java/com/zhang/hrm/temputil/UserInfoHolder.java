package com.zhang.hrm.temputil;

import com.zhang.hrm.domain.Employee;
import com.zhang.hrm.domain.Tenant;

/**
 * 作为临时的数据,返回机构和创建用户
 * 后面直接从前台获取
 */
public class UserInfoHolder {
    public static Tenant getTenant() {
        Tenant currentUserTenant = new Tenant();
        currentUserTenant.setId(24L);
        currentUserTenant.setCompanyName("建新商贸");
        return currentUserTenant;
    }
    public static Employee getCurrentUser() {
        Employee currentUser = new Employee();
        currentUser.setId(45L);
        currentUser.setUsername("张新建");
        return currentUser;
    }
}
