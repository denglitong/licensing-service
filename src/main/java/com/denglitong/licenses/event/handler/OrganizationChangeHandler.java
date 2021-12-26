package com.denglitong.licenses.event.handler;

import com.denglitong.licenses.event.CustomChannels;
import com.denglitong.licenses.event.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/8
 */
@EnableBinding({Sink.class, CustomChannels.class})
public class OrganizationChangeHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    // private OrganizationRedisRepository organizationRedisRepository;
    //
    // private AppConfig appConfig;
    //
    // @Autowired
    // public void setOrganizationRedisRepository(OrganizationRedisRepository organizationRedisRepository) {
    //     this.organizationRedisRepository = organizationRedisRepository;
    // }
    //
    // @Autowired
    // public void setAppConfig(AppConfig appConfig) {
    //     this.appConfig = appConfig;
    // }

    @StreamListener(CustomChannels.INBOUND_ORG_CHANGES)
    public void loggerSin(OrganizationChangeModel orgChange) {
        logger.info("Received a message of type {}", orgChange.getType());
        switch (orgChange.getAction()) {
            case "GET":
                logger.info("Received a GET event from the organization service for organization id {}",
                        orgChange.getOrganizationId());
                break;
            case "SAVE":
                logger.info("Received a SAVE event from the organization service for organization id {}",
                        orgChange.getOrganizationId());
                break;
            case "UPDATE":
                logger.info("Received a UPDATE event from the organization service for organization id {}",
                        orgChange.getOrganizationId());
                break;
            case "DELETE":
                logger.info("Received a DELETE event from the organization service for organization id {}",
                        orgChange.getOrganizationId());
                break;
            default:
                logger.info("Received an UNKNOWN event from the organization service of type {}",
                        orgChange.getType());
                break;
        }
    }

    // @StreamListener(Sink.INPUT)
    // public void loggerSink(OrganizationChangeModel orgChange) {
    //     logger.info("Received an event for organization id {}", orgChange.getOrganizationId());
    // }
}
