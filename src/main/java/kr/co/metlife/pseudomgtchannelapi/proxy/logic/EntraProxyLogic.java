package kr.co.metlife.pseudomgtchannelapi.proxy.logic;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import jakarta.annotation.PostConstruct;
import kr.co.metlife.pseudomgtchannelapi.proxy.ProxyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class EntraProxyLogic implements ProxyService {

    @Value("${info.remote-server.entra.base-url}")
    private String baseUrl;

    @Value("${info.tenant.id}")
    private String tenantId;

    @Value("${info.client.id}")
    private String clientId;

    @Value("${info.client.secret}")
    private String clientSecret;

    @Value("${info.client.name}")
    private String clientName;

    private RestTemplate restTemplate;
    private TokenCredential clientSecretCredential;

    @PostConstruct
    public void init() {

        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));

        clientSecretCredential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Override
    public ResponseEntity<String> delete(String url) {
        // TODO
        return null;
    }

    @Override
    public ResponseEntity<String> put(String url, Object payload) {
        // TODO
        return null;
    }

    @Override
    public ResponseEntity<String> post(String url, Object payload) {
        // TODO
        return null;
    }

    @Override
    public ResponseEntity<String> get(String url) {
        // TODO
        return null;
    }
}
