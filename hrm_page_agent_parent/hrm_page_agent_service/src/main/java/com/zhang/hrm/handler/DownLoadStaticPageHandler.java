package com.zhang.hrm.handler;


import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhang.hrm.client.FastDfsClient;
import com.zhang.hrm.config.RabbitmqConstant;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DownLoadStaticPageHandler {

    @Autowired
    private FastDfsClient fastDfsClient;

    @RabbitListener(queues = RabbitmqConstant.HRM_COMMON_QUEUE)
    public void receiveMsg(String msg, Message message, Channel channel) {
        //拿到的msg是一个json字符串
        //包含fdsType,staticPageUrl,physicalPath
        JSONObject jsonObject = JSONObject.parseObject(msg);
        //分别获得msg其中的值
        Integer fdsType = jsonObject.getInteger("fdsType");
        String staticPageUrl = jsonObject.getString("staticPageUrl");
        String physicalPath = jsonObject.getString("physicalPath");

        //根据fds的类型把模板下载下来然就把静态页面拷贝到nginx的物理路劲
        switch (fdsType) {
            case 0:
                fastDfsCopyToNginx(staticPageUrl, physicalPath);
                break;
            case 1:
                hdfsCopyToNginx(staticPageUrl, physicalPath);
                break;
        }
    }

    //hdfs的分布式文件系统方式
    private void hdfsCopyToNginx(String staticPageUrl, String physicalPath) {
    }

    //fastdfs的分布式文件系统方式
    private void fastDfsCopyToNginx(String staticPageUrl, String physicalPath) {
        Response response = fastDfsClient.download(staticPageUrl);
        InputStream inputStream =null;
        OutputStream outputStream =null;
        try {
            inputStream = response.body().asInputStream();
            outputStream = new FileOutputStream(physicalPath);
            //把response的输入流传到本地的输出流
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
