package com.example.commands;

import com.example.controllers.UserController;
import com.example.dto.*;
import com.example.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RegisterUserCommand implements Command {

    @Autowired
    private UserController userController;


    @Override
    public boolean matches(String input) {
        // RegisterUser <userName> <phoneNumber> <password>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==4 && inputArray[0].equalsIgnoreCase("RegisterUser")) {
            return true;
        }
        return false;
    }


    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");

        RegisterUserRequestDTO registerUserRequestDTO = new RegisterUserRequestDTO();
        registerUserRequestDTO.setName(inputArray[1]);
        registerUserRequestDTO.setPhoneNumber(inputArray[2]);
        registerUserRequestDTO.setPassword(inputArray[3]);

        RegisterUserResponseDTO registerUserResponseDTO = userController.registerUser(registerUserRequestDTO);

        if(registerUserResponseDTO.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Successfully registered the user with userId : " + registerUserResponseDTO.getUserId());
        }
        else {
            System.out.println("Error registering the User : " + registerUserResponseDTO.getFailureMessage());
        }
    }

}
