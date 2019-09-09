package com.zhang.hrm.web.controller;

import com.zhang.hrm.domain.Course;
import com.zhang.hrm.query.CourseQuery;
import com.zhang.hrm.service.ICourseService;
import com.zhang.hrm.temputil.UserInfoHolder;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private ICourseService courseService;

    /**
     * 保存和修改公用的
     *
     * @param course 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Course course) {
        try {
            //userId,userName,tenantId,tenantName
            course.setUserId(UserInfoHolder.getCurrentUser().getId());
            course.setUserName(UserInfoHolder.getCurrentUser().getUsername());
            course.setTenantId(UserInfoHolder.getTenant().getId());
            course.setTenantName(UserInfoHolder.getTenant().getCompanyName());
            if (course.getId() != null) {
                courseService.updateById(course);
            } else {
                courseService.insert(course);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            courseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "/{id}")
    public Course get(@PathVariable("id") Long id) {
        return courseService.selectById(id);
    }

    /**
     * 查看所有的对象信息
     *
     * @return
     */
    @PatchMapping
    public List<Course> list() {
        return courseService.selectList(null);
    }

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    public PageList<Course> json(@RequestBody CourseQuery query) {
        return courseService.selectListPage(query);
    }

    /**
     * 批量上线课程
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/online")
    public AjaxResult online(@RequestBody Long[] ids) {
        List<Long> longList = (List<Long>) Arrays.asList(ids);
        //已经上线了的课程不允许再上线
        List<Long> idsTem = new ArrayList<>();
        try {
            for (Long aLong : longList) {
                if (courseService.selectById(aLong).getStatus() == 0) {
                    idsTem.add(aLong);
                }
            }
            Long[] longs = idsTem.toArray(new Long[]{});
            if (longs.length < 1) {
                return AjaxResult.me().setSuccess(false).setMessage("上线失败:已经上线的课程不允许再上线!!");
            }
            courseService.batchOnline(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上线失败:" + e);
            return AjaxResult.me().setSuccess(false).setMessage("上线失败:" + e.getMessage());
        }
    }

    /**
     * 批量下线课程
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/offline")
    public AjaxResult offline(@RequestBody Long[] ids) {
        List<Long> longList = (List<Long>) Arrays.asList(ids);
        //已经下线了的课程不允许再下线
        List<Long> idsTem = new ArrayList<>();
        try {
            for (Long aLong : longList) {
                if (courseService.selectById(aLong).getStatus() == 1) {
                    idsTem.add(aLong);
                }
            }
            Long[] longs = idsTem.toArray(new Long[]{});
            if (longs.length < 1) {
                return AjaxResult.me().setSuccess(false).setMessage("下线失败:已经上线的课程不允许再下线!!");
            }
            courseService.batchOffline(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下线失败了:" + e);
            return AjaxResult.me().setSuccess(false).setMessage("下线失败了:" + e.getMessage());
        }
    }
}
