package dev.ivank.trchatbotdemo.security.auth;

import dev.ivank.trchatbotdemo.common.form.AuthenticatedFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.security.auth.service.form.HomeFormOptionsDelegator;
import dev.ivank.trchatbotdemo.common.BaseController;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.security.auth.service.i18n.HomeLocalizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

@Controller
public class HomeController extends BaseController {
    private final HomeFormOptionsDelegator homeFormOptionsDelegator;
    private final HomeLocalizationService localizationService;

    @Autowired
    public HomeController(HomeFormOptionsDelegator homeFormOptionsDelegator,
                          HomeLocalizationService localizationService) {
        this.homeFormOptionsDelegator = homeFormOptionsDelegator;
        this.localizationService = localizationService;
    }

    @GetMapping("${controller.paths.index}")
    public String homePage(Model model) {
        model.addAttribute(
                "pageTitle",
                localizationService.getMessage("page.title")
        );
        return properties.getPage("index");
    }

    @GetMapping("${controller.paths.indexInit}")
    public ResponseEntity<BaseFormOptionsDto> initHome(Authentication authentication) {
        RestForm<BaseFormOptionsDto> form = getBaseFormOptionsDtoRestForm(authentication);
        homeFormOptionsDelegator.prepareFormOptions(form);
        return ResponseEntity.ok(form.getDto());
    }

    private static RestForm<BaseFormOptionsDto> getBaseFormOptionsDtoRestForm(Authentication authentication) {
        return new RestForm<>(
                (authentication == null || !authentication.isAuthenticated()) ?
                        new BaseFormOptionsDto() :
                        new AuthenticatedFormOptionsDto(),
                authentication
        );
    }

    @PostMapping("${controller.paths.langPreference}")
    public ResponseEntity<?> setLanguagePreference(@RequestParam("key") Language language, HttpServletRequest request) {
        if (language.isValid()) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, language.getLocale());
        }
        return ResponseEntity.ok().build();
    }

    // TODO: this web page
    @GetMapping("${controller.paths.tos}")
    public String tosPage() {
        return properties.getPage("tos");
    }
}
