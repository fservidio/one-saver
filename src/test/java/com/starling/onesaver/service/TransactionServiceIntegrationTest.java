package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application.properties")
class TransactionServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    @Test
    void roundUpTransactions() {
        TransactionService transactionService = new TransactionService(properties);
        BigDecimal roundUp = transactionService.roundUp();
        assert(roundUp.doubleValue()>0);
    }

}