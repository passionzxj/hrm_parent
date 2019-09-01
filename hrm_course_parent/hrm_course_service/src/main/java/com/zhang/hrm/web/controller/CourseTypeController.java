package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ICourseTypeService;
import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseType")
public class CourseTypeController {

    @Autowired
    public ICourseTypeService courseTypeService;

    /**
     * 保存和修改公用的
     * @param courseType 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody CourseType courseType) {
        try {
            if (courseType.getId() != null){
                courseTypeService.updateById(courseType);
            }else{
                courseTypeService.insert(courseType);
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
            courseTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取用户
    @GetMapping(value = "{id}")
    public CourseType get(@PathVariable("id") Long id) {
        return courseTypeService.selectById(id);
    }

    /**
    * 查看所有的员工信息
    * @return
    */
    @PatchMapping
    public List<CourseType> list() {
        return courseTypeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<CourseType> json(@RequestBody CourseTypeQuery query) {
        Page<CourseType> page = new Page<CourseType>(query.getPage(), query.getRows());
        page = courseTypeService.selectPage(page);
        return new PageList<CourseType>(page.getTotal(), page.getRecords());
    }
}