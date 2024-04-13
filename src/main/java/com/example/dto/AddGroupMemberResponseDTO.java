package com.example.dto;

import com.example.enums.ResponseType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AddGroupMemberResponseDTO {

    private List<String> groupMembers;

    private ResponseType responseType;

    private String failureMessage;

}
