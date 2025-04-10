package security.springsecurityinaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import security.springsecurityinaction.filter.AuthenticationLoggingFilter;
import security.springsecurityinaction.filter.RequestValidationFilter;
import security.springsecurityinaction.filter.StaticKeyAuthenticationFilter;

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
        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new StaticKeyAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.anyRequest().permitAll());

        return http.build();
    }
}
