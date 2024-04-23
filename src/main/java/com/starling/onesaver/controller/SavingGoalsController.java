package com.starling.onesaver.controller;

import com.starling.model.SavingsGoalTransferResponseV2;
import com.starling.onesaver.service.OperationService;
import com.starling.onesaver.service.SavingGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SavingGoalsController {

    @Autowired
    SavingGoalService savingGoalService;

    @Autowired
    OperationService operationService;


    @GetMapping("/savinggoal")
    public @ResponseBody Long getSavingGoalBalance(){
        return savingGoalService.getSavingGoalBalance();
    }

    @PutMapping("/save-roundup")
    public @ResponseBody Boolean saveRoundUp(LocalDate fromDate){
        return operationService.saveRoundUp(fromDate);
    }

}
