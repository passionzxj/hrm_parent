package com.zhang.hrm.client;

import com.zhang.hrm.domain.Site;
import com.zhang.hrm.query.SiteQuery;
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
public class SiteClientHystrixFallbackFactory implements FallbackFactory<SiteClient> {

    @Override
    public SiteClient create(Throwable throwable) {
        return new SiteClient() {
            @Override
            public AjaxResult addOrEdit(Site site) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Site get(Long id) {
                return null;
            }

            @Override
            public List<Site> list() {
                return null;
            }

            @Override
            public PageList<Site> json(SiteQuery query) {
                return null;
            }
        };
    }
}
