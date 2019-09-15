package com.zhang.hrm.client;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "HRM-ES", configuration = FeignClientsConfiguration.class,
        fallbackFactory = EscourseClientHystrixFallbackFactory.class)
@RequestMapping("/esCourse")
public interface EscourseClient {

    @PostMapping("/esQuery")
    PageList<Map<String, Object>> esQuery(Map<String, Object> query);
    /**
     * 保存和修改公用的
     *
     * @param escourse 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    AjaxResult addOrEdit(Escourse escourse);

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    AjaxResult delete(@PathVariable("id") Long id);

    //通过id获取用户
    @GetMapping(value = "{id}")
    Escourse get(@PathVariable("id") Long id);

    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @PatchMapping
    List<Escourse> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    PageList<Escourse> json(@RequestBody EscourseQuery query);

    @PostMapping(value = "/online")
    AjaxResult batchSave(List<Escourse> esList);

    @PostMapping(value = "/offline")
    AjaxResult batchDel(List<Escourse> esList);
}
