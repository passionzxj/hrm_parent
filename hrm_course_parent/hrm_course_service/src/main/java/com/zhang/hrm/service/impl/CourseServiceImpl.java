package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.client.EscourseClient;
import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.domain.Course;
import com.zhang.hrm.domain.CourseDetail;
import com.zhang.hrm.mapper.CourseDetailMapper;
import com.zhang.hrm.mapper.CourseMapper;
import com.zhang.hrm.query.CourseQuery;
import com.zhang.hrm.service.ICourseService;
import com.zhang.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private EscourseClient escourseClient;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseDetailMapper courseDetailMapper;

    /**
     * 分页+高级查询+关联对象查询
     *
     * @param query
     * @return
     */
    @Override
    public PageList<Course> selectListPage(CourseQuery query) {
        Page<Course> page = new Page<>(query.getPage(), query.getPageSize());
        List<Course> rows = courseMapper.loadListPage(page, query);
        return new PageList<>(page.getTotal(), rows);
    }

    //添加课程
    @Override
    public boolean insert(Course course) {
        //本身基本信息
        course.setStatus(0);
        courseMapper.insert(course);
        //课程详情,添加一个课程的时候把课程的id赋值给课程明细
        CourseDetail courseDetail = course.getDetail();
        courseDetail.setCourseId(course.getId());
        courseDetailMapper.insert(courseDetail);
        return true;
    }

    /**
     * 批量上线
     * @param ids
     */
    @Override
    @Transactional
    public void batchOnline(Long[] ids) {
        List<Long> idList = (List<Long>) Arrays.asList(ids);
        //把上线的课程在DB中改变状态
        courseMapper.batchOnlineCourse(idList);
        //把上线的课程新添加到ES  index的索引库
        List<Course> courses = courseMapper.selectBatchIds(idList);
        List<Escourse> escourseList = Course2EscourseList(courses);
        escourseClient.batchSave(escourseList);
    }

    /**
     * 批量下线
     * @param ids
     */
    @Override
    @Transactional
    public void batchOffline(Long[] ids) {
        List<Long> idList = (List<Long>) Arrays.asList(ids);
        //把下线的课程在DB中改变状态
        courseMapper.batchOfflineCourse(idList);
        //把下线的课程从ES index库删除
        List<Course> courses = courseMapper.selectBatchIds(idList);
        List<Escourse> escourseList = Course2EscourseList(courses);
        escourseClient.batchDel(escourseList);
    }

    private List<Escourse> Course2EscourseList(List<Course> courses) {
        List<Escourse> list = new ArrayList<>();
        for (Course course : courses) {
            list.add(Course2Escourse(course));
        }
        return list;
    }

    private Escourse Course2Escourse(Course course) {
        Escourse escourse = new Escourse();
//        BeanUtils.copyProperties(course,escourse);
        escourse.setId(course.getId());
        escourse.setName(course.getName());
        escourse.setUsers(course.getUsers());
        escourse.setCourseTypeId(course.getCourseTypeId());
        //type-同库
        if (course.getCourseType() != null)
            escourse.setCourseTypeName(course.getCourseType().getName());
        //跨服务操作
        escourse.setGradeId(course.getGrade());
        escourse.setGradeName(null);
        escourse.setStatus(course.getStatus());
        escourse.setTenantId(course.getTenantId());
        escourse.setTenantName(course.getTenantName());
        escourse.setUserId(course.getUserId());
        escourse.setUserName(course.getUserName());
        escourse.setStartTime(course.getStartTime());
        escourse.setEndTime(course.getEndTime());
        //Detail
        escourse.setIntro(null);
        //resource
        escourse.setResources(null);
        //market
        escourse.setExpires(null);
        escourse.setPrice(null);
        escourse.setPriceOld(null);
        escourse.setQq(null);
        return escourse;
    }
}
