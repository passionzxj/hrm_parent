package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ICourseResourceService;
import com.zhang.hrm.domain.CourseResource;
import com.zhang.hrm.query.CourseResourceQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseResource")
public class CourseResourceController {
    @Autowired
    private ICourseResourceService courseResourceService;

    /**
     * 保存和修改公用的
     * @param courseResource 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody CourseResource courseResource) {
        try {
            if (courseResource.getCourseId() != null){
                courseResourceService.updateById(courseResource);
            }else{
                courseResourceService.insert(courseResource);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            courseResourceService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public CourseResource get(@PathVariable("id") Long id) {
        return courseResourceService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<CourseResource> list() {
        return courseResourceService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<CourseResource> json(@RequestBody CourseResourceQuery query) {
        Page<CourseResource> page = new Page<CourseResource>(query.getPage(), query.getPageSize());
        page = courseResourceService.selectPage(page);
        return new PageList<CourseResource>(page.getTotal(), page.getRecords());
    }
}
