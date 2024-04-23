package com.starling.onesaver.service;

import com.starling.model.*;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service responsible for retrieving info about the saving goals and performing transfers to saving spaces
 */
@Service
public class SavingGoalService {

    private final BankRestClient bankRestClient;
    private final ClientProperties properties;

    private final AccountService accountService;

    public SavingGoalService(ClientProperties properties, AccountService accountService) {
        this.properties = properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
        this.accountService = new AccountService(properties);
    }

    /**
     *
     * @return the details of an existing saving space
     */
    public SavingsGoalsV2 getSavingGoal() {
        AccountV2 account = accountService.getAccount();
        ClientProperties.ClientParams clientParams = properties.getParams().get("saving-goal");
        Map<String,String> pathParams=new HashMap<>();
        pathParams.put("accountUid",account.getAccountUid().toString());
        return bankRestClient.retrieveObjects(
                properties.getToken(),
                clientParams.getUrlPath(),
                null,
                pathParams,
                ParameterizedTypeReference.forType(SavingsGoalsV2.class));
    }

    /**
     * Transfers money into a saving space
     * @param amount
     * @return
     */
    public SavingsGoalTransferResponseV2 putIntoSavingGoal(Long amount){
        SavingsGoalsV2 savingGoal = this.getSavingGoal();
        ClientProperties.ClientParams clientParams = properties.getParams().get("topup-saving-goal");
        Map<String,String> pathParams=new HashMap<>();
        //accountService has been called, so accountUid is in properties
        pathParams.put("accountUid",properties.getAccountUid());
        //TODO to improve
        pathParams.put("savingsGoalUid",savingGoal.getSavingsGoalList().get(0).getSavingsGoalUid().toString());
        pathParams.put("transferUid",UUID.randomUUID().toString());
        TopUpRequestV2 topup = new TopUpRequestV2();
        CurrencyAndAmount currencyAndAmount = new CurrencyAndAmount();
        currencyAndAmount.currency("GBP");
        currencyAndAmount.minorUnits(amount);
        topup.amount(currencyAndAmount);
        return bankRestClient.putObjects(
                properties.getToken(),
                clientParams.getUrlPath(),
                null,
                pathParams,
                topup,
                ParameterizedTypeReference.forType(SavingsGoalTransferResponseV2.class));
    }
}
