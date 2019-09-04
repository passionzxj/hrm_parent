package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.domain.Course;
import com.zhang.hrm.domain.CourseDetail;
import com.zhang.hrm.mapper.CourseDetailMapper;
import com.zhang.hrm.mapper.CourseMapper;
import com.zhang.hrm.query.CourseQuery;
import com.zhang.hrm.service.ICourseService;
import com.zhang.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private  CourseMapper courseMapper;

    @Autowired
    private CourseDetailMapper courseDetailMapper;

    /**
     * 分页+高级查询+关联对象查询
     * @param query
     * @return
     */
    @Override
    public PageList<Course> selectListPage(CourseQuery query) {
        Page<Course> page = new Page<>(query.getPage(),query.getPageSize());
        List<Course> rows =  courseMapper.loadListPage(page,query);
        return new PageList<>(page.getTotal(),rows);
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @Override
    public boolean insert(Course course) {
        System.out.println("========="+course.getCourseType().getId());
        course.setCourseTypeId(course.getCourseType().getId());
        //本身基本信息
        course.setStatus(0);
        courseMapper.insert(course);
        //课程详情,添加一个课程的时候把课程的id赋值给课程明细
        CourseDetail detail = course.getDetail();
        detail.setCourseId(course.getId());
        courseDetailMapper.insert(detail);
        return true;
    }


}
