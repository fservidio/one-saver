package com.starling.onesaver.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "client.starling")
@Getter
@Setter
@AllArgsConstructor
public class ClientProperties {


    String token;

    String baseUrl;

    Map<String,ClientParams> params;

    //TODO the following fields are used to store the values temporarily
    String accountUid;
    String categoryUid;

    @Getter
    @Setter
    public static class ClientParams{
        private String urlPath;

        public Map<String,String> pathParams = new HashMap<>();
        public MultiValueMap<String,String> queryParams;
    }
}
