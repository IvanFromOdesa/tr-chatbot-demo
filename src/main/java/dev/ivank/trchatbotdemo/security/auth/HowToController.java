package dev.ivank.trchatbotdemo.security.auth;

import dev.ivank.trchatbotdemo.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EmployeeOnly
public class HowToController extends BaseController {
    @GetMapping("${controller.paths.howto}")
    public String howToPage() {
        return properties.getPage("howTo");
    }
}
