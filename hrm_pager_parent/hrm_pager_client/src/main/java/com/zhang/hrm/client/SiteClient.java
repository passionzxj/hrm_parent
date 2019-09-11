package com.zhang.hrm.client;

import com.zhang.hrm.domain.Site;
import com.zhang.hrm.query.SiteQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "HRM-PAGER", configuration = FeignClientsConfiguration.class,
        fallbackFactory = SiteClientHystrixFallbackFactory.class)
@RequestMapping("/site")
public interface SiteClient {

    /**
     * 保存和修改公用的
     * @param site 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    AjaxResult addOrEdit(Site site);

    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    AjaxResult delete(@PathVariable("id") Long id);

    //通过id获取用户
    @GetMapping(value = "{id}")
        Site get(@PathVariable("id") Long id);

    /**
     * 查看所有的员工信息
     * @return
     */
    @PatchMapping
    List<Site> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    PageList<Site> json(@RequestBody SiteQuery query);
}
