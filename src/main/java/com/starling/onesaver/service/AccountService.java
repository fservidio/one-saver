package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.model.account.Account;
import com.starling.onesaver.model.account.Accounts;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final BankRestClient bankRestClient;
    private final ClientProperties properties;

    public AccountService(ClientProperties properties) {
        this.properties = properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
    }

    public Account getAccount() {
        Accounts accounts = bankRestClient.retrieveObjects(properties, "accounts", ParameterizedTypeReference.forType(Accounts.class));
        assert(accounts!=null);
        assert(accounts.getAccounts().size()>0);
        return accounts.getAccounts().get(0);
    }
}
