package kr.co.metlife.psdnchannelapi.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleProviderFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RoleProviderFilter.class);

    @Value("${psdn.tenant-id}")
    private String azureTenantId;

    private JwtDecoder jwtDecoder;

    @PostConstruct
    public void init() {
        String issuerUri = String.format("https://login.microsoftonline.com/%s/v2.0", azureTenantId);
        this.jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHeader(request);

        if (token != null && !token.isEmpty()) {
            try {
                // Validate the JWT signature and parse claims
                Jwt jwt = jwtDecoder.decode(token);

                String username = jwt.getClaimAsString("preferred_username");
                List<GrantedAuthority> roles = jwt.getClaimAsStringList("roles").stream()
                        .map(role -> new SimpleGrantedAuthority(role.replace("Role.", "ROLE_")))
                        .collect(Collectors.toList());
                if (!roles.isEmpty()) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roles
                    );
                    log.info("User Authentication set successfully.");

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.error("Failed to retrieve roles from the token");
                }

            } catch (JwtValidationException e) {
                log.error("Failed to decode or validate the JWT token", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"token_expired_or_invalid\"}");
                response.getWriter().flush();
                return;
            }
        }

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
