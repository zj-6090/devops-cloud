package com.btlab.devops.module.ids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 * <p>
 */
@SpringBootApplication
public class IdsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdsServerApplication.class, args);

        System.out.println("*****************IDS服务【ids】*****************");
    }

}
