package com.zhang.hrm.client;

import com.zhang.hrm.domain.Systemdictionaryitem;
import com.zhang.hrm.query.SystemdictionaryitemQuery;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yaohuaipeng
 * @date 2018/10/8-16:18
 */
@Component
public class SystemdictionaryitemClientHystrixFallbackFactory implements FallbackFactory<SystemdictionaryitemClient> {

    @Override
    public SystemdictionaryitemClient create(Throwable throwable) {
        return new SystemdictionaryitemClient() {
            @Override
            public AjaxResult addOrEdit(Systemdictionaryitem systemdictionaryitem) {
                return null;
            }

            @Override
            public AjaxResult delete(Long id) {
                return null;
            }

            @Override
            public Systemdictionaryitem get(Long id) {
                return null;
            }

            @Override
            public List<Systemdictionaryitem> list() {
                return null;
            }

            @Override
            public PageList<Systemdictionaryitem> json(SystemdictionaryitemQuery query) {
                return null;
            }
        };
    }
}
