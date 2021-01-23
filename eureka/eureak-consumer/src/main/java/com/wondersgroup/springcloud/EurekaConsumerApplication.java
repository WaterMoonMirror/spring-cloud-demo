package com.wondersgroup.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2020/12/24 15:11
 * @description: eureka客户端
 */
@SpringBootApplication
@EnableDiscoveryClient // eureka客户端注解
public class EurekaConsumerApplication {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaConsumerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
