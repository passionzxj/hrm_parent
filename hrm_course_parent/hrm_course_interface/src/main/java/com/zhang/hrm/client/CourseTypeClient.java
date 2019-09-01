package com.zhang.hrm.client;

import com.zhang.hrm.domain.CourseType;
import com.zhang.hrm.query.CourseTypeQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignClientsConfiguration.class,
        fallbackFactory = CourseTypeClientHystrixFallbackFactory.class)
@RequestMapping("/courseType")
public interface CourseTypeClient {
    /**
     * 保存和修改公用的
     * @param courseType 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    AjaxResult addOrEdit(CourseType courseType);

    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    AjaxResult delete(@PathVariable("id") Long id);

    //通过id获取用户
    @GetMapping(value = "{id}")
        CourseType get(@PathVariable("id") Long id);


    /**
     * 查看所有的员工信息
     * @return
     */
    @PatchMapping
    public List<CourseType> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    PageList<CourseType> json(@RequestBody CourseTypeQuery query);
}
