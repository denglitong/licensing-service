package com.denglitong.licenses.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static org.springframework.http.HttpMethod.DELETE;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/6
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * 受保护资源的所有访问规则都将在configure方法中定义
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 定义 DELETE需要ADMIN权限
                .antMatchers(DELETE, "/v1/organizations/**")
                .hasRole("ADMIN")
                // 定义 所有访问都需要已通过身份验证
                .anyRequest()
                .authenticated();
    }
}
