package com.starling.onesaver.service;

import com.starling.model.AccountV2;
import com.starling.model.SavingsGoalV2;
import com.starling.onesaver.client.ClientProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service responsible for performing operations on the account
 */
@Service
public class OperationService {

    private final ClientProperties properties;

    private final AccountService accountService;
    private final TransactionService transactionService;

    private final SavingGoalService savingGoalService;

    public OperationService(ClientProperties properties, AccountService accountService, TransactionService transactionService, SavingGoalService savingGoalService) {
        this.properties = properties;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.savingGoalService = savingGoalService;
    }

    /**
     * Performs the transfer of the roundup amount from the primary account to the saving space
     * @return true if the transfer was successful
     */
    public Boolean saveRoundUp(LocalDate fromDate){
        //get amount to save
        Long amount = transactionService.getRoundUpValue(fromDate);
        // get the account id and the category id
        AccountV2 account = accountService.getAccount();
        SavingsGoalV2 savingGoal = savingGoalService.getSavingGoal().getSavingsGoalList().get(0);
        //put the roundup value into a saving goal
        return savingGoalService.putIntoSavingGoal(account.getAccountUid(),savingGoal.getSavingsGoalUid(),amount).getSuccess();
    }
}
