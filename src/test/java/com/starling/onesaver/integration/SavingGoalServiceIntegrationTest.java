package com.starling.onesaver.integration;

import com.starling.model.SavingsGoalsV2;
import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.service.AccountService;
import com.starling.onesaver.service.SavingGoalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientProperties.class)
@TestPropertySource("classpath:application-operation.properties")

class SavingGoalServiceIntegrationTest {

    @Autowired
    ClientProperties properties;

    AccountService accountService;
    @Test
    void getAccount() {
        accountService = new AccountService(properties);
        SavingGoalService savingGoalService = new SavingGoalService(properties,accountService);
        SavingsGoalsV2 result = savingGoalService.getSavingGoal();
        assertNotNull(result);
    }
}