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

    @Value("${spring.datasource.username}")
    private String dataSourceUserName;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getDataSourceUserName() {
        return dataSourceUserName;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
