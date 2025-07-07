package com.btlab.devops.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author 芋道源码
 */
@ConfigurationProperties(prefix = "btlab.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "btlab.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
