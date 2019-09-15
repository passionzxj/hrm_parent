package com.zhang.hrm.client;

import com.zhang.hrm.doc.Escourse;
import com.zhang.hrm.query.EscourseQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangxj
 * @date 2018/10/8-16:18
 */
@Component
public class EscourseClientHystrixFallbackFactory implements FallbackFactory<EscourseClient> {

    @Override
    public EscourseClient create(Throwable throwable) {
        return new EscourseClient() {
            @Override
            public PageList<Map<String, Object>> esQuery(Map<String, Object> query) {
                return null;
            }

            @Override
            public AjaxResult addOrEdit(Escourse escourse) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Escourse get(Long id) {
                return null;
            }

            @Override
            public List<Escourse> list() {
                return null;
            }

            @Override
            public PageList<Escourse> json(EscourseQuery query) {
                return null;
            }

            @Override
            public AjaxResult batchSave(List<Escourse> esList) {
                return null;
            }

            @Override
            public AjaxResult batchDel(List<Escourse> escourseList) {
                return null;
            }

        };
    }
}
