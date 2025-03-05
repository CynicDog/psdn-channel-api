package kr.co.metlife.pseudomgtchannelapi.proxy.logic;

import jakarta.annotation.PostConstruct;
import kr.co.metlife.pseudomgtchannelapi.proxy.ProxyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
        return restTemplate.getForEntity(baseUrl + url, String.class);
    }
}
