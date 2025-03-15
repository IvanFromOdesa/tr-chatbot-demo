package dev.ivank.trchatbotdemo.security.auth;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.security.auth.dto.UserSignupDto;
import dev.ivank.trchatbotdemo.security.auth.service.UserSignupFormValidator;
import dev.ivank.trchatbotdemo.security.auth.service.UserSignupService;
import dev.ivank.trchatbotdemo.security.auth.service.form.LoginFormOptionsService;
import dev.ivank.trchatbotdemo.security.auth.service.form.SignupFormOptionsService;
import dev.ivank.trchatbotdemo.common.BaseController;
import dev.ivank.trchatbotdemo.common.util.UUIDUtils;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import dev.ivank.trchatbotdemo.common.form.SessionMessageDto;
import dev.ivank.trchatbotdemo.common.form.layout.BSVariant;
import dev.ivank.trchatbotdemo.common.valid.BaseErrorMapException;
import dev.ivank.trchatbotdemo.common.valid.ErrorMap;
import dev.ivank.trchatbotdemo.common.valid.FormValidationContext;
import dev.ivank.trchatbotdemo.security.auth.service.i18n.LoginLocalizationService;
import dev.ivank.trchatbotdemo.security.auth.service.i18n.SignupLocalizationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static dev.ivank.trchatbotdemo.common.HttpSessionAttributeNames.SESSION_MSG;

@Controller
public class AuthenticationController extends BaseController {
    private final LoginFormOptionsService loginFormOptionsService;
    private final SignupFormOptionsService signupFormOptionsService;
    private final UserSignupFormValidator signupFormValidator;
    private final UserSignupService signupService;
    private final LoginLocalizationService loginLocalizationService;
    private final SignupLocalizationService signupLocalizationService;

    @Autowired
    public AuthenticationController(LoginFormOptionsService loginFormOptionsService,
                                    SignupFormOptionsService signupFormOptionsService,
                                    UserSignupFormValidator signupFormValidator,
                                    UserSignupService signupService,
                                    LoginLocalizationService loginLocalizationService,
                                    SignupLocalizationService signupLocalizationService) {
        this.loginFormOptionsService = loginFormOptionsService;
        this.signupFormOptionsService = signupFormOptionsService;
        this.signupFormValidator = signupFormValidator;
        this.signupService = signupService;
        this.loginLocalizationService = loginLocalizationService;
        this.signupLocalizationService = signupLocalizationService;
    }

    @GetMapping("${controller.paths.login}")
    public String loginPage(Model model) {
        model.addAttribute(
                "pageTitle",
                loginLocalizationService.getMessage("page.title")
        );
        return properties.getPage("login");
    }

    @GetMapping("${controller.paths.loginApi}/init")
    public ResponseEntity<BaseFormOptionsDto> initLogin() {
        return initResponse(loginFormOptionsService::prepareFormOptions);
    }

    @GetMapping("${controller.paths.signup}")
    public String signupPage(Model model) {
        model.addAttribute(
                "pageTitle",
                signupLocalizationService.getMessage("page.title")
        );
        return properties.getPage("signup");
    }

    @GetMapping("${controller.paths.signupApi}/init")
    public ResponseEntity<BaseFormOptionsDto> initSignup() {
        return initResponse(signupFormOptionsService::prepareFormOptions);
    }

    @GetMapping("${controller.paths.signup}/activate/{encoded}")
    public String activate(HttpSession session, @PathVariable String encoded) {
        User user = signupService.activate(UUIDUtils.fromBase64Url(encoded));
        session.setAttribute(SESSION_MSG, (Supplier<SessionMessageDto>) () ->
                new SessionMessageDto(signupLocalizationService.getMessage(
                        "message.activated",
                        user.getFullName()
        ),
                        BSVariant.PRIMARY)
        );
        return redirectToLogin();
    }

    @PostMapping("${controller.paths.signupApi}")
    public String signup(HttpSession session, @RequestBody UserSignupDto form) {
        ErrorMap errors = signupFormValidator.validate(new FormValidationContext<>(form));
        if (!errors.isEmpty()) {
            throw new BaseErrorMapException(errors);
        } else {
            String path = properties.getPath("signup");
            String signupCode = signupService.signup(
                    form,
                    getBaseUrl().concat(path).concat("/activate")
            );
            session.setAttribute(SESSION_MSG, (Supplier<SessionMessageDto>) () ->
                    new SessionMessageDto(
                            signupLocalizationService.getMessage(signupCode),
                            BSVariant.SUCCESS
                    )
            );
            return redirectToLogin();
        }
    }

    // TODO: 400 on authorization
    @GetMapping("${controller.paths.sessionMsg}")
    public ResponseEntity<SessionMessageDto> sessionMessage(@SessionAttribute(SESSION_MSG) Supplier<SessionMessageDto> msgSupplier) {
        return ResponseEntity.ok(msgSupplier.get());
    }

    private String redirectToLogin() {
        return "redirect:".concat(properties.getPath("login"));
    }

    private ResponseEntity<BaseFormOptionsDto> initResponse(Consumer<RestForm<BaseFormOptionsDto>> populateDtoCallback) {
        RestForm<BaseFormOptionsDto> form = new RestForm<>(new BaseFormOptionsDto());
        populateDtoCallback.accept(form);
        return ResponseEntity.ok(form.getDto());
    }
}
