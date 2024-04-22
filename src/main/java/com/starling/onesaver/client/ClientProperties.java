package com.starling.onesaver.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "client.starling")
@Component
@Getter
@Setter
@AllArgsConstructor
public class ClientProperties {


    String token;

    String baseUrl;

    Map<String,ClientParams> params;


    @Getter
    @Setter
    public static class ClientParams{
        private String urlPath;

        public Map<String,String> pathParams = new HashMap<>();
        public MultiValueMap<String,String> queryParams;
    }
}
