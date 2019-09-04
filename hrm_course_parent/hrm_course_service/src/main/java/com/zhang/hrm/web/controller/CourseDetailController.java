package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ICourseDetailService;
import com.zhang.hrm.domain.CourseDetail;
import com.zhang.hrm.query.CourseDetailQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseDetail")
public class CourseDetailController {
    @Autowired
    private ICourseDetailService courseDetailService;

    /**
     * 保存和修改公用的
     * @param courseDetail 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody CourseDetail courseDetail) {
        try {
            if (courseDetail.getCourseId() != null){
                courseDetailService.updateById(courseDetail);
            }else{
                courseDetailService.insert(courseDetail);
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
            courseDetailService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public CourseDetail get(@PathVariable("id") Long id) {
        return courseDetailService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<CourseDetail> list() {
        return courseDetailService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<CourseDetail> json(@RequestBody CourseDetailQuery query) {
        Page<CourseDetail> page = new Page<CourseDetail>(query.getPage(), query.getPageSize());
        page = courseDetailService.selectPage(page);
        return new PageList<CourseDetail>(page.getTotal(), page.getRecords());
    }
}
