package com.bethlabs.module.infra.config.minio;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class MinioManage {

    @Autowired
    private MinioClient minioClient;


    public void deleteBucket(String bucketName) {
        try {
            // 步骤1：清空桶内所有对象
            emptyBucket(bucketName);

            // 步骤2：删除空桶
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("删除桶时发生错误: " + e.getMessage());
        }
    }

    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("删除文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createBucket(String bucketName) throws Exception {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            setBucketPublic(minioClient, bucketName);
        } catch (Exception e) {
            log.error("创建桶时发生错误: " + e.getMessage());
        }
        //设置桶内对象过期时间，单位是天，默认设置为90天
        setObjectExpiration(bucketName, 90);
    }


    private void emptyBucket(String bucketName) throws Exception {
        // 检查桶是否存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        if (!bucketExists) {
            System.out.println("桶不存在: " + bucketName);
            return;
        }

        // 遍历桶内所有对象并删除
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true)  // 递归列出所有子目录中的对象
                .build());

        List<DeleteObject> objectsToDelete = new ArrayList<>();
        for (Result<Item> result : results) {
            Item item = result.get();
            objectsToDelete.add(new DeleteObject(item.objectName()));

            // 每1000个对象批量删除一次（MinIO建议的最大批量大小）
            if (objectsToDelete.size() >= 1000) {
                deleteObjects(minioClient, bucketName, objectsToDelete);
                objectsToDelete.clear();
            }
        }

        // 删除剩余的对象
        if (!objectsToDelete.isEmpty()) {
            deleteObjects(minioClient, bucketName, objectsToDelete);
        }
    }

    private static void deleteObjects(MinioClient minioClient, String bucketName, List<DeleteObject> objectsToDelete) {
        try {
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(objectsToDelete).build());

            // 处理删除失败的对象
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("删除对象失败: {}，错误: {}", error.objectName(), error.message());
            }
        } catch (Exception e) {
            log.error("删除对象时发生错误: " + e.getMessage());
        }
    }

    public void setObjectExpiration(String bucketName, Integer days) throws Exception {
        // 3. 设置生命周期规则
        LifecycleRule rule = new LifecycleRule(Status.ENABLED,                // 状态
                null, new Expiration((ResponseDate) null, days, null),// 30天后过期
                new RuleFilter(""),                 // 应用到所有对象，无过渡规则
                "day-expiry",                   // 规则名
                null,                              // 非当前版本过期规则
                null,                              // 非当前版本转换规则
                null                               // 对象转换规则
        );

        LifecycleConfiguration config = new LifecycleConfiguration(Collections.singletonList(rule));

        minioClient.setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).config(config).build());
        System.out.println("Lifecycle rule set: " + days + " days expiration");
    }

    public void setBucketPublic(MinioClient minioClient, String bucketName) {
        String policyJson = "{" + "\"Version\":\"2012-10-17\"," + "\"Statement\":[{" + "\"Effect\":\"Allow\"," + "\"Principal\":{\"AWS\":[\"*\"]}," + "\"Action\":[\"s3:GetObject\"]," + "\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]" + "}]}";

        try {
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policyJson).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Bucket policy set to public");
    }
}
