package com.denglitong.licenses.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/1
 */
@Component
public class BeanConfig {

    /**
     * LoadBalanced 注解告诉Spring Cloud创建一个支持Ribbon的RestTemplate类
     *
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
