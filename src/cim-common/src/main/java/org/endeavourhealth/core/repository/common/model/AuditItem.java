package org.endeavourhealth.core.repository.common.model;

import java.util.Date;
import java.util.UUID;

public class AuditItem<T> {
    private UUID id;
    private String tableName;
    private UUID rowKey;
    private Date auditDate;
    private UUID userId;
    private AuditMode mode;
    private T data;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UUID getRowKey() {
        return rowKey;
    }

    public void setRowKey(UUID rowKey) {
        this.rowKey = rowKey;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public AuditMode getMode() {
        return mode;
    }

    public void setMode(AuditMode mode) {
        this.mode = mode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}