package com.example.dto;

import com.example.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDTO {

    private int userId;

    private ResponseType responseType;

    private String failureMessage;

}
