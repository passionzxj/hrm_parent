package com.zhang.hrm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.client.FastDfsClient;
import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.config.RabbitmqConfig;
import com.zhang.hrm.domain.Pager;
import com.zhang.hrm.domain.PagerConfig;
import com.zhang.hrm.domain.Site;
import com.zhang.hrm.dto.CourseTypeDto;
import com.zhang.hrm.mapper.PagerConfigMapper;
import com.zhang.hrm.mapper.PagerMapper;
import com.zhang.hrm.mapper.SiteMapper;
import com.zhang.hrm.service.IPagerConfigService;
import com.zhang.hrm.util.FastDfsApiOpr;
import com.zhang.hrm.util.VelocityUtils;
import com.zhang.hrm.util.ZipUtil;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxj
 * @since 2019-09-08
 */
@Service
public class PagerConfigServiceImpl extends ServiceImpl<PagerConfigMapper, PagerConfig> implements IPagerConfigService {

    @Autowired
    private PagerMapper pagerMapper;
    @Autowired
    private PagerConfigMapper pagerConfigMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private FastDfsClient fastDfsClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SiteMapper siteMapper;

    @Override
    public void startStaticPage(String pageName, String dataKey) {
        List<Pager> pagerList = pagerMapper.selectList(new EntityWrapper<Pager>().eq("name", pageName));
        Pager pager = pagerList.get(0);
        String templateUrl = pager.getTemplateUrl();
        String templateName = pager.getTemplateName();
        //拿到redis中的数据dataKey
        String courseTypes = redisClient.get("courseTypes");
        //页面静态化,参数:数据,模板
        Response response = fastDfsClient.download(templateUrl);
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = response.body().asInputStream();//从feign的response中拿到输入流
            outputStream = new FileOutputStream("F://temp.zip");
            IOUtils.copy(inputStream, outputStream);//下载到本地
            ZipUtil.unzip("F://temp.zip", "F://temp/");//解压
            String templatePath = "F://temp/" + templateName;//模板的地址
            String templatePagePath = "F://temp/" + templateName + ".html";//本地静态页面的地址
            String staticRoot = "F://temp/";
            List<CourseTypeDto> courseTypeDtos = JSONArray.parseArray(courseTypes, CourseTypeDto.class);
            Map<String, Object> map = new HashMap<>();
            map.put("staticRoot", staticRoot);
            map.put("courseTypes", courseTypeDtos);
            VelocityUtils.staticByTemplate(map, templatePath, templatePagePath);//进行页面静态化
            String fileName = new File(templatePagePath).getName();
            String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String pageUrl = FastDfsApiOpr.upload(templatePagePath, extName);
            //保存pagerConfig
            PagerConfig pagerConfig = new PagerConfig();
            pagerConfig.setTemplateUrl(templateUrl);
            pagerConfig.setTemplateName(templateName);
            pagerConfig.setDataKey(courseTypes);
            pagerConfig.setPhysicalPath(pager.getPhysicalPath());
            pagerConfig.setDfsType(pager.getType());
            pagerConfig.setPageUrl(pageUrl);//静态化页面生成之后的路径
            pagerConfig.setPageId(pager.getId());
            pagerConfig.setDataKey(dataKey);
            pagerConfigMapper.insert(pagerConfig);
            //拿到routingKey
            String routingKey = siteMapper.selectList(new EntityWrapper<Site>().eq("id", pager.getSiteId())).get(0).getSn();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fdsType", 0);
            jsonObject.put("staticPageUrl", pageUrl);
            jsonObject.put("physicalPath", pager.getPhysicalPath());
            //发送消息到交换机
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_DIRECT_INFORM, routingKey, jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
