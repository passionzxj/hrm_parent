package com.zhang.hrm.client;

import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.query.CourseTypeQuery;
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
public class CourseTypeClientHystrixFallbackFactory implements FallbackFactory<CourseTypeClient> {

    @Override
    public CourseTypeClient create(Throwable throwable) {
        return new CourseTypeClient() {
            @Override
            public AjaxResult addOrEdit(CourseType courseType) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public CourseType get(Long id) {
                return null;
            }

            @Override
            public List<CourseType> list() {
                return null;
            }

            @Override
            public PageList<CourseType> json(CourseTypeQuery query) {
                return null;
            }
        };
    }
}
