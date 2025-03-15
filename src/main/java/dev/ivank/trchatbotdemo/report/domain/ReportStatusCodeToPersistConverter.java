package dev.ivank.trchatbotdemo.report.domain;

import dev.ivank.trchatbotdemo.common.enums.EnumCustomFieldToPersistConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReportStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, ReportStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReportStatus reportStatus) {
        return byValue(reportStatus);
    }

    @Override
    public ReportStatus convertToEntityAttribute(Integer code) {
        return byKey(ReportStatus.INITIALIZED, code);
    }
}
