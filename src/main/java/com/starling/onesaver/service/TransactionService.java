package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import com.starling.onesaver.client.ClientProperties;
import com.starling.onesaver.model.transaction.FeedItems;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for all the operations related to the transactions (belonging to a specific account)
 */
@Service
public class TransactionService {

    private final BankRestClient bankRestClient;
    private final ClientProperties properties;

    public TransactionService(ClientProperties properties) {
        this.properties=properties;
        this.bankRestClient = new BankRestClient(properties.getBaseUrl());
    }

    /**
     * Determines the potential savings from rounding-up all the transactions
     * @return the total amount of the rounding-up from all the transactions, in BigDecimal.
     */
    public BigDecimal roundUp(){


                FeedItems items = bankRestClient
                .retrieveObjects(properties,"transactions",ParameterizedTypeReference.forType(FeedItems.class));


        List<BigDecimal> amount =items.getFeedItems().stream()
                .filter(f -> f.getSource().equals("FASTER_PAYMENTS_OUT"))
                .map(f -> BigDecimal.valueOf(f.getAmount().getMinorUnits()).divide(BigDecimal.valueOf(100.00)))
                .collect(Collectors.toList());

        return amount
                .stream()
                .map(i->i.setScale(0, RoundingMode.CEILING).subtract(i))
                .reduce((x,y)->x.add(y))
                .get();

    }
}
