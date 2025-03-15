package dev.ivank.trchatbotdemo.security.acl;

import java.util.function.Supplier;

public interface SecurityExpressionHandlerHelper {
    void setAclServiceSupplier(Supplier<AclService> serviceSupplier);
}
