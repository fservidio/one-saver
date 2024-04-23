package com.starling.onesaver.service;

import com.starling.model.CurrencyAndAmount;
import com.starling.model.SavingsGoalTransferResponseV2;
import com.starling.model.SavingsGoalsV2;
import com.starling.model.TopUpRequestV2;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    public SavingGoalService(ClientProperties properties) {
        this.properties = properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
    }

    /**
     *
     * @return the details of an existing saving space
     */
    public SavingsGoalsV2 getSavingGoal() {
        ClientProperties.ClientParams clientParams = properties.getParams().get("saving-goal");
        return bankRestClient.retrieveObjects(
                properties.getToken(),
                clientParams.getUrlPath(),
                null,
                clientParams.pathParams,
                ParameterizedTypeReference.forType(SavingsGoalsV2.class));
    }

    /**
     *
     * @return the amount of money saved into a saving space
     */
    public Long getSavingGoalBalance() {
        SavingsGoalsV2 savingGoal = getSavingGoal();
        assert savingGoal.getSavingsGoalList().get(0).getTotalSaved() != null;
        return savingGoal.getSavingsGoalList().get(0).getTotalSaved().getMinorUnits();
    }

    /**
     * Transfers money into a saving space
     * @param accountUid
     * @param savingsGoalUid
     * @param amount
     * @return
     */
    public SavingsGoalTransferResponseV2 putIntoSavingGoal(UUID accountUid, UUID savingsGoalUid, Long amount){
        ClientProperties.ClientParams clientParams = properties.getParams().get("topup-saving-goal");
        Map<String,String> pathParams=new HashMap<>();
        pathParams.put("accountUid",accountUid.toString());
        pathParams.put("savingsGoalUid",savingsGoalUid.toString());
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
