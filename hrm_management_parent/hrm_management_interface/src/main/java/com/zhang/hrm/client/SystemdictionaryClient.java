package com.zhang.hrm.client;

import com.zhang.hrm.domain.Systemdictionary;
import com.zhang.hrm.query.SystemdictionaryQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignClientsConfiguration.class,
        fallbackFactory = SystemdictionaryClientHystrixFallbackFactory.class)
@RequestMapping("/systemdictionary")
public interface SystemdictionaryClient {
    /**
     * 保存和修改公用的
     * @param systemdictionary 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    AjaxResult addOrEdit(Systemdictionary systemdictionary);

    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    AjaxResult delete(@PathVariable("id") Long id);

    //通过id获取用户
    @GetMapping(value = "{id}")
        Systemdictionary get(@PathVariable("id") Long id);


    /**
     * 查看所有的员工信息
     * @return
     */
    @PatchMapping
    public List<Systemdictionary> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    PageList<Systemdictionary> json(@RequestBody SystemdictionaryQuery query);
}
