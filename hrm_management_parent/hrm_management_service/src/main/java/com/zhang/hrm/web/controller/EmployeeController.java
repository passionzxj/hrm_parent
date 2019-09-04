package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IEmployeeService;
import com.zhang.hrm.domain.Employee;
import com.zhang.hrm.query.EmployeeQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 保存和修改公用的
     * @param employee 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Employee employee) {
        try {
            if (employee.getId() != null){
                employeeService.updateById(employee);
            }else{
                employeeService.insert(employee);
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
            employeeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Employee get(@PathVariable("id") Long id) {
        return employeeService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Employee> list() {
        return employeeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Employee> json(@RequestBody EmployeeQuery query) {
        Page<Employee> page = new Page<Employee>(query.getPage(), query.getPageSize());
        page = employeeService.selectPage(page);
        return new PageList<Employee>(page.getTotal(), page.getRecords());
    }
}
