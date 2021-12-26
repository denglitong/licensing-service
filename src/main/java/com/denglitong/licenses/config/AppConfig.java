package com.denglitong.licenses.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/23
 */
@Component
public class AppConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${redis.server:''}")
    private String redisServer;

    @Value("${redis.port:''}")
    private String redisPort;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getRedisServer() {
        return redisServer;
    }

    public Integer getRedisPort() {
        return Integer.valueOf(redisPort);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
