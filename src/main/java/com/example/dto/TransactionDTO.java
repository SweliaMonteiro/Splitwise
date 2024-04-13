package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

    private UserDTO supposeToPay;

    private UserDTO supposeToReceive;

    private int amount;

}
