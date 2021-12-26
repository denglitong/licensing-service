package com.denglitong.licenses.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/8
 */
public interface CustomChannels {
    String INBOUND_ORG_CHANGES = "inboundOrgChanges";

    @Input(INBOUND_ORG_CHANGES)
    SubscribableChannel inbound();
}
