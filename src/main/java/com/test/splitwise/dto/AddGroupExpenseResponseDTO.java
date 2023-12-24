package com.test.splitwise.dto;

import com.test.splitwise.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupExpenseResponseDTO {

    private int expenseId;
    private ResponseType responseType;
    private String failureMessage;

}
