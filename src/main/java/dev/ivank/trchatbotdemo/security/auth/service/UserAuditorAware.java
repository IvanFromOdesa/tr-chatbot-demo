package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<User> {
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public UserAuditorAware(AuthenticationHolderService authenticationHolderService) {
        this.authenticationHolderService = authenticationHolderService;
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        return authenticationHolderService.getCurrentAuthentication();
    }
}
