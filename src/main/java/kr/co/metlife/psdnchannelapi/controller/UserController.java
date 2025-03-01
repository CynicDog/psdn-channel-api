package kr.co.metlife.psdnchannelapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('ROLE_Application')")
    @GetMapping("/greetAsApplication")
    public ResponseEntity<String> greetAsApplication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>("Authentication is missing", HttpStatus.UNAUTHORIZED);
        }

        // Get the roles of the authenticated user (assuming roles are included in the JWT token)
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Application"));

        // Return appropriate message
        if (isAdmin) {
            return new ResponseEntity<>("Hello, Application!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.FORBIDDEN);
        }
    }
}
