package dev.ivank.trchatbotdemo.report.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReportStatusSerializer extends CustomSerializer<ReportStatus> {
    @Override
    public Class<ReportStatus> getSerializationClass() {
        return ReportStatus.class;
    }

    @Override
    public void serialize(ReportStatus reportStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(reportStatus.getAlias());
    }
}
