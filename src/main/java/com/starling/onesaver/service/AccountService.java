package com.starling.onesaver.service;

import com.starling.model.AccountV2;
import com.starling.model.Accounts;
import com.starling.model.BalanceV2;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for getting Account details and Account balance
 */
@Service
public class AccountService {

    private final BankRestClient bankRestClient;
    private final ClientProperties properties;

    public AccountService(ClientProperties properties) {
        this.properties = properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
    }

    /**
     * Get the details of the primary account from the current account holder, assumes that there is one primary account only
     * @return the object containing the details of the primary account
     */
    public AccountV2 getAccount() {
        ClientProperties.ClientParams clientParams = properties.getParams().get("accounts");

        Accounts accounts = bankRestClient.retrieveObjects(
                properties.getToken(),
                clientParams.getUrlPath(),
                null,
                new HashMap<>(),
                ParameterizedTypeReference.forType(Accounts.class));

        assert accounts.getAccounts() != null;
        List<AccountV2> primaryAccounts = accounts.getAccounts().stream()
                .filter(a -> a.getAccountType().equals(AccountV2.AccountTypeEnum.PRIMARY))
                .collect(Collectors.toList());
        assert(primaryAccounts.size()==1);
        return primaryAccounts.get(0);
    }

    /**
     *
     * @return the cleared balance in minor units of the primary account
     */
    public Long getAccountBalance() {
        // get the account id and the category id
        AccountV2 account = getAccount();
        assert(account!=null);
        // set the params for the transactions service to get the account balance
        properties.getParams().get("account-balance").pathParams.put("accountUid", account.getAccountUid().toString());
        ClientProperties.ClientParams clientParams = properties.getParams().get("account-balance");
        BalanceV2 accounts = bankRestClient.retrieveObjects(
                properties.getToken(),
                clientParams.getUrlPath(),
                null,
                clientParams.getPathParams(),
                ParameterizedTypeReference.forType(BalanceV2.class));
        assert(accounts!=null);
        return accounts.getClearedBalance().getMinorUnits();
    }
}
