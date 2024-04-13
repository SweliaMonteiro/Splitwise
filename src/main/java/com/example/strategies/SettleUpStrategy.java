package com.example.strategies;

import com.example.dto.TransactionDTO;
import com.example.models.Expense;
import java.util.List;

public interface SettleUpStrategy {

    public List<TransactionDTO> settleUp(List<Expense> expensesToSettleUp);

}
