package com.starling.onesaver.controller;

import com.starling.onesaver.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    WebClient webClient;
    public TransactionController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/roundup")
    public @ResponseBody String roundUp(){
        return transactionService.roundUp().toString();
    }

    private static final String RESOURCE_URI = "https://api-sandbox.starlingbank.com/api/v2/accounts/c1228dc8-a35e-45de-ac22-800808ae34c2/balance";

            @GetMapping("/roundround")
    Mono<String> useOauthWithAuthCodeAndAnnotation() {
        Mono<String> retrievedResource = webClient.get()
                .uri(RESOURCE_URI)
                .attributes(clientRegistrationId("starling"))
                .retrieve()
                .bodyToMono(String.class);
        return retrievedResource.map(string -> "We retrieved the following resource using Oauth: " + string
                );
    }
}
