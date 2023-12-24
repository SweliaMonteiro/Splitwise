package com.test.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class AddGroupExpenseRequestDTO {

    private String groupName;
    private String createdByUserName;
    private String description;
    private int amount;
    private HashMap<String, Integer> paidBy;
    private HashMap<String, Integer> supposeToPayBy;

}
