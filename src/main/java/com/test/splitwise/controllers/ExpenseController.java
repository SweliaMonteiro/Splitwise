package com.test.splitwise.controllers;

import com.test.splitwise.dto.AddGroupExpenseRequestDTO;
import com.test.splitwise.dto.AddGroupExpenseResponseDTO;
import com.test.splitwise.enums.ResponseType;
import com.test.splitwise.models.Expense;
import com.test.splitwise.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    public AddGroupExpenseResponseDTO addGroupExpense(AddGroupExpenseRequestDTO request) {
        AddGroupExpenseResponseDTO response = new AddGroupExpenseResponseDTO();

        try {
            Expense expense = expenseService.addGroupExpense(request.getGroupName(), request.getCreatedByUserName(),
                    request.getDescription(), request.getAmount(), request.getPaidBy(), request.getSupposeToPayBy());
            response.setExpenseId(expense.getId());
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }

}
