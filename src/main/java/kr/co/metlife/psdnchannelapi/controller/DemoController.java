package kr.co.metlife.psdnchannelapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/greet")
    public String greet() {

        String accessToken = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getCredentials();

        logger.info(accessToken);

        return accessToken;
    }
}
