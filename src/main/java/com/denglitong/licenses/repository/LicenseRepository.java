package com.denglitong.licenses.repository;

import com.denglitong.licenses.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/23
 */
@Repository
public interface LicenseRepository extends JpaRepository<License, String> {

    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndAndLicenseId(String organizationId, String licenseId);

}
