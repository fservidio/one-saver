package com.starling.onesaver.e2e;

import com.starling.onesaver.OneSaverApplication;
import com.starling.onesaver.client.ClientProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-operation.properties")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OneSaverApplication.class)
@AutoConfigureMockMvc
public class RoundupOperationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ClientProperties properties;

    @Test
    public void whenAccountExistsAndSavingGoalExistsThenRoundUpAndSave() throws Exception {

        //get the account balance
        String balanceResult = mvc.perform(get("/account-balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("accountUid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        Long balance = Long.valueOf(balanceResult);

// check values of properties fields
        assertThat(properties.getCategoryUid()).isNotEmpty();
        assertThat(properties.getAccountUid()).isNotEmpty();
        //get potential roundup

        String roundupResult = mvc.perform(get("/roundup")
                    .param("from","2024-04-18")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("accountUid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        Long roundup = Long.valueOf(roundupResult);

        //get saving goal balance

        String savingGoalResult = mvc.perform(get("/savinggoal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()

                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        Long savingGoalBalance = Long.valueOf(savingGoalResult);
        //execute roundup operation

        String savingGoalPerformed = mvc.perform(put("/save-roundup")
                        .param("from","2024-04-18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        assertThat(Boolean.valueOf(savingGoalPerformed)).isTrue();

        //get the account balance
        String balanceResultAfterTopup = mvc.perform(get("/account-balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("accountUid", notNullValue()))
                .andReturn().getResponse().getContentAsString();
        Long balanceAfterTopup = Long.valueOf(balanceResultAfterTopup);

        //verify account balance - roundup
        assertThat(balanceAfterTopup).isEqualTo(balance-roundup);
        //verify saving goal balance + roundup
        String savingGoalAfterTopupResult = mvc.perform(get("/savinggoal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()

                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        Long savingGoalAfterTopup = Long.valueOf(savingGoalAfterTopupResult);

        assertThat(savingGoalAfterTopup).isEqualTo(savingGoalBalance+roundup);
    }
}