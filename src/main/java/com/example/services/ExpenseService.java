package com.example.services;

import com.example.repositories.*;
import com.example.enums.*;
import com.example.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ExpenseService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserExpenseRepository userExpenseRepository;

    @Autowired
    private ExpenseRepository expenseRepository;


    public Expense addGroupExpense(String groupName, String createdByUserName, String description, int amount, HashMap<String, Integer> paidBy, HashMap<String, Integer> supposeToPayBy) {
        Expense expense = new Expense();

        // Get the Group for the Expense
        Optional<Group> groupOptional = groupRepository.findByName(groupName);
        if(groupOptional.isEmpty()) {
            throw new RuntimeException("No Group exists with the entered Group name!");
        }
        expense.setGroup(groupOptional.get());

        // Get the User who created the Expense
        Optional<User> userOptional = userRepository.findByName(createdByUserName);
        if(userOptional.isEmpty()) {
            throw new RuntimeException("No User exists with the entered User name!");
        }
        expense.setCreatedBy(userOptional.get());

        // Set description, amount, ExpenseType of the Expense
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setExpenseType(ExpenseType.NORMAL);

        // Save in DB before creating UserExpense objects as UserExpense has Expense attribute,
        // so inorder to set that the Expense should be saved in the DB.
        expense = expenseRepository.save(expense);

        // Generate list of UserExpense of PaidBy and HadToPayBy Users with the amount
        List<UserExpense> userExpenses = new ArrayList<>();

        // For creating PaidBy UserExpense objects
        for(String paidByUserName : paidBy.keySet()) {
            UserExpense userExpense = new UserExpense();

            userExpense.setExpense(expense);
            userExpense.setAmount(paidBy.get(paidByUserName));
            userExpense.setUserExpenseType(UserExpenseType.PAID);

            Optional<User> paidByUserOptional = userRepository.findByName(paidByUserName);
            if(paidByUserOptional.isEmpty()) {
                throw new RuntimeException("No User exists with the entered User name for paidBy!");
            }
            userExpense.setUser(paidByUserOptional.get());

            userExpense = userExpenseRepository.save(userExpense);
            userExpenses.add(userExpense);
        }

        // For creating HadToPayBy UserExpense objects
        for(String supposeToPayByUserName : supposeToPayBy.keySet()) {
            UserExpense userExpense = new UserExpense();

            userExpense.setExpense(expense);
            userExpense.setAmount(supposeToPayBy.get(supposeToPayByUserName));
            userExpense.setUserExpenseType(UserExpenseType.HAD_TO_PAY);

            Optional<User> supposeToPayByUserOptional = userRepository.findByName(supposeToPayByUserName);
            if(supposeToPayByUserOptional.isEmpty()) {
                throw new RuntimeException("No User exists with the entered User name for supposeToPayBy!");
            }
            userExpense.setUser(supposeToPayByUserOptional.get());

            userExpense = userExpenseRepository.save(userExpense);
            userExpenses.add(userExpense);
        }

        expense.setUserExpenses(userExpenses);

        // Save the Expense in the DB
        expense = expenseRepository.save(expense);
        return expense;
    }

}
