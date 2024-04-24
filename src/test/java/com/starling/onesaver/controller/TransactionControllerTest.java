package com.starling.onesaver.controller;

import com.starling.model.AccountV2;
import com.starling.onesaver.service.AccountService;
import com.starling.onesaver.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void whenRoundUpMockedWithValidParametersThenCallServices() throws Exception {
        LocalDate localDate = LocalDate.of(2002,2,2);
        AccountV2 account = new AccountV2();
        UUID accountUid = UUID.randomUUID();
        account.setAccountUid(accountUid);
        UUID categoryUid = UUID.randomUUID();
        account.setDefaultCategory(categoryUid);
        when(accountService.getAccount()).thenReturn(account);
        when(transactionService.getRoundUpValue(any(),any(),any())).thenReturn(158L);
        mockMvc.perform(get("/roundup")
                .param("from","2002-02-02"))
                .andExpect(status().isOk())
                .andExpect(content().string("158"));
        ArgumentCaptor<String> acAccount = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> acCategory = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<LocalDate> acFromDate = ArgumentCaptor.forClass(LocalDate.class);
        verify(transactionService).getRoundUpValue(acAccount.capture(),acCategory.capture(),acFromDate.capture());
        assertThat(acFromDate.getValue()).isEqualTo(LocalDate.of(2002,2,2));
        assertThat(acAccount.getValue()).isEqualTo(accountUid.toString());
        assertThat(acCategory.getValue()).isEqualTo(categoryUid.toString());

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

    @Test
    void whenTokenInvalidThenReturnForbiddenException() throws Exception {
        String errorResponseBody="{\"error\":\"invalid_token\",\"error_description\":\"Could not validate provided access token\"}";
        when(accountService.getAccount()).thenThrow(new WebClientResponseException("403 Forbidden from GET https://api-sandbox.starlingbank.com/api/v2/accounts",403,"Forbidden",null, errorResponseBody.getBytes(),null));
        mockMvc.perform(get("/roundup")
                        .param("from","2002-02-02"))
                .andExpect(status().isForbidden());

    }
}
