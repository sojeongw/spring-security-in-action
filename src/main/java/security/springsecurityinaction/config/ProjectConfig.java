package security.springsecurityinaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/hello").hasRole("ADMIN")
                .anyRequest().authenticated());

        return http.build();
    }
}
