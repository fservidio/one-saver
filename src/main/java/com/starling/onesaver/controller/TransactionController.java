package com.starling.onesaver.controller;

import com.starling.onesaver.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/roundup")
    public @ResponseBody String roundUp(){
        return transactionService.roundUp().toString();
    }
}
