package com.starling.onesaver.controller;

import com.starling.onesaver.service.OperationService;
import com.starling.onesaver.service.SavingGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Boolean saveRoundUp(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate) {
        return operationService.saveRoundUp(fromDate);
    }

}
