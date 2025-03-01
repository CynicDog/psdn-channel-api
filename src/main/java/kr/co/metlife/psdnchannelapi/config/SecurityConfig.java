package kr.co.metlife.psdnchannelapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AzureAuthFilter azureAuthFilter;

    public SecurityConfig(AzureAuthFilter azureAuthFilter) {
        this.azureAuthFilter = azureAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .addFilterBefore(azureAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
