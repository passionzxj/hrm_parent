package com.zhang.hrm.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhang.hrm.domain.PagerConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-08
 */
public interface IPagerConfigService extends IService<PagerConfig> {

    void startStaticPage(String pageName,String dataKey);
}
