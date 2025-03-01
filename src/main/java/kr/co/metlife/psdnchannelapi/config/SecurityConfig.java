package kr.co.metlife.psdnchannelapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AzureAuthFilter azureAuthFilter;
    private final RoleProviderFilter roleProviderFilter;

    public SecurityConfig(AzureAuthFilter azureAuthFilter, RoleProviderFilter roleProviderFilter) {
        this.azureAuthFilter = azureAuthFilter;
        this.roleProviderFilter = roleProviderFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .addFilterBefore(azureAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(roleProviderFilter, AzureAuthFilter.class)
                .build();
    }
}
