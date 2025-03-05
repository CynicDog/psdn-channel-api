package kr.co.metlife.pseudomgtchannelapi.proxy;

import org.springframework.http.ResponseEntity;

public interface ProxyService {

    ResponseEntity<String> get(String url);

    ResponseEntity<String> post(String url, Object payload);

    ResponseEntity<String> put(String url, Object payload);

    ResponseEntity<String> delete(String url);
}
