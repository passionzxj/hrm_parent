package com.zhang.hrm.client;

import com.zhang.hrm.domain.Department;
import com.zhang.hrm.query.DepartmentQuery;
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
public class DepartmentClientHystrixFallbackFactory implements FallbackFactory<DepartmentClient> {

    @Override
    public DepartmentClient create(Throwable throwable) {
        return new DepartmentClient() {
            @Override
            public AjaxResult addOrEdit(Department department) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Department get(Long id) {
                return null;
            }

            @Override
            public List<Department> list() {
                return null;
            }

            @Override
            public PageList<Department> json(DepartmentQuery query) {
                return null;
            }
        };
    }
}
