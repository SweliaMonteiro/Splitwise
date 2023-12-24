package com.test.splitwise.controllers;

import com.test.splitwise.dto.*;
import com.test.splitwise.enums.ResponseType;
import com.test.splitwise.models.Group;
import com.test.splitwise.models.User;
import com.test.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

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
