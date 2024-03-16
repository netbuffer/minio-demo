package cn.netbuffer.minio.demo.controller;

import cn.netbuffer.minio.demo.service.MinIoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/minio")
public class MinIOController {

    @Resource
    private MinIoService minIoService;

    @GetMapping
    public String get() {
        return "minio";
    }

    @GetMapping("getUrl")
    public String getUrl(String objectName) {
        return minIoService.getUrl(objectName, 5, TimeUnit.MINUTES);
    }

    @PostMapping("upload")
    public String upload(MultipartFile file) {
        return minIoService.uploadToFile(file);
    }

}
