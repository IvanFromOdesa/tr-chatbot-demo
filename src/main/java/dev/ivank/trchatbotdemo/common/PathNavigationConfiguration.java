package dev.ivank.trchatbotdemo.common;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class PathNavigationConfiguration {
    private final ControllerProperties controllerProperties;

    @Autowired
    public PathNavigationConfiguration(ControllerProperties controllerProperties) {
        this.controllerProperties = controllerProperties;
    }

    @Bean
    public Map<Role, List<PathNavigation>> rolePathNavigationMap() {
        enum Path {
            HOME("home"),
            HOME_INIT("homeInit"),
            LOGIN("login"),
            LOGIN_INIT("loginInit"),
            LOGIN_SUBMIT("loginSubmit"),
            SIGNUP("signup"),
            SIGNUP_INIT("signupInit"),
            SIGNUP_SUBMIT("signupSubmit"),
            SESSION_MSG("sessionMsg"),
            LANG_PREFERENCE("langPreference"),
            LOGOUT("logout"),
            CHAT("chat"),
            CHAT_FEEDBACK("chatFeedback"),
            CHAT_CURRENT_CONVERSATION("currentConversation"),
            KNOWLEDGE_BASE("knowledgeBase"),
            KNOWLEDGE_BASE_INIT("knowledgeBaseInit"),
            KNOWLEDGE_BASE_UPLOAD_PDF("knowledgeBaseUploadPdf"),
            KNOWLEDGE_BASE_UPLOAD_JSON("knowledgeBaseUploadJson"),
            KNOWLEDGE_BASE_GET_PDF("knowledgeBaseGetPdfs"),
            KNOWLEDGE_BASE_GET_QA("knowledgeBaseGetQAs"),
            KNOWLEDGE_BASE_PREVIEW_PDF("knowledgeBasePdfPreview"),
            KNOWLEDGE_BASE_DOWNLOAD_PDF("knowledgeBasePdfDownload"),
            REPORT("report"),
            REPORT_INIT("reportInit"),
            REPORT_DOWNLOAD("reportDownload"),
            TOS("tos"),
            HOW_TO("howTo"),
            AVATAR_ICONS("avatarIcons");

            private final String name;

            Path(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        List<PathNavigation> common = List.of(
                new PathNavigation(Path.HOME.getName(), getPath("index")),
                new PathNavigation(Path.HOME_INIT.getName(), getPath("indexInit")),
                new PathNavigation(Path.SESSION_MSG.getName(), getPath("sessionMsg")),
                new PathNavigation(Path.LANG_PREFERENCE.getName(), getPath("langPreference")),
                new PathNavigation(Path.AVATAR_ICONS.getName(), getPath("avatarIcons"))
        );

        List<PathNavigation> anonymousPaths = List.copyOf(
                Stream.concat(common.stream(), Stream.of(
                        new PathNavigation(Path.LOGIN.getName(), getPath("login")),
                        new PathNavigation(Path.LOGIN_INIT.getName(), getPath("loginApi").concat("/init")),
                        new PathNavigation(Path.LOGIN_SUBMIT.getName(), getPath("loginApi")),
                        new PathNavigation(Path.SIGNUP.getName(), getPath("signup")),
                        new PathNavigation(Path.SIGNUP_INIT.getName(), getPath("signupApi").concat("/init")),
                        new PathNavigation(Path.SIGNUP_SUBMIT.getName(), getPath("signupApi")),
                        new PathNavigation(Path.TOS.getName(), getPath("tos"))
                )).toList()
        );

        List<PathNavigation> visitorPaths = List.copyOf(
                Stream.concat(common.stream(), Stream.of(
                        new PathNavigation(Path.LOGOUT.getName(), getPath("logout")),
                        new PathNavigation(Path.CHAT.getName(), getPath("chatApi")),
                        new PathNavigation(Path.CHAT_FEEDBACK.getName(), getPath("chatApi").concat("/feedback")),
                        new PathNavigation(Path.CHAT_CURRENT_CONVERSATION.getName(), getPath("chatApi").concat("/conversation/current"))
                )).toList()
        );

        List<PathNavigation> employeePaths = List.copyOf(
                Stream.concat(common.stream(), Stream.of(
                        new PathNavigation(Path.KNOWLEDGE_BASE.getName(), getPath("knowledgeBase")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_INIT.getName(), getPath("knowledgeBaseApi").concat("/init")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_UPLOAD_PDF.getName(), getPath("knowledgeBaseUploadPdf")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_UPLOAD_JSON.getName(), getPath("knowledgeBaseUploadJson")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_GET_QA.getName(), getPath("knowledgeBaseGetQAs")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_GET_PDF.getName(), getPath("knowledgeBaseGetPdfs")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_PREVIEW_PDF.getName(), getPath("knowledgeBasePdfPreview")),
                        new PathNavigation(Path.KNOWLEDGE_BASE_DOWNLOAD_PDF.getName(), getPath("knowledgeBasePdfDownload")),
                        new PathNavigation(Path.REPORT.getName(), getPath("report")),
                        new PathNavigation(Path.REPORT_INIT.getName(), getPath("reportApi").concat("/init")),
                        new PathNavigation(Path.REPORT_DOWNLOAD.getName(), getPath("reportDownload")),
                        new PathNavigation(Path.HOW_TO.getName(), getPath("howTo")),
                        new PathNavigation(Path.LOGOUT.getName(), getPath("logout"))
                )).toList()
        );

        return Map.of(
                Role.ANONYMOUS, anonymousPaths,
                Role.VISITOR, visitorPaths,
                Role.EMPLOYEE, employeePaths
        );
    }

    private String getPath(String key) {
        return controllerProperties.getPath(key);
    }
}
