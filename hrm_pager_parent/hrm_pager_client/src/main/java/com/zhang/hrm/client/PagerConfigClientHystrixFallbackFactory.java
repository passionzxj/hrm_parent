package com.zhang.hrm.client;

import com.zhang.hrm.domain.PagerConfig;
import com.zhang.hrm.query.PagerConfigQuery;
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
public class PagerConfigClientHystrixFallbackFactory implements FallbackFactory<PagerConfigClient> {

    @Override
    public PagerConfigClient create(Throwable throwable) {
        return new PagerConfigClient() {
            @Override
            public AjaxResult addOrEdit(PagerConfig pagerConfig) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public PagerConfig get(Long id) {
                return null;
            }

            @Override
            public List<PagerConfig> list() {
                return null;
            }

            @Override
            public PageList<PagerConfig> json(PagerConfigQuery query) {
                return null;
            }

            @Override
            public AjaxResult startStaticPage(Map<String, String> map) {
                return null;
            }


        };
    }
}
