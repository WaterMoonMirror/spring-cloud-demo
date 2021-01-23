package com.wondersgroup.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2020/12/24 10:24
 * @description: eureka 注册中心
 */
@SpringBootApplication
@EnableEurekaServer // eurekaServer启动注解
public class EurekaServicePeerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServicePeerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
