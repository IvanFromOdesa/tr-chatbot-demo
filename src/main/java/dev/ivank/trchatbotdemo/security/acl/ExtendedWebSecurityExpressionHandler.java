package dev.ivank.trchatbotdemo.security.acl;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.function.Supplier;

public class ExtendedWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler implements SecurityExpressionHandlerHelper {
    private Supplier<AclService> aclServiceSupplier;

    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
        final ExtendedWebSecurityExpressionRoot expressionRoot = new ExtendedWebSecurityExpressionRoot(authentication, fi);
        expressionRoot.setAclService(aclServiceSupplier.get());
        expressionRoot.setPermissionEvaluator(this.getPermissionEvaluator());
        expressionRoot.setTrustResolver(new AuthenticationTrustResolverImpl());
        expressionRoot.setRoleHierarchy(this.getRoleHierarchy());
        expressionRoot.setDefaultRolePrefix(Role.ROLE_PREFIX);
        return expressionRoot;
    }

    @Override
    public void setAclServiceSupplier(Supplier<AclService> aclServiceSupplier) {
        this.aclServiceSupplier = aclServiceSupplier;
    }
}
