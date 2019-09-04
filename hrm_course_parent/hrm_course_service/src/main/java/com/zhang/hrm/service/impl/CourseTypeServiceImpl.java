package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.mapper.CourseTypeMapper;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.service.ICourseTypeService;
import com.zhang.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-01
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    /**
     * 获得分页+高级查询+关联对象
     * @param query
     * @return
     */
    @Override
    public PageList<CourseType> selectPageList(CourseTypeQuery query) {
        Pagination page = new Pagination();
        page.setCurrent(query.getPage());
        page.setSize(query.getPageSize());
        List<CourseType> courseTypes = courseTypeMapper.loadPageList(page,query);
        return new PageList<>(page.getTotal(),courseTypes);
    }
}
