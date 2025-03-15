package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.dto.UserSignupDto;
import dev.ivank.trchatbotdemo.common.valid.DtoEntityValidator;
import dev.ivank.trchatbotdemo.common.valid.ErrorMap;
import dev.ivank.trchatbotdemo.common.valid.FormValidationContext;
import org.springframework.stereotype.Service;

@Service
public class UserSignupFormValidator implements DtoEntityValidator<UserSignupDto, FormValidationContext<UserSignupDto>> {
    @Override
    // TODO: validator
    public ErrorMap validate(FormValidationContext<UserSignupDto> context) {
        return ErrorMap.empty();
    }
}
