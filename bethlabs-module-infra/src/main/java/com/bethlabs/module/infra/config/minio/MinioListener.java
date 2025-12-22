package com.bethlabs.module.infra.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 进行minio的初始化桶操作，创建默认桶，并设置桶的策略等操作。
 */
@Component
@Slf4j
public class MinioListener implements CommandLineRunner {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioManage minioManage;

    @Override
    public void run(String... args) throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
        ;
        if (!exists) {
            //创建桶，并且设置为public访问权限，
            minioManage.createBucket(bucketName);
        } else {
            log.info("桶已经存在");
        }
    }
}
