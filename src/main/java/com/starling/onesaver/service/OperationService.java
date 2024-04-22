package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.model.account.Account;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OperationService {

    private final ClientProperties properties;

    private final AccountService accountService;
    private final TransactionService transactionService;

    public OperationService(ClientProperties properties, AccountService accountService, TransactionService transactionService) {
        this.properties = properties;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void executeRoundUp() {

        // get the account id and the category id
        Account account = accountService.getAccount();
        // set the params for the transactions service to get the roundup value
        properties.getParams().get("transactions").pathParams.put("accountId", account.getAccountUid());
        properties.getParams().get("transactions").pathParams.put("categoryId", account.getDefaultCategory());
        BigDecimal roundUp = transactionService.roundUp();
        assert(roundUp.doubleValue()>0);
        //put the roundup value into a saving goal

    }
}
