package kr.co.metlife.pseudomgtchannelapi.proxy.logic;

import jakarta.annotation.PostConstruct;
import kr.co.metlife.pseudomgtchannelapi.proxy.ProxyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class SystemProxyLogic implements ProxyService {

    @Value("${info.remote-server.system.base-url}")
    private String baseUrl;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    private HttpEntity<Object> createJsonRequest(Object payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(payload, headers);
    }

    @Override
    public ResponseEntity<String> delete(String url) {
        return restTemplate.exchange(baseUrl + url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    @Override
    public ResponseEntity<String> put(String url, Object payload) {
        // TODO
        return null;
    }

    @Override
    public ResponseEntity<String> post(String url, Object payload) {
        HttpEntity<Object> requestEntity = createJsonRequest(payload);
        return restTemplate.postForEntity(baseUrl + url, requestEntity, String.class);
    }

    @Override
    public ResponseEntity<String> get(String url) {
        return restTemplate.getForEntity(baseUrl + url, String.class);
    }
}
