package com.example.dto;

import com.example.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupExpenseResponseDTO {

    private int expenseId;

    private ResponseType responseType;

    private String failureMessage;

}
