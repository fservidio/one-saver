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

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application.properties")
class TransactionServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    @Test
    void roundUpTransactions() {
        //init
        String accountUid="c1222b60-96d9-4e53-963a-008a63bc8c76";
        String categoryUid="c1220def-51c0-4225-97f3-4bdcd43b666c";

        TransactionService transactionService = new TransactionService(properties);
        ClientProperties.ClientParams clientParams = properties.getParams().get("transactions");
        Long roundUp = transactionService.getRoundUpValue(accountUid, categoryUid,LocalDate.of(2024,4,18));
        assertTrue(roundUp>0);
    }


}