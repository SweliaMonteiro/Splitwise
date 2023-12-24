package com.test.splitwise.strategies;

import com.test.splitwise.dto.TransactionDTO;
import com.test.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {

    public List<TransactionDTO> settleUp(List<Expense> expensesToSettleUp);

}
