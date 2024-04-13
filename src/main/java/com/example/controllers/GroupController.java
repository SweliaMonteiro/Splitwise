package com.example.controllers;

import com.example.dto.*;
import com.example.enums.ResponseType;
import com.example.models.*;
import com.example.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.*;


@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;


    public CreateGroupResponseDTO createGroup(CreateGroupRequestDTO request) {
        CreateGroupResponseDTO response = new CreateGroupResponseDTO();

        try {
            Group group = groupService.createGroup(request.getGroupName(), request.getAdminName());
            response.setGroupId(group.getId());
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }


    public AddGroupMemberResponseDTO addGroupMember(AddGroupMemberRequestDTO request) {
        AddGroupMemberResponseDTO response = new AddGroupMemberResponseDTO();

        try {
            Group group = groupService.addGroupMember(request.getGroupName(), request.getUserName());
            List<String> groupMemberNames = new ArrayList<>();
            for(User member : group.getMembers()) {
                groupMemberNames.add(member.getName());
            }
            response.setGroupMembers(groupMemberNames);
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }

}
