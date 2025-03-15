package dev.ivank.trchatbotdemo.security;

import dev.ivank.trchatbotdemo.security.auth.ExtendedAuthenticationSuccessHandler;
import dev.ivank.trchatbotdemo.security.auth.service.UserEntityService;
import dev.ivank.trchatbotdemo.common.ControllerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    private final ControllerProperties controllerProperties;
    @Value("${static.whitelisted-resources}")
    private String whitelistedResources;

    @Autowired
    public SecurityConfiguration(ControllerProperties controllerProperties) {
        this.controllerProperties = controllerProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserEntityService userEntityService,
                                                               PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userEntityService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   @Qualifier(ExtendedAuthenticationSuccessHandler.NAME)
                                                   AuthenticationSuccessHandler successHandler) throws Exception {
        RequestMatcher csrfRequestMatcher = request -> csrfProtected().stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(configurer -> configurer.requireCsrfProtectionMatcher(csrfRequestMatcher))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(getPath("index")).permitAll()
                        .requestMatchers(getPath("indexInit")).permitAll()
                        .requestMatchers(getPath("loginApi").concat("/**")).permitAll()
                        .requestMatchers(getPath("signup").concat("/**")).permitAll()
                        .requestMatchers(getPath("signupApi").concat("/**")).permitAll()
                        .requestMatchers(getPath("sessionMsg")).permitAll()
                        .requestMatchers(getPath("tos")).permitAll()
                        .requestMatchers(getPath("langPreference")).permitAll()
                        .requestMatchers(whitelistedResources, "/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(getPath("login"))
                        .loginProcessingUrl(getPath("loginApi"))
                        .permitAll()
                        .defaultSuccessUrl(getPath("index"))
                        .successHandler(successHandler)
                )
                .logout(logout -> logout
                        .logoutUrl(getPath("logout"))
                        .logoutSuccessUrl(getPath("login").concat("?logout"))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .build();
    }

    private String getPath(String key) {
        return controllerProperties.getPath(key);
    }

    private Set<RequestMatcher> csrfProtected() {
        return Set.of(
                getPathRequestMatcher("loginApi"),
                getPathRequestMatcher("logout"),
                getPathRequestMatcher("signupApi"),
                getPathRequestMatcher("chatApi"),
                getPathRequestMatcher("knowledgeBaseUploadJson"),
                getPathRequestMatcher("knowledgeBaseUploadPdf")
        );
    }

    private AntPathRequestMatcher getPathRequestMatcher(String key) {
        return new AntPathRequestMatcher(getPath(key), HttpMethod.POST.name());
    }
}
