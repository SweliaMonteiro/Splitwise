package com.example.commands;

import com.example.controllers.GroupController;
import com.example.dto.*;
import com.example.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddGroupMemberCommand implements Command {

    @Autowired
    private GroupController groupController;


    @Override
    public boolean matches(String input) {
        // AddGroupMember <groupName> <userName>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==3 && inputArray[0].equalsIgnoreCase("AddGroupMember")) {
            return true;
        }
        return false;
    }


    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");

        AddGroupMemberRequestDTO addGroupMemberRequestDTO = new AddGroupMemberRequestDTO();
        addGroupMemberRequestDTO.setGroupName(inputArray[1]);
        addGroupMemberRequestDTO.setUserName(inputArray[2]);

        AddGroupMemberResponseDTO addGroupMemberResponseDTO = groupController.addGroupMember(addGroupMemberRequestDTO);

        if(addGroupMemberResponseDTO.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.print("Successfully added a new member to the Group. Group Members : ");
            for(String member : addGroupMemberResponseDTO.getGroupMembers()) {
                System.out.print(member + ", ");
            }
            System.out.println();
        }
        else {
            System.out.println("Error adding a new member to the Group : " + addGroupMemberResponseDTO.getFailureMessage());
        }
    }

}
