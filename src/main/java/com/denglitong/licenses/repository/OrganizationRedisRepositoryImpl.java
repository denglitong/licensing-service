package com.denglitong.licenses.repository;

import com.denglitong.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/8
 */
@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

    private static final String HASH_NAME = "organization";

    private RedisTemplate<String, Organization> redisTemplate;

    private HashOperations<String, String, Organization> hashOperations;

    @Autowired
    public OrganizationRedisRepositoryImpl(RedisTemplate<String, Organization> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getOrganizationId(), org);
    }

    @Override
    public void updateOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getOrganizationId(), org);
    }

    @Override
    public void deleteOrganization(Organization org) {
        hashOperations.delete(HASH_NAME, org.getOrganizationId());
    }

    @Override
    public Organization findOrganization(String orgId) {
        return hashOperations.get(HASH_NAME, orgId);
    }
}
