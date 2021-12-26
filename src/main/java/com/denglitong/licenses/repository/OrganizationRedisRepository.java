package com.denglitong.licenses.repository;

import com.denglitong.licenses.model.Organization;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/8
 */
public interface OrganizationRedisRepository {
    void saveOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganization(Organization org);
    Organization findOrganization(String orgId);
}
