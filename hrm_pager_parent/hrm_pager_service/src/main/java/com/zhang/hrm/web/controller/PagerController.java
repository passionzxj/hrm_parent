package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IPagerService;
import com.zhang.hrm.domain.Pager;
import com.zhang.hrm.query.PagerQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pager")
public class PagerController {
    @Autowired
    private IPagerService pagerService;

    /**
     * 保存和修改公用的
     * @param pager 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Pager pager) {
        try {
            if (pager.getId() != null){
                pagerService.updateById(pager);
            }else{
                pagerService.insert(pager);
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
            pagerService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Pager get(@PathVariable("id") Long id) {
        return pagerService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Pager> list() {
        return pagerService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Pager> json(@RequestBody PagerQuery query) {
        Page<Pager> page = new Page<Pager>(query.getPage(), query.getPageSize());
        page = pagerService.selectPage(page);
        return new PageList<Pager>(page.getTotal(), page.getRecords());
    }
}
