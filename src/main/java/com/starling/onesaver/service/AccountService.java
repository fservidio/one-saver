package com.starling.onesaver.service;

import com.starling.model.AccountV2;
import com.starling.model.Accounts;
import com.starling.model.BalanceV2;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for getting Account details and Account balance
 */
@Service
public class AccountService {
    private final ClientProperties properties;

    public AccountService(ClientProperties properties) {
        this.properties = properties;
    }

    /**
     * Get the details of the primary account from the current account holder, assumes that there is one primary account only
     * @return the object containing the details of the primary account
     */
    public AccountV2 getAccount() {

        ClientProperties.ClientParams clientParams = properties.getParams().get("accounts");
        Accounts accounts = WebClient.builder().baseUrl(properties.getBaseUrl()).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(clientParams.getUrlPath()) //Base-path for invoking the 3rd party service.
                        .build())
                .headers(h -> {
                            h.setBearerAuth(properties.getToken());
                            h.setAccept(List.of(MediaType.APPLICATION_JSON));
                            h.setContentType(MediaType.APPLICATION_JSON);
                        }
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Accounts.class)
                .block();

        return getPrimaryAccount(accounts);
    }

    private AccountV2 getPrimaryAccount(Accounts accounts) {
        assert accounts.getAccounts() != null;
        List<AccountV2> primaryAccounts = accounts.getAccounts().stream()
                .filter(a -> {
                    assert a.getAccountType() != null;
                    return a.getAccountType().equals(AccountV2.AccountTypeEnum.PRIMARY);
                })
                .toList();
        assert(primaryAccounts.size()==1);
        AccountV2 account = primaryAccounts.get(0);

        //update properties fields
        Optional.ofNullable(account.getAccountUid()).ifPresent(a->properties.setAccountUid(a.toString()));
        Optional.ofNullable(account.getDefaultCategory()).ifPresent(a->properties.setCategoryUid(a.toString()));
        assert account.getDefaultCategory() != null;
        properties.setCategoryUid(account.getDefaultCategory().toString());

        return account;
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
        assert account.getAccountUid() != null;
        properties.getParams().get("account-balance").pathParams.put("accountUid", account.getAccountUid().toString());
        ClientProperties.ClientParams clientParams = properties.getParams().get("account-balance");


        BalanceV2 balance = WebClient.builder().baseUrl(properties.getBaseUrl()).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(clientParams.getUrlPath())
                        .build(clientParams.getPathParams()))

                .headers(h -> {
                            h.setBearerAuth(properties.getToken());
                            h.setAccept(List.of(MediaType.APPLICATION_JSON));
                            h.setContentType(MediaType.APPLICATION_JSON);
                        }
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BalanceV2.class)
                .block();

        assert(balance!=null);
        assert balance.getClearedBalance() != null;
        return balance.getClearedBalance().getMinorUnits();
    }
}
