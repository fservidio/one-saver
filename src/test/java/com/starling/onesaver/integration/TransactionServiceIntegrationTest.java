package com.starling.onesaver.integration;

import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application-operation.properties")
class TransactionServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    @Test
    void roundUpTransactions() {
        //init
        properties.setAccountUid("c1222b60-96d9-4e53-963a-008a63bc8c76");
        properties.setCategoryUid("c1220def-51c0-4225-97f3-4bdcd43b666c");

        TransactionService transactionService = new TransactionService(properties);
        ClientProperties.ClientParams clientParams = properties.getParams().get("transactions");
        Long roundUp = transactionService.getRoundUpValue(LocalDate.of(2024,4,2));
        assertTrue(roundUp>0);
    }

}