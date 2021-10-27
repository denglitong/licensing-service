package com.denglitong.licenses.service;

import com.denglitong.licenses.clients.OrganizationDiscoveryClient;
import com.denglitong.licenses.clients.OrganizationFeignClient;
import com.denglitong.licenses.clients.OrganizationRestTemplateClient;
import com.denglitong.licenses.config.ServiceConfig;
import com.denglitong.licenses.model.License;
import com.denglitong.licenses.model.Organization;
import com.denglitong.licenses.repository.LicenseRepository;
import com.denglitong.licenses.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/22
 */
@Service
// 类级别的Hystrix配置，类内部的注解都共享这些配置，除非被函数级别的注解所覆盖
// 这里配置值可移到spring cloud config服务内，这样修改完配置值后重新启动服务即可
@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
        }
)
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private LicenseRepository licenseRepository;

    private ServiceConfig serviceConfig;

    private OrganizationRestTemplateClient organizationRestClient;

    private OrganizationDiscoveryClient organizationDiscoveryClient;

    private OrganizationFeignClient organizationFeignClient;

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        logger.debug("LicenseService.retrieveOrgInfo");
        randomlyRunLong();
        Organization organization = null;
        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }
        return organization;
    }

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Autowired
    public void setOrganizationRestClient(OrganizationRestTemplateClient organizationRestClient) {
        this.organizationRestClient = organizationRestClient;
    }

    @Autowired
    public void setOrganizationDiscoveryClient(OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    @Autowired
    public void setOrganizationFeignClient(OrganizationFeignClient organizationFeignClient) {
        this.organizationFeignClient = organizationFeignClient;
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
    }

    /**
     * HystrixCommand 注解修饰的方法需要被外部类直接调用才生效（AOP代理类的要求），
     * 注解在间接调用的的内部方法不生效，比如这里的 retrieveOrgInfo 添加注解是不生效的
     */
    @HystrixCommand
    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license.withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(serviceConfig.getExampleProperty());
    }

    private void randomlyRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Random random = new Random();
            int randomNum = random.nextInt(6) + 1;
            Thread.sleep(randomNum * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Netflix推荐的自定义线程池大小：
     * 服务健康状态下每秒支撑的最大请求数 * 第99百分位延迟时间（秒）+ 用于缓冲的少量额外线程
     * <p>
     * 默认情况下Hystrix采用THREAD的隔离策略，父线程的context传递不到Hystrix注解标注开启的子线程里
     *
     * @param organizationId
     * @return
     */
    @HystrixCommand(
            threadPoolKey = "licenceByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "20")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"), // 监视窗口15s
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"), // 窗口内的监视信息收集在5个桶中，每个桶收集3s，这里必须整除
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"), // 断路器触发跳闸必须达到的窗口内请求数量
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"), // 触发跳闸的错误率阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"), // 断路器触发后允许下一个请求通过的休眠时间（以便检查服务是否恢复）
            },
            fallbackMethod = "buildFallbackLicenseList"
    )
    public List<License> getLicensesByOrg(String organizationId) {
        logger.info("LicenseService.getLicensesByOrg correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    /**
     * 后备方法需要和原方法保持函数签名一致
     * 注意后备方法可能失败的情况，此时需要进行防御型编程，如可对后备方法再使用 @HystrixCommand 来注解
     */
    private List<License> buildFallbackLicenseList(String organizationId) {
        License license = new License().withLicenseId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");
        return Collections.singletonList(license);
    }

    public void saveLicense(License license) {
        license.withLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(String licenseId) {
        licenseRepository.deleteById(licenseId);
    }
}
