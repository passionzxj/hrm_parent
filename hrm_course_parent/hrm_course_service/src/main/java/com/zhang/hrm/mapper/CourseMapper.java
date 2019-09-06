package com.zhang.hrm.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhang.hrm.domain.Course;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhang.hrm.query.CourseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-03
 */
public interface CourseMapper extends BaseMapper<Course> {

    //添加课程的时候添加课程类型
    void saveCourseType(Long id);

    //分页+高级查询+关联对象查询
    List<Course> loadListPage(Page<Course> page, @Param("query") CourseQuery query);

    //根据前台传的id数组批量修改相应对象的状态
    void batchOnlineCourse(List<Long> idList);

    void batchOfflineCourse(List<Long> idList);
}
