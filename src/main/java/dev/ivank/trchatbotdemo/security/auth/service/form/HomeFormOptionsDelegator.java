package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.security.auth.service.AuthenticationHolderService;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HomeFormOptionsDelegator {
    private final AuthenticationHolderService authenticationHolderService;
    private final Map<Role, HomeFormOptionsService<? extends BaseFormOptionsDto>> delegateMap;

    @Autowired
    public HomeFormOptionsDelegator(AuthenticationHolderService authenticationHolderService,
                                    List<HomeFormOptionsService<? extends BaseFormOptionsDto>> homeFormOptionsServices) {
        this.authenticationHolderService = authenticationHolderService;
        this.delegateMap = homeFormOptionsServices
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        HomeFormOptionsService::getUserRole,
                        Function.identity())
                );
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseFormOptionsDto> void prepareFormOptions(RestForm<T> form) {
        Role userRole = authenticationHolderService.getUserRole();
        HomeFormOptionsService<? extends BaseFormOptionsDto> homeFormOptionsService =
                delegateMap.get(userRole);

        if (homeFormOptionsService == null) {
            throw new IllegalStateException("No mapping for user role: %s".formatted(userRole));
        }

        // Safe cast as the service has the same generic type
        HomeFormOptionsService<T> typedService = (HomeFormOptionsService<T>) homeFormOptionsService;
        typedService.prepareFormOptions(form);
    }
}
