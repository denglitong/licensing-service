package com.denglitong.licenses.model;

import java.io.Serializable;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/22
 */
public class Organization implements Serializable {
    private static final long serialVersionUID = 8298128573107252365L;

    String organizationId;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
