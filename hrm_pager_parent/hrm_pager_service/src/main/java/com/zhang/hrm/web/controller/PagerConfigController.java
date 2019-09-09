package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IPagerConfigService;
import com.zhang.hrm.domain.PagerConfig;
import com.zhang.hrm.query.PagerConfigQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagerConfig")
public class PagerConfigController {
    @Autowired
    private IPagerConfigService pagerConfigService;

    /**
     * 保存和修改公用的
     * @param pagerConfig 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody PagerConfig pagerConfig) {
        try {
            if (pagerConfig.getId() != null){
                pagerConfigService.updateById(pagerConfig);
            }else{
                pagerConfigService.insert(pagerConfig);
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
            pagerConfigService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public PagerConfig get(@PathVariable("id") Long id) {
        return pagerConfigService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<PagerConfig> list() {
        return pagerConfigService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<PagerConfig> json(@RequestBody PagerConfigQuery query) {
        Page<PagerConfig> page = new Page<PagerConfig>(query.getPage(), query.getPageSize());
        page = pagerConfigService.selectPage(page);
        return new PageList<PagerConfig>(page.getTotal(), page.getRecords());
    }
}
