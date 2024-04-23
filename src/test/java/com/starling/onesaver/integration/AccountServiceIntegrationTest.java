package com.starling.onesaver.integration;

import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application-account.properties")

class AccountServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    @Test
    void getAccount() {
        AccountService accountService = new AccountService(properties);
        assertNotNull(accountService.getAccount());
    }
}