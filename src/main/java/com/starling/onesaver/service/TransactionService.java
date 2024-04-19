package com.starling.onesaver.service;

import com.starling.onesaver.client.BankRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransactionService {

    @Autowired
    BankRestClient bankRestClient;

    public BigDecimal roundUp(){
        return bankRestClient
                .getAmountFromAllTransactions()
                .stream()
                .map(i->i.setScale(0, RoundingMode.CEILING).subtract(i))
                .reduce((x,y)->x.add(y))
                .get();

    }
}
