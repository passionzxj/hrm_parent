package com.zhang.hrm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhang.hrm.client.FastDfsClient;
import com.zhang.hrm.client.RedisClient;
import com.zhang.hrm.config.RabbitmqConstant;
import com.zhang.hrm.domain.Pager;
import com.zhang.hrm.domain.PagerConfig;
import com.zhang.hrm.domain.Site;
import com.zhang.hrm.dto.CourseTypeDto;
import com.zhang.hrm.mapper.PagerConfigMapper;
import com.zhang.hrm.mapper.PagerMapper;
import com.zhang.hrm.mapper.SiteMapper;
import com.zhang.hrm.service.IPagerConfigService;
import com.zhang.hrm.util.VelocityUtils;
import com.zhang.hrm.util.ZipUtil;
import feign.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
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
        //一:创建静态化页面
        Pager pager = pagerMapper.selectList(new EntityWrapper<Pager>().eq("name", pageName)).get(0);
        String templateUrl = pager.getTemplateUrl();
        String templateName = pager.getTemplateName();
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //1.1通过模板在fastdfs上的路径拿到模板并存放在临时文件夹
            Response response = fastDfsClient.download(templateUrl);
            inputStream = response.body().asInputStream();//从feign的response中拿到输入流
            //1.2拿到当前环境的临时文件夹路径
            String tempPath = System.getProperty("java.io.tmpdir");
            String zipPath = tempPath + "/temp.zip";
            String unZipPath = tempPath + "/temp/";
            outputStream = new FileOutputStream(zipPath);
            IOUtils.copy(inputStream, outputStream);//下载到本地临时文件夹--->zip文件
            ZipUtil.unzip(zipPath, unZipPath);//解压
            //1.3拿到模板
            String templatePath = unZipPath + templateName;//模板的地址
            String templatePagePath = templatePath + ".html";//本地静态页面的地址
            System.out.println("======templatePagePath====="+templatePagePath);//home.vm.html
            //1.4拿到redis中的数据dataKey
            String courseTypes = redisClient.get("courseTypes");
            List<CourseTypeDto> courseTypeDtos = JSONArray.parseArray(courseTypes, CourseTypeDto.class);
            Map<String, Object> map = new HashMap<>();
            map.put("staticRoot", unZipPath);
            map.put("courseTypes", courseTypeDtos);
            //1.5进行页面静态化
            VelocityUtils.staticByTemplate(map, templatePath, templatePagePath);
            //二.上传静态页面到fastfds
            String staticPageUrl = fastDfsClient.upload(new CommonsMultipartFile(createFileItem(new File(templatePagePath), "file")));
            //三.保存pagerConfig
            PagerConfig pagerConfig = new PagerConfig();
            pagerConfig.setTemplateUrl(templateUrl);
            pagerConfig.setTemplateName(templateName);
            pagerConfig.setDataKey(courseTypes);
            pagerConfig.setPhysicalPath(pager.getPhysicalPath());
            pagerConfig.setDfsType(pager.getType());
            pagerConfig.setPageUrl(staticPageUrl);//静态化页面生成之后的路径
            pagerConfig.setPageId(pager.getId());
            pagerConfig.setDataKey(dataKey);
            pagerConfigMapper.insert(pagerConfig);
            //四.发送消息
            //拿到routingKey
            String routingKey = siteMapper.selectList(new EntityWrapper<Site>().eq("id", pager.getSiteId())).get(0).getSn();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fdsType", 0);
            jsonObject.put("staticPageUrl", staticPageUrl);
            jsonObject.put("physicalPath", pager.getPhysicalPath());
            //发送消息到交换机
            rabbitTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_DIRECT_INFORM, routingKey, jsonObject);
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

    private FileItem createFileItem(File file, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fileName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
