package dev.ivank.trchatbotdemo.chat;

import dev.ivank.trchatbotdemo.chat.dto.ChatFeedback;
import dev.ivank.trchatbotdemo.chat.dto.ChatInteractionDto;
import dev.ivank.trchatbotdemo.chat.dto.ConversationDto;
import dev.ivank.trchatbotdemo.chat.service.ChatClientService;
import dev.ivank.trchatbotdemo.chat.service.ChatInteractionPromptService;
import dev.ivank.trchatbotdemo.common.BaseController;
import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.domain.ReportStatus;
import dev.ivank.trchatbotdemo.security.auth.VisitorOnly;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.security.auth.service.AuthenticationHolderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Controller
@SessionAttributes("userReport")
@VisitorOnly
public class ChatBotController extends BaseController {
    private final ChatInteractionPromptService promptService;
    private final ChatClientService chatClientService;

    @ModelAttribute("userReport")
    public Report initUserReport(AuthenticationHolderService authenticationHolderService) {
        Optional<User> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
        if (currentAuthentication.isPresent()) {
            User user = currentAuthentication.get();
            if (user.getRole().isVisitor()) {
                Report report = new Report();
                report.setUser(user);
                return report;
            } else {
                throw new AccessDeniedException(
                        "User %s not a visitor."
                                .formatted(user.getEmail())
                );
            }
        }
        throw new IllegalStateException("User has to be authenticated.");
    }

    @Autowired
    public ChatBotController(ChatInteractionPromptService promptService,
                             ChatClientService chatClientService) {
        this.promptService = promptService;
        this.chatClientService = chatClientService;
    }

    // TODO: apply prompt validation
    @GetMapping(value = "${controller.paths.chatApi}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<ChatInteractionDto> chat(@ModelAttribute("userReport") Report report,
                                         @RequestParam("prompt") String prompt,
                                         HttpSession session) {
        return promptService.chat(prompt, report, cr -> session.setAttribute("userReport", cr));
    }

    @GetMapping("${controller.paths.chatApi}/conversation/current")
    public ResponseEntity<ConversationDto> currentConversation(@ModelAttribute("userReport") Report report) {
        return ResponseEntity.ok(chatClientService.getOngoingConversation(report));
    }

    @PostMapping("${controller.paths.chatApi}/feedback")
    public ResponseEntity<ReportStatus> chatFeedback(@ModelAttribute("userReport") Report report,
                                                     @RequestParam("res") Optional<ChatFeedback> res) {
        return ResponseEntity.ok(chatClientService.changeConversationStatus(report, res.orElse(ChatFeedback.NEGATIVE)));
    }
}
