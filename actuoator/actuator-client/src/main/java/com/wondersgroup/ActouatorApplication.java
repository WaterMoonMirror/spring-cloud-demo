package com.wondersgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2021/1/25 15:06
 * @description:
 */
@SpringBootApplication
public class ActouatorApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ActouatorApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
