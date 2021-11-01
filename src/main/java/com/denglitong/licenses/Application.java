package com.denglitong.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/21
 */
@SpringBootApplication
@RefreshScope
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
