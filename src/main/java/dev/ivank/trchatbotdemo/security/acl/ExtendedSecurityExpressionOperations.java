package dev.ivank.trchatbotdemo.security.acl;

import org.springframework.security.access.expression.SecurityExpressionOperations;

public interface ExtendedSecurityExpressionOperations extends SecurityExpressionOperations {
    AclService getAclService();

    default boolean isVisitor() {
        return getAclService().isVisitor();
    }

    default boolean isEmployee() {
        return getAclService().isEmployee();
    }
}
