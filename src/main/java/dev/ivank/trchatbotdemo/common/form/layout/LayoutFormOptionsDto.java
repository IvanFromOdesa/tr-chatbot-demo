package dev.ivank.trchatbotdemo.common.form.layout;

import dev.ivank.trchatbotdemo.common.i18n.Language;

import java.util.EnumSet;

public record LayoutFormOptionsDto(String logoPath, EnumSet<Language> languages) {

}
