package dev.ivank.trchatbotdemo.common.form;

import dev.ivank.trchatbotdemo.common.AssetsProperties;
import dev.ivank.trchatbotdemo.common.PathNavigation;
import dev.ivank.trchatbotdemo.common.form.layout.LayoutFormOptionsService;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.security.auth.service.AuthenticationHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractBaseFormOptionsService<DTO extends BaseFormOptionsDto, FORM extends RestForm<DTO>> implements IFormOptionsService<FORM> {
    @Autowired
    private LayoutFormOptionsService layoutFormOptionsService;
    @Autowired
    private Map<Role, List<PathNavigation>> rolePathNavigationMap;
    @Autowired
    private AssetsProperties assetsProperties;
    @Autowired
    private AuthenticationHolderService authenticationHolderService;

    public void prepareFormOptions(FORM form) {
        layoutFormOptionsService.prepareFormOptions(form);
        BaseFormOptionsDto dto = form.getDto();
        List<PathNavigation> pathNavigations = rolePathNavigationMap.get(
                authenticationHolderService.getUserRole()
        );
        dto.setPathNavigation(pathNavigations
                .stream()
                .collect(Collectors.toMap(
                                PathNavigation::name,
                                PathNavigation::path
                        )
                )
        );
        dto.setAssets(assetsProperties.paths());
        dto.setLocale(Language.byLocaleLang(LocaleContextHolder.getLocale()).getAlias());
    }
}
