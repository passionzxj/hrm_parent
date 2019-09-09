package com.zhang.hrm.client;

import com.zhang.hrm.domain.Pager;
import com.zhang.hrm.query.PagerQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignClientsConfiguration.class,
        fallbackFactory = PagerClientHystrixFallbackFactory.class)
@RequestMapping("/pager")
public interface PagerClient {

    /**
     * 保存和修改公用的
     * @param pager 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    AjaxResult addOrEdit(Pager pager);

    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    AjaxResult delete(@PathVariable("id") Long id);

    //通过id获取用户
    @GetMapping(value = "{id}")
        Pager get(@PathVariable("id") Long id);

    /**
     * 查看所有的员工信息
     * @return
     */
    @PatchMapping
    List<Pager> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    PageList<Pager> json(@RequestBody PagerQuery query);
}
