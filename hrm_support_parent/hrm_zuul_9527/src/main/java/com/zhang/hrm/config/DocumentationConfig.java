package com.zhang.hrm.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        //通过网关访问服务地址
        resources.add(swaggerResource("系统管理", "/services/management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("课程中心", "/services/course/v2/api-docs", "2.0"));
        resources.add(swaggerResource("分布式文件系统", "/services/fastdfs/v2/api-docs", "2.0"));
        resources.add(swaggerResource("ES全文检索", "/services/es/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Redis支持", "/services/redis/v2/api-docs", "2.0"));
        resources.add(swaggerResource("页面管理", "/services/pager/v2/api-docs", "2.0"));
        resources.add(swaggerResource("消息队列管理", "/services/rabbit/v2/api-docs", "2.0"));
        resources.add(swaggerResource("用户中心管理", "/services/user/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}