package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.common.util.UUIDUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivationTokenService {
    public UUID generate(User user) {
        return UUIDUtils.v5(user.getEmail());
    }
}
