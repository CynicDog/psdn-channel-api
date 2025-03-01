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
import reactor.core.publisher.Mono;

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

        // TODO: migrate to blocking behavior
        // Get the access token asynchronously
        Mono<AccessToken> tokenResponse = clientSecretCredential.getToken(new TokenRequestContext().addScopes(scope))
                .map(accessToken -> {

                    // log.info("Access token: {}", accessToken.getToken());
                    log.info("Access token acquired successfully");
                    return accessToken;
                })
                .doOnError(error -> {
                    log.error("Error while acquiring access token: {}", error.getMessage());
                });

        // Subscribe to the Mono and set authentication once the token is acquired
        tokenResponse.subscribe(
                token -> {
                    Authentication authentication = new PreAuthenticatedAuthenticationToken(
                            clientId,           // principal
                            token.getToken()    // credentials
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Authentication set successfully");
                },
                error -> {
                    log.error("Authentication failed: {}", error.getMessage());
                }
        );

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
