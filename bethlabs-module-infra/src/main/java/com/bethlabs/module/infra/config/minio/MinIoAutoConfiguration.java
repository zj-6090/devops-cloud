package com.bethlabs.module.infra.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(MinIoProperties.class)
// 这个注解的作用是将MinIoPropertiesBean对象放到容器中 并且开启这个类的属性绑定
public class MinIoAutoConfiguration {

    @Autowired
    private MinIoProperties minIoProperties;


    @Bean
    public MinioClient minioClient() throws Exception {
        // 创建 MinIO 客户端
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minIoProperties.getEndpoint())
                .credentials(minIoProperties.getAccessKey(), minIoProperties.getSecretKey())
                .build();

        return minioClient;
    }


}
