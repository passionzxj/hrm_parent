package com.zhang.hrm.client;

import com.zhang.hrm.domain.Meal;
import com.zhang.hrm.query.MealQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangxj
 * @date 2018/10/8-16:18
 */
@Component
public class MealClientHystrixFallbackFactory implements FallbackFactory<MealClient> {

    @Override
    public MealClient create(Throwable throwable) {
        return new MealClient() {
            @Override
            public AjaxResult addOrEdit(Meal meal) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Meal get(Long id) {
                return null;
            }

            @Override
            public List<Meal> list() {
                return null;
            }

            @Override
            public PageList<Meal> json(MealQuery query) {
                return null;
            }
        };
    }
}
