package com.zhang.hrm.client;

import com.zhang.hrm.config.FeignMultipartSupportConfig;
import com.zhang.hrm.util.AjaxResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "HRM-FASTDFS", configuration = FeignMultipartSupportConfig.class,
        fallbackFactory = FastDfsClientHystrixFallbackFactory.class)
@RequestMapping("/fastdfs")
public interface FastDfsClient {

    /**
     * 上传文件
     * @param file
     */
    @PostMapping(value = "/upload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    String upload(MultipartFile file);

    /**
     * 删除文件
     * @return
     */
    @DeleteMapping(value = "/delete")
    AjaxResult delete(String path);

    /**
     * 下载文件
     * @param path
     */
    @GetMapping(value = "/download")
    Response download(String path);

}
