package com.zhang.hrm.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.zhang.hrm.util.AjaxResult;
import com.zhang.hrm.util.FastDfsApiOpr;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/fastdfs")
public class FastDfsController {
    private Logger logger = LoggerFactory.getLogger(FastDfsController.class);
    @PostMapping(value = "/upload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart(value = "file") MultipartFile file){
        try {
            String filename = file.getOriginalFilename();
            String substring = filename.substring(filename.lastIndexOf(".") + 1);
            byte[] bytes = file.getBytes();
            return FastDfsApiOpr.upload(bytes,substring);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("上传错误:"+e.getMessage());
        }
        return null;
    }

    @GetMapping(value = "/download")
    public void download(@RequestParam("path") String path, HttpServletResponse response){
        String pathTmp = path.substring(1); // goup1/xxxxx/yyyy
        String groupName =  pathTmp.substring(0, pathTmp.indexOf("/")); //goup1
        String remotePath = pathTmp.substring(pathTmp.indexOf("/")+1);// xxxxx/yyyy
        System.out.println(groupName);
        System.out.println(remotePath);
        OutputStream os = null;
        InputStream is = null;
        try {
            byte[] datas = FastDfsApiOpr.download(groupName, remotePath);
            os = response.getOutputStream(); //直接给以流方式进行返回
            is = new ByteInputStream(datas,datas.length);
            IOUtils.copy(is,os);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error...."+e.getMessage());
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam("path") String path) {
        ///group1/xxxx
        try {
            String pathTmp = path.substring(1); // goup1/xxxxx/yyyy
            String groupName =  pathTmp.substring(0, pathTmp.indexOf("/")); //goup1
            String remotePath = pathTmp.substring(pathTmp.indexOf("/")+1);// /xxxxx/yyyy
            System.out.println(groupName);
            System.out.println(remotePath);
            FastDfsApiOpr.delete(groupName,remotePath);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error...."+e.getMessage());
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }
}
