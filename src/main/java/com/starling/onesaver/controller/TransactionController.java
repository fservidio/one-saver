package com.starling.onesaver.controller;

import com.starling.onesaver.service.AccountService;
import com.starling.onesaver.service.OperationService;
import com.starling.onesaver.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@Validated
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;

    @GetMapping("/account-balance")
    public @ResponseBody Long getAccount(){
        return accountService.getAccountBalance();

    }

    @GetMapping("/roundup")
    public @ResponseBody Long getRoundUp(@RequestParam ("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate) {
        return transactionService.getRoundUpValue(fromDate);
    }


}
