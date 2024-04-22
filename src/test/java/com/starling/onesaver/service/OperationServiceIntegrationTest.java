package com.starling.onesaver.service;

import com.starling.onesaver.client.ClientProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application-operation.properties")
class OperationServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    private AccountService accountService;
    private TransactionService transactionService;

    @Test
    void executeRoundUp() {
        accountService = new AccountService(properties);
        transactionService = new TransactionService(properties);
        OperationService service = new OperationService(properties,accountService,transactionService);
        service.executeRoundUp();
    }

}