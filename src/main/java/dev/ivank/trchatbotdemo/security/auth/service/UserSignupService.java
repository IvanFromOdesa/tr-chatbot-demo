package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.domain.AuthenticationStatus;
import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.security.auth.dto.UserSignupDto;
import dev.ivank.trchatbotdemo.security.auth.service.i18n.SignupLocalizationService;
import dev.ivank.trchatbotdemo.common.EntityNotFoundException;
import dev.ivank.trchatbotdemo.common.util.UUIDUtils;
import dev.ivank.trchatbotdemo.common.cache.ManualCacheManager;
import dev.ivank.trchatbotdemo.common.mail.EmailPrepareService;
import dev.ivank.trchatbotdemo.common.mail.IEmailService;
import dev.ivank.trchatbotdemo.common.mail.NotificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserSignupService {
    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;
    private final ManualCacheManager cacheManager;
    private final ActivationTokenService activationTokenService;
    private final SignupLocalizationService localizationService;
    private final IEmailService<NotificationEmail> emailService;
    private final EmailPrepareService emailPrepareService;

    @Value("${caching.default.ttl}")
    private Long cacheTtl;

    private static final String TOKEN_CACHE_NAME = "activationTokens";

    @Autowired
    public UserSignupService(UserEntityService userEntityService,
                             PasswordEncoder passwordEncoder,
                             ManualCacheManager cacheManager,
                             ActivationTokenService activationTokenService,
                             SignupLocalizationService localizationService,
                             IEmailService<NotificationEmail> emailService,
                             EmailPrepareService emailPrepareService) {
        this.userEntityService = userEntityService;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
        this.activationTokenService = activationTokenService;
        this.localizationService = localizationService;
        this.emailService = emailService;
        this.emailPrepareService = emailPrepareService;
    }

    public String signup(UserSignupDto signUpDtoUser, String activationLink) {
        User saved = userEntityService.save(createUser(signUpDtoUser));
        UUID id = activationTokenService.generate(saved);
        cacheManager.cache(TOKEN_CACHE_NAME, id, saved);
        sendActivationEmail(saved.getEmail(), id, activationLink);
        return "message.success";
    }

    public User activate(UUID id) {
        User evicted = cacheManager.evict(TOKEN_CACHE_NAME, id, User.class);
        if (evicted == null) {
            throw new EntityNotFoundException("error.notFound", id);
        }
        evicted.setStatus(AuthenticationStatus.CONFIRMED);
        userEntityService.save(evicted);
        return evicted;
    }

    private User createUser(UserSignupDto form) {
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(
                form
                        .getPwdForm()
                        .getPassword()
                )
        );
        user.setRole(Role.VISITOR);
        user.setStatus(AuthenticationStatus.CREATED);
        return user;
    }

    private void sendActivationEmail(String recipient, UUID token, String link) {
        String toActivate = link.concat("/%s".formatted(UUIDUtils.toBase64Url(token)));
        NotificationEmail email = emailPrepareService.prepareEmail(
                recipient,
                localizationService,
                toActivate,
                TimeUnit.MINUTES.convert(cacheTtl, TimeUnit.MILLISECONDS)
                );
        emailService.sendEmail(email);
    }
}
