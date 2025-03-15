package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationHolderService {
    private static final String ANONYMOUS_AUTHENTICATION = "anonymousUser";

    public Optional<User> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousAuthentication(authentication)) {
            return Optional.empty();
        }
        return Optional.of((User) authentication.getPrincipal());
    }

    public Role getUserRole() {
        Optional<User> currentAuthentication = getCurrentAuthentication();
        if (currentAuthentication.isPresent()) {
            return currentAuthentication.get().getRole();
        }
        return Role.ANONYMOUS;
    }

    public static boolean isAnonymousAuthentication(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken || authentication.getName().equals(ANONYMOUS_AUTHENTICATION);
    }
}
