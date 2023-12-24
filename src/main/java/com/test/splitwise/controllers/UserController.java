package com.test.splitwise.controllers;

import com.test.splitwise.dto.RegisterUserRequestDTO;
import com.test.splitwise.dto.RegisterUserResponseDTO;
import com.test.splitwise.enums.ResponseType;
import com.test.splitwise.models.User;
import com.test.splitwise.services.UserService;
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
