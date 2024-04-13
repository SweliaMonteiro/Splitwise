package com.example.commands;

import com.example.controllers.ExpenseController;
import com.example.dto.*;
import com.example.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;


@Component
public class AddGroupExpenseCommand implements Command {

    @Autowired
    private ExpenseController expenseController;


    @Override
    public boolean matches(String input) {
        // AddGroupExpense <groupName> <createdByUserName> <description> <amount> <paidBy> <supposeToPayBy>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==7 && inputArray[0].equalsIgnoreCase("AddGroupExpense")) {
            return true;
        }
        return false;
    }


    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");

        AddGroupExpenseRequestDTO addGroupExpenseRequestDTO = new AddGroupExpenseRequestDTO();
        addGroupExpenseRequestDTO.setGroupName(inputArray[1]);
        addGroupExpenseRequestDTO.setCreatedByUserName(inputArray[2]);
        addGroupExpenseRequestDTO.setDescription(inputArray[3]);
        addGroupExpenseRequestDTO.setAmount(Integer.parseInt(inputArray[4]));

        // Input for <paidBy> ---> user1=100,user2=50,user3=80
        HashMap<String, Integer> paidByMap = new HashMap<>();
        String[] paidByArray = inputArray[5].split(",");
        for(String paidBy : paidByArray) {
            String[] keyValue = paidBy.split("=");
            paidByMap.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }
        addGroupExpenseRequestDTO.setPaidBy(paidByMap);

        HashMap<String, Integer> supposeToPayByMap = new HashMap<>();
        String[] supposeToPayByArray = inputArray[6].split(",");
        for(String supposeToPayBy : supposeToPayByArray) {
            String[] keyValue = supposeToPayBy.split("=");
            supposeToPayByMap.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }
        addGroupExpenseRequestDTO.setSupposeToPayBy(supposeToPayByMap);

        AddGroupExpenseResponseDTO addGroupExpenseResponseDTO = expenseController.addGroupExpense(addGroupExpenseRequestDTO);

        if(addGroupExpenseResponseDTO.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Successfully added a new expense to the Group.");
        }
        else {
            System.out.println("Error adding a new expense to the Group : " + addGroupExpenseResponseDTO.getFailureMessage());
        }
    }

}
