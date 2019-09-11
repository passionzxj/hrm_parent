package com.zhang.hrm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.cache.CourseTypeCache;
import com.zhang.hrm.client.PagerConfigClient;
import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.mapper.CourseTypeMapper;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.service.ICourseTypeService;
import com.zhang.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
    @Autowired
    private CourseTypeCache cache;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PagerConfigClient pagerConfigClient;

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
        //先尝试从缓存中拿数据
        List<CourseType> cacheCourseTypes = cache.getCourseTypes();
        //缓存中没有数据,返回从数据库中拿到的数据,同时把数据添加到缓存
        if (cacheCourseTypes == null || cacheCourseTypes.size() < 1) {
            System.out.println("从db中拿数据........");
            List<CourseType> courseTypesDb = getCourseTypesCycle(pid);
            //解决缓存穿透的问题,如果查出的没有,则设置一个空的数据到redis中,并设置过期时间
            if (courseTypesDb == null || courseTypesDb.size() < 1) {
                courseTypesDb = new ArrayList<>();
            }
            cache.setCourseTypes(courseTypesDb);
            return courseTypesDb;
        }
        //缓存中有数据,直接返回数据
        System.out.println("缓存中直接返回数据......");
        return cacheCourseTypes;
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

    /**
     * 对数据做增删改的时候要同步到缓存
     */
    private void setDataToCache() {
        List<CourseType> courseTypes = getCourseTypesCycle(0L);
        cache.setCourseTypes(courseTypes);
    }

    @Override
    public boolean insert(CourseType entity) {
        courseTypeMapper.insert(entity);
        setDataToCache();
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        courseTypeMapper.deleteById(id);
        setDataToCache();
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        courseTypeMapper.updateById(entity);
        setDataToCache();
        return true;
    }

    /**
     *初始化静态页面
     */
    @Override
    public void initCourseSiteIndex() {
        //前台上传模板zip文件到fastdfs
        //把数据存到redis,有就直接拿,没有就数据库拿
        List<CourseType> courseTypes = selectCourseTypeTree(0L);
        String courseTypesStr = JSONArray.toJSONString(courseTypes);
        String dataKey = "courseTypes";
        redisClient.set(dataKey, courseTypesStr);
        //调用静态化页面服务接口产生静态页面并上传到fastdfs
        String pageName = "CourseIndex";
        Map<String, String> map = new HashMap<>();
        map.put("pageName",pageName);
        map.put("dataKey",dataKey);
        pagerConfigClient.startStaticPage(map);
        //把页面放入消息队列中,让pageAgent接收到消息并下载
        //在静态化服务中做

    }
}
