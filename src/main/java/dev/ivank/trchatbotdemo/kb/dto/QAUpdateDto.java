package dev.ivank.trchatbotdemo.kb.dto;

public class QAUpdateDto extends QABaseDto {
    public QAUpdateDto() {

    }

    public QAUpdateDto(String q, String a) {
        this.q = q;
        this.a = a;
    }

    public boolean isToSave() {
        return this.vectorStoreId == null || this.dbId == null;
    }
}
