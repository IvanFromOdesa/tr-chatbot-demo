package dev.ivank.trchatbotdemo.kb.dto;

import java.util.UUID;

public abstract class QABaseDto {
    protected String q;
    protected String a;
    protected UUID vectorStoreId;
    protected Long dbId;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public UUID getVectorStoreId() {
        return vectorStoreId;
    }

    public void setVectorStoreId(UUID vectorStoreId) {
        this.vectorStoreId = vectorStoreId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
}
