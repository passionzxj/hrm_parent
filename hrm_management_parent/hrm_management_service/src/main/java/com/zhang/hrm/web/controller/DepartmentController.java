package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IDepartmentService;
import com.zhang.hrm.domain.Department;
import com.zhang.hrm.query.DepartmentQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    /**
     * 保存和修改公用的
     * @param department 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Department department) {
        try {
            if (department.getId() != null){
                departmentService.updateById(department);
            }else{
                departmentService.insert(department);
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
            departmentService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Department get(@PathVariable("id") Long id) {
        return departmentService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Department> list() {
        return departmentService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Department> json(@RequestBody DepartmentQuery query) {
        Page<Department> page = new Page<Department>(query.getPage(), query.getPageSize());
        page = departmentService.selectPage(page);
        return new PageList<Department>(page.getTotal(), page.getRecords());
    }
}
