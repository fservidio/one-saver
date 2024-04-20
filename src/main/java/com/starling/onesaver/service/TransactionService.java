package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service class for all the operations related to the transactions (belonging to a specific account)
 */
@Service
public class TransactionService {

    @Autowired
    BankRestClient bankRestClient;

    /**
     * Determines the potential savings from rounding-up all the transactions
     * @return the total amount of the rounding-up from all the transactions, in BigDecimal.
     */
    public BigDecimal roundUp(){
        return bankRestClient
                .getAmountFromAllTransactions()
                .stream()
                .map(i->i.setScale(0, RoundingMode.CEILING).subtract(i))
                .reduce((x,y)->x.add(y))
                .get();

    }
}
