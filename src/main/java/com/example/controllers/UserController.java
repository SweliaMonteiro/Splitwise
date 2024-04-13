package com.example.controllers;

import com.example.dto.*;
import com.example.enums.ResponseType;
import com.example.models.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO request) {
        RegisterUserResponseDTO response = new RegisterUserResponseDTO();

        try {
            User user = userService.registerUser(request.getName(), request.getPhoneNumber(), request.getPassword());
            response.setUserId(user.getId());
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }

}
