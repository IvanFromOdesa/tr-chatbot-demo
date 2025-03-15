package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import org.springframework.stereotype.Service;

@Service
public class EmployeeHomeFormOptionsService extends AuthenticatedHomeFormOptionsService {
    @Override
    protected Role getUserRole() {
        return Role.EMPLOYEE;
    }
}
