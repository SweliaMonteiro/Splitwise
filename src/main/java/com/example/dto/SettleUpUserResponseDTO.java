package com.example.dto;

import com.example.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDTO {

    private ResponseType responseType;

    private String failureMessage;

    private List<TransactionDTO> transactions;

}
