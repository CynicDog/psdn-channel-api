package kr.co.metlife.psdnchannelapi.controller;

import kr.co.metlife.psdnchannelapi.proxy.AzureProxy;
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

    private final AzureProxy azureProxy;

    public UserController(AzureProxy azureProxy) {
        this.azureProxy = azureProxy;
    }

    @PreAuthorize("hasRole('ROLE_Application')")
    @GetMapping("/greetAsApplication")
    public ResponseEntity<String> greetAsApplication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>("Authentication is missing", HttpStatus.UNAUTHORIZED);
        }

        boolean isApplication = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Application"));

        // Return appropriate message
        if (isApplication) {
            return new ResponseEntity<>("Hello, Application!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_Application', 'ROLE_Admin')")
    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsers() {
        return azureProxy.getUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_Application', 'ROLE_Admin')")
    @GetMapping("/getAppRoles")
    public ResponseEntity<String> getAppRoleAssignments() {
        return azureProxy.getAppRoles();
    }
}
