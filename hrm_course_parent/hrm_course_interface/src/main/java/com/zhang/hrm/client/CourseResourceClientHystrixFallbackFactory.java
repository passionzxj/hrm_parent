package com.zhang.hrm.client;

import com.zhang.hrm.domain.CourseResource;
import com.zhang.hrm.query.CourseResourceQuery;
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
public class CourseResourceClientHystrixFallbackFactory implements FallbackFactory<CourseResourceClient> {

    @Override
    public CourseResourceClient create(Throwable throwable) {
        return new CourseResourceClient() {
            @Override
            public AjaxResult addOrEdit(CourseResource courseResource) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public CourseResource get(Long id) {
                return null;
            }

            @Override
            public List<CourseResource> list() {
                return null;
            }

            @Override
            public PageList<CourseResource> json(CourseResourceQuery query) {
                return null;
            }
        };
    }
}
