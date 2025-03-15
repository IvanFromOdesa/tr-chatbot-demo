package dev.ivank.trchatbotdemo.security.auth;

import dev.ivank.trchatbotdemo.common.HttpSessionAttributeNames;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(ExtendedAuthenticationSuccessHandler.NAME)
public class ExtendedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String NAME = "extendedAuthenticationSuccessHandler";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        request.getSession().removeAttribute(HttpSessionAttributeNames.SESSION_MSG);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
