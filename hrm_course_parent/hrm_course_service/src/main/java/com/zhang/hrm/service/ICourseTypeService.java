package com.zhang.hrm.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.util.PageList;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-01
 */
public interface ICourseTypeService extends IService<CourseType> {

    PageList<CourseType> selectPageList(CourseTypeQuery query);

    List<CourseType> selectCourseTypeTree(Long pid);

    void initCourseSiteIndex();
}
