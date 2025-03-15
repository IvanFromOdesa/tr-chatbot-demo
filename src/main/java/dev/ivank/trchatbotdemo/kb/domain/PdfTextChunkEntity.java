package dev.ivank.trchatbotdemo.kb.domain;

import com.google.common.base.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class PdfTextChunkEntity {
    @EmbeddedId
    private PdfTextChunkId id;

    @ManyToOne
    @MapsId("pdfId")
    private PdfEntity pdfEntity;

    public PdfTextChunkId getId() {
        return id;
    }

    public void setId(PdfTextChunkId id) {
        this.id = id;
    }

    public PdfEntity getPdfEntity() {
        return pdfEntity;
    }

    public void setPdfEntity(PdfEntity pdfEntity) {
        this.pdfEntity = pdfEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfTextChunkEntity that = (PdfTextChunkEntity) o;
        return Objects.equal(id, that.id) && Objects.equal(pdfEntity, that.pdfEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, pdfEntity);
    }

    @Embeddable
    public static class PdfTextChunkId implements Serializable {
        private UUID vectorStoreId;
        private Long pdfId;

        public UUID getVectorStoreId() {
            return vectorStoreId;
        }

        public void setVectorStoreId(UUID vectorStoreId) {
            this.vectorStoreId = vectorStoreId;
        }

        public Long getPdfId() {
            return pdfId;
        }

        public void setPdfId(Long pdfId) {
            this.pdfId = pdfId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PdfTextChunkId that = (PdfTextChunkId) o;
            return Objects.equal(vectorStoreId, that.vectorStoreId) && Objects.equal(pdfId, that.pdfId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(vectorStoreId, pdfId);
        }
    }
}
