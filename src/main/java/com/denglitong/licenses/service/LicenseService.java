package com.denglitong.licenses.service;

import com.denglitong.licenses.model.License;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/22
 */
@Service
public class LicenseService {

    public License getLicense(String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(UUID.randomUUID().toString())
                .withProductName("Test Product Name")
                .withLicenseType("PerSeat");
    }

    public void saveLicense(License license) {

    }

    public void updateLicense(License license) {

    }

    public void deleteLicense(License license) {

    }
}
