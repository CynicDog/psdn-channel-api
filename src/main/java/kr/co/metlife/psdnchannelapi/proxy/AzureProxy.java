package kr.co.metlife.psdnchannelapi.proxy;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredentialBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;


@Component
public class AzureProxy {

    @Value("${psdn.tenant-id}")
    private String tenantId;

    @Value("${psdn.client-id}")
    private String clientId;

    @Value("${psdn.client-secret}")
    private String clientSecret;

    @Value("${psdn.client-display-name}")
    private String clientDisplayName;

    private TokenCredential clientSecretCredential;

    @PostConstruct
    public void init() {
        clientSecretCredential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    private String getAccessToken() {
        String scope = "https://graph.microsoft.com/.default";
        AccessToken accessToken = clientSecretCredential.getToken(new TokenRequestContext().addScopes(scope)).block();
        if (accessToken == null) {
            throw new RuntimeException("Failed to retrieve access token");
        }
        return accessToken.getToken();
    }

    public ResponseEntity<String> getUsers() {

        String endpoint = "https://graph.microsoft.com/v1.0/users";

        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getAppRoles() {

        String endpoint = String.format("https://graph.microsoft.com/v1.0/applications?$select=displayName, appId, appRoles&$filter=startswith(displayName, '%s')", clientDisplayName);

        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);
    }

//    public ResponseEntity<String> getAppRoleAssignments() {
//
//        String endpoint = String.format("https://graph.microsoft.com/v1.0/servicePrincipals(appId='%s')/appRoleAssignments", clientId);
//
//        String token = getAccessToken();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        headers.set("Content-Type", "application/json");
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);
//    }
}