package com.starling.onesaver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.starling.model.FeedItems;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    BankRestClient bankRestClient;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    ClientProperties properties;


    @BeforeEach
    public void setUp(){
        transactionService= new TransactionService(properties);
        transactionService.setBankRestClient(bankRestClient);
    }
    /**
     * Given 3 distinct transactions,
     * When the roundUp method is called,
     * Then that the round-up method returns the expected potential saving is returned.
     */
    @Test
    void roundUpThreeTransactions() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        FeedItems feedItems = objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("feedItems.json"),FeedItems.class);

        when(bankRestClient.retrieveObjects(any(),any(),any(),any(), any()))
                .thenReturn(feedItems);
        assertEquals(158L,transactionService.getRoundUpValue("an-account","a-category", LocalDate.now()));
    }
}