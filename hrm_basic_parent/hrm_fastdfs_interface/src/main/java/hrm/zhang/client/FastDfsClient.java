package hrm.zhang.client;

import com.zhang.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignClientsConfiguration.class,
        fallbackFactory = FastDfsClientHystrixFallbackFactory.class)
@RequestMapping("/fastdfs")
public interface FastDfsClient {

    /**
     * 上传文件
     * @param file
     */
    @PostMapping(value = "/upload")
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
    void download(String path, HttpServletResponse response);

}
