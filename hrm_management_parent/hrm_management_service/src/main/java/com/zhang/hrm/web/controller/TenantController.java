package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ITenantService;
import com.zhang.hrm.domain.Tenant;
import com.zhang.hrm.query.TenantQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private ITenantService tenantService;

    /**
     * 保存和修改公用的
     * @param tenant 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Tenant tenant) {
        try {
            if (tenant.getId() != null){
                tenantService.updateById(tenant);
            }else{
                tenantService.insert(tenant);
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
            tenantService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Tenant get(@PathVariable("id") Long id) {
        return tenantService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Tenant> list() {
        return tenantService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Tenant> json(@RequestBody TenantQuery query) {
        Page<Tenant> page = new Page<Tenant>(query.getPage(), query.getPageSize());
        page = tenantService.selectPage(page);
        return new PageList<Tenant>(page.getTotal(), page.getRecords());
    }
}
