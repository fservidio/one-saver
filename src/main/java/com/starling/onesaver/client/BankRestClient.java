package com.starling.onesaver.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Class responsible for communicating with the bank API
 */
@Component
public class BankRestClient {


    private final WebClient webClient;

    public BankRestClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public <T> T retrieveObjects(ClientProperties properties,String type, ParameterizedTypeReference<T> typeReference){

        ClientProperties.ClientParams clientParams = properties.params.get(type);
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(clientParams.getUrlPath()) //Base-path for invoking the 3rd party service.
                        .queryParams(clientParams.getQueryParams())
                        .build(clientParams.getPathParams()))

                .headers(h -> {
                    h.setBearerAuth(properties.token);
                    h.setAccept(List.of(MediaType.APPLICATION_JSON));
                    h.setContentType(MediaType.APPLICATION_JSON);
                }
                )

                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(typeReference)
                .onErrorMap(Exception.class, ex -> new Exception("Generic exception", ex))
                .block();

    }
}
