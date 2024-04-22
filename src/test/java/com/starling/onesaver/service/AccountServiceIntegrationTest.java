package com.starling.onesaver.service;

import com.starling.onesaver.client.ClientProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application.properties")

class AccountServiceIntegrationTest {

    @Autowired
    ClientProperties properties;
    @Test
    void getAccount() {
        AccountService accountService = new AccountService(properties);
        assertNotNull(accountService.getAccount());
    }
}