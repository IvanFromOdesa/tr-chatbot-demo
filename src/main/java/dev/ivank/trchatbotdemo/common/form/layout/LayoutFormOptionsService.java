package dev.ivank.trchatbotdemo.common.form.layout;

import dev.ivank.trchatbotdemo.common.form.GenericFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.IFormOptionsService;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayoutFormOptionsService implements IFormOptionsService<RestForm<? extends GenericFormOptionsDto>> {
    private final LayoutLocalizationService localizationService;

    @Autowired
    public LayoutFormOptionsService(LayoutLocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public void prepareFormOptions(RestForm<? extends GenericFormOptionsDto> form) {
        GenericFormOptionsDto dto = form.getDto();
        dto.setNavMenu(new LayoutFormOptionsDto("/logo.svg", Language.supported()));
        dto.getBundle().putAll(localizationService.getMessages());
    }
}
