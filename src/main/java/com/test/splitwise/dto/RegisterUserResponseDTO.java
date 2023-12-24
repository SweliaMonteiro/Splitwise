package com.test.splitwise.dto;

import com.test.splitwise.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDTO {

    private int userId;
    private ResponseType responseType;
    private String failureMessage;

}
