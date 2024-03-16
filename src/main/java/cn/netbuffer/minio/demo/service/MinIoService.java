package cn.netbuffer.minio.demo.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinIoService {

    @Resource
    private MinioClient minioClient;
    private String bucket = "demo";

    public String uploadToFile(MultipartFile file) {
        ObjectWriteResponse objectWriteResponse = null;
        try {
            InputStream inputStream = file.getInputStream();
            objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            log.error("putObject error:", e);
        }
        if (objectWriteResponse == null) {
            return null;
        }
        return objectWriteResponse.object();
    }

    public String getUrl(String objectName, int time, TimeUnit timeUnit) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(objectName)
                    .expiry(time, timeUnit).build());
        } catch (Exception e) {
            log.error("getPresignedObjectUrl error:", e);
        }
        return url;
    }

}
