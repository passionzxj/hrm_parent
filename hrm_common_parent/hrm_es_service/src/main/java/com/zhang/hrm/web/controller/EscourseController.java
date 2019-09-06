package com.zhang.hrm.web.controller;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.service.IEscourseService;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esCourse")
public class EscourseController {

    private Logger logger = LoggerFactory.getLogger(EscourseController.class);
    @Autowired
    private IEscourseService escourseService;

    /**
     * 保存和修改公用的
     *
     * @param escourse 线递的实体
     * @return AjaxResult转换结果
     */
    @PutMapping
    public AjaxResult addOrEdit(@RequestBody Escourse escourse) {
        try {
            if (escourse.getId() != null) {
                escourseService.updateById(escourse);
            } else {
                escourseService.insert(escourse);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            escourseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //通过id获取对象
    @GetMapping(value = "{id}")
    public Escourse get(@PathVariable("id") Long id) {
        return escourseService.selectById(id);
    }

    /**
     * 查看所有的对象信息
     *
     * @return
     */
    @PatchMapping
    public List<Escourse> list() {
        return escourseService.selectList(null);
    }

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @PostMapping
    public PageList<Escourse> json(@RequestBody EscourseQuery query) {
        return escourseService.selectListPage(query);
    }

    /**
     * 课程上线保存到index库
     * 需要线入List的Escourse对象
     * @param esList
     * @return
     */
    @PostMapping(value = "/online")
    public AjaxResult batchSave(@RequestBody List<Escourse> esList) {
        try {
            escourseService.batchOnline(esList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上线失败了:" + e);
            return AjaxResult.me().setSuccess(false).setMessage("上线失败了:" + e.getMessage());
        }
    }

    /**
     * 下线课程
     * @param esList
     * @return
     */
    @PostMapping(value = "/offline")
    public AjaxResult batchDel(@RequestBody List<Escourse> esList) {
        try {
            escourseService.batchOffline(esList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下线失败了:" + e);
            return AjaxResult.me().setSuccess(false).setMessage("下线失败了:" + e.getMessage());
        }
    }
}
