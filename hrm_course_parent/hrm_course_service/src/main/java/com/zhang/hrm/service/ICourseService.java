package com.zhang.hrm.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhang.hrm.domain.Course;
import com.zhang.hrm.query.CourseQuery;
import com.zhang.hrm.util.PageList;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-03
 */
public interface ICourseService extends IService<Course> {

    PageList<Course> selectListPage(CourseQuery query);

    void batchOnline(Long[] ids);

    void batchOffline(Long[] ids);

    PageList<Map<String, Object>> courseList(Map<String, Object> query);
}
