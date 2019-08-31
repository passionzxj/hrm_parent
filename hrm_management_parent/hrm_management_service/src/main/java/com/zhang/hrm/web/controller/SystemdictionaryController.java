package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ISystemdictionaryService;
import com.zhang.hrm.domain.Systemdictionary;
import com.zhang.hrm.query.SystemdictionaryQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionary")
public class SystemdictionaryController {
    @Autowired
    public ISystemdictionaryService systemdictionaryService;

    /**
     * 保存和修改公用的
     * @param systemdictionary 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Systemdictionary systemdictionary) {
        try {
            if (systemdictionary.getId() != null){
                systemdictionaryService.updateById(systemdictionary);
            }else{
                systemdictionaryService.insert(systemdictionary);
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
            systemdictionaryService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取用户
    @GetMapping(value = "{id}")
    public Systemdictionary get(@PathVariable("id") Long id) {
        return systemdictionaryService.selectById(id);
    }

    /**
    * 查看所有的员工信息
    * @return
    */
    @PatchMapping
    public List<Systemdictionary> list() {
        return systemdictionaryService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Systemdictionary> json(@RequestBody SystemdictionaryQuery query) {
        Page<Systemdictionary> page = new Page<Systemdictionary>(query.getPage(), query.getRows());
        page = systemdictionaryService.selectPage(page);
        return new PageList<Systemdictionary>(page.getTotal(), page.getRecords());
    }
}
