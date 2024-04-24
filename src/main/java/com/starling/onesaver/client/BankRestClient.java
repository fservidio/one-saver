package com.starling.onesaver.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 * Class responsible for communicating with the bank API.
 */
public class BankRestClient {

    private final WebClient webClient;

    public BankRestClient(String baseUrl){
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();

    }

    /**
     * Executes GET calls
     * @param token
     * @param urlPath
     * @param queryParam
     * @param pathParam
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T retrieveObjects(String token, String urlPath, MultiValueMap<String, String> queryParam, Map<String, String> pathParam, ParameterizedTypeReference<T> typeReference){
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlPath) //Base-path for invoking the 3rd party service.
                        .queryParams(queryParam)
                        .build(pathParam))

                .headers(h -> {
                    h.setBearerAuth(token);
                    h.setAccept(List.of(MediaType.APPLICATION_JSON));
                    h.setContentType(MediaType.APPLICATION_JSON);
                }
                )

                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    /**
     * Executes PUT calls
     * @param token
     * @param urlPath
     * @param queryParam
     * @param pathParam
     * @param body
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T putObjects(String token, String urlPath, MultiValueMap<String, String> queryParam, Map<String, String> pathParam, Object body, ParameterizedTypeReference<T> typeReference){
        return webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path(urlPath) //Base-path for invoking the 3rd party service.
                        .queryParams(queryParam)
                        .build(pathParam))

                .headers(h -> {
                            h.setBearerAuth(token);
                            h.setAccept(List.of(MediaType.APPLICATION_JSON));
                            h.setContentType(MediaType.APPLICATION_JSON);
                        }
                )
                .body(BodyInserters.fromValue(body))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }
}
