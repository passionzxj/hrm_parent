package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ITenantTypeService;
import com.zhang.hrm.domain.TenantType;
import com.zhang.hrm.query.TenantTypeQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenantType")
public class TenantTypeController {
    @Autowired
    private ITenantTypeService tenantTypeService;

    /**
     * 保存和修改公用的
     * @param tenantType 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody TenantType tenantType) {
        try {
            if (tenantType.getId() != null){
                tenantTypeService.updateById(tenantType);
            }else{
                tenantTypeService.insert(tenantType);
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
            tenantTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public TenantType get(@PathVariable("id") Long id) {
        return tenantTypeService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<TenantType> list() {
        return tenantTypeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<TenantType> json(@RequestBody TenantTypeQuery query) {
        Page<TenantType> page = new Page<TenantType>(query.getPage(), query.getPageSize());
        page = tenantTypeService.selectPage(page);
        return new PageList<TenantType>(page.getTotal(), page.getRecords());
    }
}
