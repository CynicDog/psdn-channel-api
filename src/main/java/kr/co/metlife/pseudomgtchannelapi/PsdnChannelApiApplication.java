package kr.co.metlife.pseudomgtchannelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PsdnChannelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsdnChannelApiApplication.class, args);
    }

}
