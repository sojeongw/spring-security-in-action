package security.springsecurityinaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import security.springsecurityinaction.filter.CsrfTokenLogger;
import security.springsecurityinaction.repositories.CustomCsrfTokenRepository;
import security.springsecurityinaction.repositories.JpaTokenRepository;

import java.util.List;

@Configuration
public class ProjectConfig {

    private final AuthenticationProvider authenticationProviderService;

    public ProjectConfig(AuthenticationProvider authenticationProviderService) {
        this.authenticationProviderService = authenticationProviderService;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProviderService));
    }

    @Bean
    public CsrfTokenRepository customCsrfTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomCsrfTokenRepository customCsrfTokenRepository) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of());
                config.setAllowedMethods(List.of());
                return config;
            };
            c.configurationSource(source);
        });

        http.csrf(c -> {
                    c.csrfTokenRepository(customCsrfTokenRepository());
                    c.ignoringRequestMatchers("/ciao");
                })
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
