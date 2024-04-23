package com.starling.onesaver.controller;

import com.starling.onesaver.service.AccountService;
import com.starling.onesaver.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@TestPropertySource("classpath:application-operation.properties")
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountService accountService;
    @Test
    void roundUpSuccess() throws Exception {
        LocalDate localDate = LocalDate.of(2002,2,2);
        when(transactionService.getRoundUpValue(localDate)).thenReturn(158L);
        mockMvc.perform(get("/roundup")
                .param("from","2002-02-02"))
                .andExpect(status().isOk())
                .andExpect(content().string("158"));
    }
    @Test
    void whenMissingFromDateThenReturnsBadRequest() throws Exception {
        String content = mockMvc.perform(get("/roundup"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).contains("Required parameter 'from' is not present");
    }
    @Test
    void whenFromDateWithBadFormatThenReturnsBadRequest() throws Exception {
        String content = mockMvc.perform(get("/roundup")
                        .param("from","1234"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).contains("Failed to convert");
    }

}
