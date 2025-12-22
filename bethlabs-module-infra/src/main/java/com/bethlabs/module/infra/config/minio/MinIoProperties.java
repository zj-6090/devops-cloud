package com.bethlabs.module.infra.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
//这个注解可以将指定的配置文件的key和配置类中的属性（属性名一定要一样）一一映射
@ConfigurationProperties("minio")
public class MinIoProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    /**
     * 浏览器文件访问前缀，
     */
    private String prefix;
}
