package kr.co.metlife.psdnchannelapi.config;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredentialBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AzureAuthFilter extends OncePerRequestFilter {

    @Value("${psdn.tenant-id}")
    private String tenantId;

    @Value("${psdn.client-id}")
    private String clientId;

    @Value("${psdn.client-secret}")
    private String clientSecret;

    private static final Logger log = LoggerFactory.getLogger(AzureAuthFilter.class);

    private TokenCredential clientSecretCredential;

    @PostConstruct
    public void init() {
        log.info("Tenant ID: {}", tenantId);
        log.info("Client ID: {}", clientId);
        log.info("Client Secret: {}", clientSecret);

        clientSecretCredential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String scope = "https://graph.microsoft.com/.default";

        AccessToken token = clientSecretCredential.getToken(new TokenRequestContext().addScopes(scope)).block();

        if (token != null) {
            Authentication authentication = new PreAuthenticatedAuthenticationToken(
                    clientId,           // Service Principal
                    token.getToken()    // REST-API Access Token
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication set successfully");
        } else {
            log.error("Failed to retrieve access token");
        }

        // Proceed with the filter chain (will continue once token is acquired)
        filterChain.doFilter(request, response);
    }
}
