package com.denglitong.licenses.clients;

import com.denglitong.licenses.model.Organization;
import com.denglitong.licenses.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.denglitong.licenses.utils.UserContextHolder.getContext;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/24
 */
@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    RestTemplate restTemplate;
    OrganizationRedisRepository orgRedisRepo;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setOrgRedisRepo(OrganizationRedisRepository orgRedisRepo) {
        this.orgRedisRepo = orgRedisRepo;
    }

    public Organization getOrganization(String organizationId) {
        logger.info("In Licensing Service getOrganization: {}", getContext().getCorrelationId());

        Organization org = checkRedisCache(organizationId);
        if (org != null) {
            logger.info("Successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                // "http://organizationservice/v1/organizations/{organizationId}",
                // 使用Zuul服务网关代理请求
                "http://zuulservice/api/organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET, null, Organization.class, organizationId);

        org = restExchange.getBody();
        if (org != null) {
            orgRedisRepo.saveOrganization(org);
        }

        return org;
    }

    private Organization checkRedisCache(String organizationId) {
        try {
            return orgRedisRepo.findOrganization(organizationId);
        } catch (Exception ex) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis cache. Exception {}",
                    organizationId, ex);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization org) {
        try {
            orgRedisRepo.saveOrganization(org);
        } catch (Exception ex) {
            logger.error("Unable to cache organization {} in cache. Exception {}", org.getOrganizationId(), ex);
        }
    }
}
