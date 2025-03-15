package dev.ivank.trchatbotdemo.security.acl;

import dev.ivank.trchatbotdemo.security.auth.service.AuthenticationHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AclService {
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public AclService(AuthenticationHolderService authenticationHolderService) {
        this.authenticationHolderService = authenticationHolderService;
    }

    public boolean isVisitor() {
        return authenticationHolderService.getUserRole().isVisitor();
    }

    public boolean isEmployee() {
        return authenticationHolderService.getUserRole().isEmployee();
    }
}
