package com.test.splitwise.services;

import com.test.splitwise.dto.TransactionDTO;
import com.test.splitwise.models.Expense;
import com.test.splitwise.models.Group;
import com.test.splitwise.models.User;
import com.test.splitwise.models.UserExpense;
import com.test.splitwise.repositories.ExpenseRepository;
import com.test.splitwise.repositories.GroupRepository;
import com.test.splitwise.repositories.UserExpenseRepository;
import com.test.splitwise.repositories.UserRepository;
import com.test.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserExpenseRepository userExpenseRepository;

    @Autowired
    private SettleUpStrategy settleUpStrategy;


    public List<TransactionDTO> settleUpUser(String userName) {
        // Get the User
        Optional<User> userOptional = userRepository.findByName(userName);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Invalid User!");
        }
        User user = userOptional.get();

        // Get all the UserExpenses related to the User
        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(user);

        // Iterate through all the userExpenses to check who owns what
        // There can be duplicate expenses for PAID and HAD_TO_PAY for the same user, we want only unique
        Set<Expense> expenses = new HashSet<>();
        for(UserExpense userExpense : userExpenses){
            expenses.add(userExpense.getExpense());
        }

        // Generate Transactions to settle up
        List<TransactionDTO> transactions = settleUpStrategy.settleUp(expenses.stream().toList());

        // Filter out the Transactions for the given User
        List<TransactionDTO> filteredTransactions = new ArrayList<>();
        for(TransactionDTO transaction : transactions) {
            if(transaction.getSupposeToPay().getUserId()==user.getId() || transaction.getSupposeToReceive().getUserId()==user.getId()) {
                filteredTransactions.add(transaction);
            }
        }

        // If no Transactions found, then throw error
        if(filteredTransactions.isEmpty()) {
            throw new RuntimeException("No active Transactions for the given User.");
        }

        return filteredTransactions;
    }


    public List<TransactionDTO> settleUpGroup(String groupName) {
        // Get the Group
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        if(groupOptional.isEmpty()){
            throw new RuntimeException("Invalid Group!");
        }
        Group group = groupOptional.get();

        // Get all the Expenses in the Group
        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        // Generate Transactions to settle up
        List<TransactionDTO> transactions = settleUpStrategy.settleUp(expenses);

        // If no Transactions found, then throw error
        if(transactions.isEmpty()) {
            throw new RuntimeException("No active Transactions for the given Group name.");
        }

        return transactions;
    }

}
