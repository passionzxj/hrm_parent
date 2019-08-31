package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ISystemdictionaryitemService;
import com.zhang.hrm.domain.Systemdictionaryitem;
import com.zhang.hrm.query.SystemdictionaryitemQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionaryitem")
public class SystemdictionaryitemController {
    @Autowired
    public ISystemdictionaryitemService systemdictionaryitemService;

    /**
     * 保存和修改公用的
     * @param systemdictionaryitem 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Systemdictionaryitem systemdictionaryitem) {
        try {
            if (systemdictionaryitem.getId() != null){
                systemdictionaryitemService.updateById(systemdictionaryitem);
            }else{
                systemdictionaryitemService.insert(systemdictionaryitem);
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
            systemdictionaryitemService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取用户
    @GetMapping(value = "{id}")
    public Systemdictionaryitem get(@PathVariable("id") Long id) {
        return systemdictionaryitemService.selectById(id);
    }

    /**
    * 查看所有的员工信息
    * @return
    */
    @PatchMapping
    public List<Systemdictionaryitem> list() {
        return systemdictionaryitemService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Systemdictionaryitem> json(@RequestBody SystemdictionaryitemQuery query) {
        Page<Systemdictionaryitem> page = new Page<Systemdictionaryitem>(query.getPage(), query.getRows());
        page = systemdictionaryitemService.selectPage(page);
        return new PageList<Systemdictionaryitem>(page.getTotal(), page.getRecords());
    }
}
