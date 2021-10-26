package com.denglitong.licenses.controller;

import com.denglitong.licenses.model.License;
import com.denglitong.licenses.service.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/21
 */
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    private LicenseService licenseService;

    @Autowired
    public void setLicenseService(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @GetMapping
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        logger.debug("LicenseServiceController.getLicenses");
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(organizationId, licenseId);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicensesWithType(@PathVariable("organizationId") String organizationId,
                                       @PathVariable("licenseId") String licenseId,
                                       @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @PutMapping("/{licenseId}")
    public void updateLicenses(@RequestBody License license) {
        licenseService.updateLicense(license);
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicenses(@PathVariable("licenseId") String licenseId) {
        licenseService.deleteLicense(licenseId);
    }
}
