package com.starling.onesaver;

import com.starling.onesaver.client.ClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ClientProperties.class})
public class OneSaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneSaverApplication.class, args);
    }

}
