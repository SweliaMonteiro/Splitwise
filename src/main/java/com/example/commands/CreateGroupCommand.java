package com.example.commands;

import com.example.controllers.GroupController;
import com.example.dto.*;
import com.example.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CreateGroupCommand implements Command {

    @Autowired
    private GroupController groupController;


    @Override
    public boolean matches(String input) {
        // CreateGroup <groupName> <adminName>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==3 && inputArray[0].equalsIgnoreCase("CreateGroup")) {
            return true;
        }
        return false;
    }


    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");

        CreateGroupRequestDTO createGroupRequestDTO = new CreateGroupRequestDTO();
        createGroupRequestDTO.setGroupName(inputArray[1]);
        createGroupRequestDTO.setAdminName(inputArray[2]);

        CreateGroupResponseDTO createGroupResponseDTO = groupController.createGroup(createGroupRequestDTO);

        if(createGroupResponseDTO.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Successfully created a new Group with groupId : " + createGroupResponseDTO.getGroupId());
        }
        else {
            System.out.println("Error creating a new Group : " + createGroupResponseDTO.getFailureMessage());
        }
    }

}
