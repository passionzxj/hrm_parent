package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.ISiteService;
import com.zhang.hrm.domain.Site;
import com.zhang.hrm.query.SiteQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private ISiteService siteService;

    /**
     * 保存和修改公用的
     * @param site 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Site site) {
        try {
            if (site.getId() != null){
                siteService.updateById(site);
            }else{
                siteService.insert(site);
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
            siteService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Site get(@PathVariable("id") Long id) {
        return siteService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Site> list() {
        return siteService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Site> json(@RequestBody SiteQuery query) {
        Page<Site> page = new Page<Site>(query.getPage(), query.getPageSize());
        page = siteService.selectPage(page);
        return new PageList<Site>(page.getTotal(), page.getRecords());
    }
}
