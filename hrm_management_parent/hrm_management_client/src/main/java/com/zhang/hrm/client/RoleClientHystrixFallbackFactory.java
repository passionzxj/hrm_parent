package com.zhang.hrm.client;

import com.zhang.hrm.domain.Role;
import com.zhang.hrm.query.RoleQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangxj
 * @date 2018/10/8-16:18
 */
@Component
public class RoleClientHystrixFallbackFactory implements FallbackFactory<RoleClient> {

    @Override
    public RoleClient create(Throwable throwable) {
        return new RoleClient() {
            @Override
            public AjaxResult addOrEdit(Role role) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Role get(Long id) {
                return null;
            }

            @Override
            public List<Role> list() {
                return null;
            }

            @Override
            public PageList<Role> json(RoleQuery query) {
                return null;
            }
        };
    }
}
