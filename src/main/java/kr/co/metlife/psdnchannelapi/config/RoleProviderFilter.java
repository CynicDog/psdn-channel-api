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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleProviderFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RoleProviderFilter.class);
    private final JwtDecoder jwtDecoder;

    public RoleProviderFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extract the JWT token from the "Authorization" header
        String token = getTokenFromHeader(request);

        if (token != null && !token.isEmpty()) {
            // Validate the JWT signature and parse claims
            Jwt jwt = jwtDecoder.decode(token);

            String username = jwt.getClaimAsString("preferred_username");
            List<GrantedAuthority> roles = jwt.getClaimAsStringList("roles").stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (!roles.isEmpty()) {
                Authentication authentication = new PreAuthenticatedAuthenticationToken(
                        username,
                        roles
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User Authentication set successfully");
            } else {
                log.error("Failed to retrieve access token");
            }

        }

        // Continue the filter chain (call next filter or controller)
        filterChain.doFilter(request, response);
    }

    // Helper method to extract token from the Authorization header
    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Extract token after "Bearer "
        }
        return null;
    }
}
