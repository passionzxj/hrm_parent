package com.zhang.hrm.web.controller;

import com.zhang.hrm.service.IMealService;
import com.zhang.hrm.domain.Meal;
import com.zhang.hrm.query.MealQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Autowired
    private IMealService mealService;

    /**
     * 保存和修改公用的
     * @param meal 传递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Meal meal) {
        try {
            if (meal.getId() != null){
                mealService.updateById(meal);
            }else{
                mealService.insert(meal);
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
            mealService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Meal get(@PathVariable("id") Long id) {
        return mealService.selectById(id);
    }

    /**
    * 查看所有的对象信息
    * @return
    */
    @PatchMapping
    public List<Meal> list() {
        return mealService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Meal> json(@RequestBody MealQuery query) {
        Page<Meal> page = new Page<Meal>(query.getPage(), query.getPageSize());
        page = mealService.selectPage(page);
        return new PageList<Meal>(page.getTotal(), page.getRecords());
    }
}
