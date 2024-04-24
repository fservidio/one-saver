package com.starling.onesaver.service;

import com.starling.model.FeedItem;
import com.starling.model.FeedItems;
import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.util.DateUtil;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for all the operations related to the transactions (belonging to a specific account)
 */
@Service
public class TransactionService {

    @Setter
    private BankRestClient bankRestClient;
    private ClientProperties properties;

    public TransactionService(ClientProperties properties) {
        this.properties = properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
    }

    /**
     * Determines the potential savings from rounding-up all the transactions
     *
     * @return the total amount of the rounding-up from all the transactions, in minor units.
     */
    public Long getRoundUpValue(String accountUid, String categoryUid, LocalDate fromDate) {
        ClientProperties.ClientParams clientParams = properties.getParams().get("transactions");

        String minDateString = DateUtil.convert(fromDate);
        String maxDateString = DateUtil.convert(fromDate.plusWeeks(1));
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("minTransactionTimestamp",List.of(minDateString));
        queryParams.put("maxTransactionTimestamp", List.of(maxDateString));

        Map<String, String> pathParams = new LinkedHashMap<>();
        pathParams.put("accountUid",accountUid);
        pathParams.put("categoryUid",categoryUid);

        FeedItems items = bankRestClient
                .retrieveObjects(
                        properties.getToken(),
                        clientParams.getUrlPath(),
                        queryParams,
                        pathParams,
                        ParameterizedTypeReference.forType(FeedItems.class));

        return items.getFeedItems().stream()
                .filter(f -> f.getSource().equals(FeedItem.SourceEnum.FASTER_PAYMENTS_OUT))
                .mapToLong(f -> ((f.getAmount().getMinorUnits()+99)/100)*100-f.getAmount().getMinorUnits()).sum();


    }
}
