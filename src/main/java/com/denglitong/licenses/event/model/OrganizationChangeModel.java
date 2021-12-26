package com.denglitong.licenses.event.model;

import java.io.Serializable;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/11/7
 */
public class OrganizationChangeModel implements Serializable {
    private static final long serialVersionUID = -5577373958548623282L;

    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

    public OrganizationChangeModel withType(String type) {
        setType(type);
        return this;
    }

    public OrganizationChangeModel withAction(String action) {
        setAction(action);
        return this;
    }

    public OrganizationChangeModel withOrganizationId(String organizationId) {
        setOrganizationId(organizationId);
        return this;
    }

    public OrganizationChangeModel withCorrelationId(String correlationId) {
        setCorrelationId(correlationId);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
