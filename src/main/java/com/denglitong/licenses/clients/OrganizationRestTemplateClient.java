package com.denglitong.licenses.clients;

import com.denglitong.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/24
 */
@Component
public class OrganizationRestTemplateClient {

    RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                // "http://organizationservice/v1/organizations/{organizationId}",
                // 使用Zuul服务网关代理请求
                "http://zuulservice/api/organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }

}
