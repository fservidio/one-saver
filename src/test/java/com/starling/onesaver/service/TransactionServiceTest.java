package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    BankRestClient bankRestClient;

    /**
     * Given 3 distinct transactions,
     * When the roundUp method is called,
     * Then that the round-up method returns the expected potential saving is returned.
     */
    @Test
    void roundUpThreeTransactions() {
        Mockito.when(bankRestClient.getAmountFromAllTransactions())
                .thenReturn(List.of(
                        BigDecimal.valueOf(4.35),
                        BigDecimal.valueOf(5.20),
                        BigDecimal.valueOf(0.87)
                ));
        assertEquals(BigDecimal.valueOf(1.58),transactionService.roundUp());
    }

    /**
     * Given 5 distinct transactions,
     * When the roundUp method is called,
     * Then that the round-up method returns the expected potential saving is returned.
     */
    @Test
    void roundUpFiveTransactions() {
        Mockito.when(bankRestClient.getAmountFromAllTransactions())
                .thenReturn(List.of(
                        BigDecimal.valueOf(10.99),
                        BigDecimal.valueOf(11.01),
                        BigDecimal.valueOf(10.95),
                        BigDecimal.valueOf(3.05),
                        BigDecimal.valueOf(8.44)
                        ));

        assertEquals(BigDecimal.valueOf(2.56),transactionService.roundUp());
    }
}