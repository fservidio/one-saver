package com.starling.onesaver.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains and handles the property values for the parameters needed in the WebClient
 * such as url path, plus it stores the values of accountId and categoryId once fetched from the API
 */
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "client.starling")
@Validated
@Getter
@Setter
@AllArgsConstructor
public class ClientProperties {

    @NotNull
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
