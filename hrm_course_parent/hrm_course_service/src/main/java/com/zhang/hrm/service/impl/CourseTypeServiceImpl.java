package com.zhang.hrm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.mapper.CourseTypeMapper;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.service.ICourseTypeService;
import com.zhang.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param query
     * @return
     */
    @Override
    public PageList<CourseType> selectPageList(CourseTypeQuery query) {
        Pagination page = new Pagination();
        page.setCurrent(query.getPage());
        page.setSize(query.getPageSize());
        List<CourseType> courseTypes = courseTypeMapper.loadPageList(page, query);
        return new PageList<>(page.getTotal(), courseTypes);
    }

    //获得菜单树
    @Override
    public List<CourseType> selectCourseTypeTree(Long pid) {
//        return getCourseTypesCycle(pid);
        return getCourseTypesRecursion(pid);

    }

    //递归的方式(不采用,发很多sql)
    private List<CourseType> getCourseTypesRecursion(Long pid) {
        List<CourseType> children = courseTypeMapper.selectList(new EntityWrapper<CourseType>().eq("pid", pid));
        if (children == null || children.size() < 1) {
            return null;
        }
        for (CourseType child : children) {
            List<CourseType> courseTypes = getCourseTypesRecursion(child.getId());
            child.setChildren(courseTypes);
        }
        return children;
    }

    //循环的方式(推荐)
    private List<CourseType> getCourseTypesCycle(Long pid) {
        List<CourseType> result = new ArrayList<>();
        List<CourseType> allTypes = courseTypeMapper.selectList(null);
        Map<Long, CourseType> map = new HashMap<>();
        for (CourseType type : allTypes) {
            map.put(type.getId(), type);
        }
        for (CourseType courseType : allTypes) {
            Long pidTem = courseType.getPid();//临时pid
            if (pidTem.longValue() == pid.longValue()) {
                result.add(courseType);
            } else {
                CourseType parent = map.get(pidTem);
                if (parent != null)
                    parent.getChildren().add(courseType);
            }
        }
        return result;
    }
}
