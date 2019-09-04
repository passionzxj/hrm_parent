package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IPermissionService;
import com.zhang.hrm.domain.Permission;
import com.zhang.hrm.query.PermissionQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    /**
     * 保存和修改公用的
     * @param permission 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Permission permission) {
        try {
            if (permission.getId() != null){
                permissionService.updateById(permission);
            }else{
                permissionService.insert(permission);
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
            permissionService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Permission get(@PathVariable("id") Long id) {
        return permissionService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Permission> list() {
        return permissionService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Permission> json(@RequestBody PermissionQuery query) {
        Page<Permission> page = new Page<Permission>(query.getPage(), query.getPageSize());
        page = permissionService.selectPage(page);
        return new PageList<Permission>(page.getTotal(), page.getRecords());
    }
}
